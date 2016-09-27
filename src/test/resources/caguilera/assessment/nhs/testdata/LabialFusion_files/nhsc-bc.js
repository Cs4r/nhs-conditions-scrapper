//-----------------------------------------------------------------------
// FUNCTION:	Brightcove large Play icon
// DESCRIPTION:	Generates and controls how and when large 'Play' icon
//              is displayed on Brightcove media players.
//              Dependent on http://admin.brightcove.com/js/APIModules_all.js and
//              /includes/MobileCompatibility.js (obtained from Brightcove and
//              used to detect smart phone platforms)
//              Image sprite used. Positioning controlled via BEML
//-----------------------------------------------------------------------

var iconPlayURL = "http://media.nhschoices.nhs.uk/mediaplayerassets/base/play-overlay.png";
var iconPlayURLAudio = "http://media.nhschoices.nhs.uk/mediaplayerassets/base/audio_icon_517x80.jpg";
var iconPlayElementID = "controlIconPlay";
var iconPlayElementIDAudio = "controlIconPlayAudio";
var controlTransparentOverlayAudioElementID = "controlTransparentOverlayAudio";
var nhscSmartPlayerId = "692182480001";
var iconPlayClearIcon = null;
var callbackList = {};

function onTemplateLoaded(pPlayer) {

    var player = bcPlayer.getPlayer(pPlayer);
    var exp = player.getModule(APIModules.EXPERIENCE);
    var video = player.getModule(APIModules.VIDEO_PLAYER);
    var overImg = exp.getElementByID(iconPlayElementID);
    var overImgAudio = exp.getElementByID(iconPlayElementIDAudio);
    var overTransparentOverlayAudio = exp.getElementByID(controlTransparentOverlayAudioElementID);

    exp.addEventListener(BCExperienceEvent.TEMPLATE_READY, function () {
        //lastly, execute the callback and send the 
        if (callbackList[pPlayer] != null) {
            var callback = callbackList[pPlayer];
            var vidMgr = new videoManager(pPlayer, video);

            var handled;
            try {
                handled = callback(vidMgr);
            }
            catch (e) {
                console.log(e);
            }
        }

        if (!handled) {
            overImg.setSource(iconPlayURL);
            overImg.setVisible(true);
        }
    });

    video.addEventListener(BCMediaEvent.COMPLETE, function () {
        var videoloaded = video.getCurrentVideo();
        var url = videoloaded.customFields.linkimg2;

        overImg.setSource(iconPlayClearIcon);
        overImg.setURL(videoloaded.linkURL);
    });
    var clearIconFunction = function () {
        overImg.setVisible(false);
        overImg.setSource(iconPlayClearIcon);
        overImg.setURL(iconPlayClearIcon);
    }
    video.addEventListener(BCMediaEvent.CHANGE, clearIconFunction);
    video.addEventListener(BCMediaEvent.PLAY, clearIconFunction);
    video.addEventListener(BCMenuEvent.MENU_PAGE_OPEN, clearIconFunction);

    //--------------------------------
    //Handle the audio icon
    //--------------------------------
    exp.addEventListener(BCExperienceEvent.TEMPLATE_READY, function () {
        overImgAudio.setSource(iconPlayURL);
        overImgAudio.setVisible(true);
    });

    //Clear the overlays
    var clearOverImgAudio = function () {
        overImgAudio.setVisible(false);
        overTransparentOverlayAudio.setVisible(false);
    }
    video.addEventListener(BCMenuEvent.MENU_PAGE_OPEN, clearOverImgAudio);
    video.addEventListener(BCMenuEvent.MENU_PAGE_CLOSE, clearOverImgAudio);
    video.addEventListener(BCMediaEvent.PLAY, clearOverImgAudio);

    //Maintain the overlays
    var maintainOverImgAudio = function () {
        overImgAudio.setVisible(true);
        overTransparentOverlayAudio.setVisible(true);
    }
}

//-----------------------------------------------------------------------
// FUNCTION:	RenderMediaObject()
// DESCRIPTION:	Draw <object> to render BC media
//-----------------------------------------------------------------------

function RenderMediaObject(objectId, title, width, height, playerId, mediaId, isVideo, isUI, clientId, videoLoadedCallback, alternateSmartPlayerId) {

    var devicePlayerId = playerId;
    var thisIsSmartPhone = DetectSmartphone();
    if (thisIsSmartPhone) {
        if (typeof alternateSmartPlayerId != 'undefined' && alternateSmartPlayerId != null && alternateSmartPlayerId != "" && alternateSmartPlayerId > 0) {
            devicePlayerId = alternateSmartPlayerId;
        }
        else {
            devicePlayerId = nhscSmartPlayerId;
        }
    }

    //if the user has specified a callback to notify when the video has loaded, add it to the
    //callback dictionary under the objectId of the player being targetted for this video.
    if (videoLoadedCallback != null) {
        callbackList[objectId] = videoLoadedCallback;
    }

    var metaTags = "";
    if (typeof jQuery != 'undefined') {
        $('meta[name="DC.Subject"][scheme="NHSC.Ontology"]:not([content^="ID"])').each(function () {
            metaTags += $(this).attr('content');
        });
    }

    var objectMarkup = '<object width="' + width + '" height="' + height + '" id="' + objectId + '" class="BrightcoveExperience">' +
				'<param name="width" value="' + width + '" />' +
				'<param name="height" value="' + height + '" />' +
			    '<param name="playerID" value="' + devicePlayerId + '" />' +
				'<param name="@videoPlayer" value="' + mediaId + '" />' +
				'<param name="isVid" value="' + isVideo + '" />' +
				'<param name="isUI" value="' + isUI + '" />' +
			    '<param name="wmode" value="transparent" />' +
			    '<param name="relatedMetaTags" value="' + metaTags + '" />' +
			    '<param name="videoObjectId" value="' + clientId + '" />' +
			    '<param name="currentURL" value="' + document.location.href + '" />';

    if ((typeof webServiceAddress != 'undefined') && (webServiceAddress != null)) {
        objectMarkup += '<param name="webserviceAddress" value="' + webServiceAddress + '"/>';
    }

    objectMarkup += '</object>';

    try {
        $('.swfplayer').children('.js-vers_' + objectId).each(function () {
            $(this).html(objectMarkup);
        });
    }
    catch (err) {
        document.getElementById('js-vers_' + objectId).innerHTML = objectMarkup;
    }

    brightcove.createExperiences(objectId);

    //Disable focus to avoid IE keyboard trap
    var mediaObject = document.getElementById(objectId);
    $(mediaObject).attr('tabindex', '-1');


    $('#' + objectId).attr("title", title);
}

//-----------------------------------------------------------------------
// FUNCTION:	ReplaceText(inputstring,replacedate)
// DESCRIPTION:	Returns the string with the updated date.
//-----------------------------------------------------------------------
function ReplaceText(inputstring, replacedate) {

    var extractReviewddt = inputstring.substr(inputstring.indexOf(':') + 1, inputstring.length);
    if (inputstring.indexOf('/') == -1) {
        return inputstring + ' ' + replacedate;
    }
    else {

        return inputstring.replace(extractReviewddt, replacedate);
    }


}

//-----------------------------------------------------------------------
// FUNCTION:	UpdateMediaDetails()
// DESCRIPTION:	Updates the media details of the specified media object
//-----------------------------------------------------------------------

function UpdateMediaDetails(objectId, title, description, lastReviewdate, nextReviewdate) {
    var videoDiv = $('#bcVideo_' + objectId).parents('.video-panel').parent();
    $(videoDiv).find('#mediaTitle').text(title);
    $(videoDiv).find('#mediaDesc').text(description);
    var lstreviewed = $(videoDiv).find('#lastReviewed').text();
    $(videoDiv).find('#lastReviewed').text(ReplaceText(lstreviewed, lastReviewdate));
    var nxtreview = $(videoDiv).find('#nextReview').text();
    $(videoDiv).find('#nextReview').text(ReplaceText(nxtreview, nextReviewdate));
}


var videoStatus = function (videoIsPlaying, videoPosition) {
    this.isPlaying = videoIsPlaying;
    this.position = videoPosition;
}

var videoManager = function (playerObjectId, videoObject) {
    this.playerId = playerObjectId;
    this.video = videoObject;

    this.playVideo = function () {
        if (this.Video != null) {
            this.Video.play();
        }
    };

    this.playFromPosition = function (seekTime) {
        if (this.Video != null) {
            this.Video.play();
            this.Video.seek(seekTime);
        }
    };

    this.getVideoPosition = function () {
        if (this.Video != null) {
            return this.Video.getVideoPosition();
        }
    };

    this._getVideoStatus = function () {
        if (this.Video != null) {
            var isPlaying = this.Video.isPlaying();
            var position = this.Video.getVideoPosition();
            return new videoStatus(isPlaying, position);
        }
    };

    this._setVideoStatus = function (videoStatus) {
        if (this.Video != null) {

            if (videoStatus.position > 0) {

                var vid = this.Video;
                var progressCallback = function () {
                    vid.seek(videoStatus.position);
                    if (!videoStatus.isPlaying) {
                        vid.pause();
                    }
                    vid.removeEventListener(BCMediaEvent.PROGRESS, progressCallback);
                };

                this.Video.addEventListener(BCMediaEvent.PROGRESS, progressCallback);
                this.Video.play();
            }
        }
    }

    //note: outside of this unit, only the items we expose through
    //this return statement are visible - that includes from within
    //this object, hence we have to make calls to these functions 
    //and properties in the main function body
    return {
        PlayerId: this.playerId,
        Video: this.video,
        play: this.playVideo,
        getVideoStatus: this._getVideoStatus,
        setVideoStatus: this._setVideoStatus
    };
}
