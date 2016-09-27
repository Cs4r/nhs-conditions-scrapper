jQuery(document).ready(function($) {

    if ($(".male-map").length != 0) {

        var maleSrc = $("#male-image").attr("src");
        var femaleSrc = $("#female-image").attr("src");
        jQuery.fn.addEvent = jQuery.fn.bind;

        $('#haz-mod2 area').each(function() {
            var id = $(this).attr("id");
            $(this).addEvent('mouseover', function() { switcher(this, 'mouseover') });
            $(this).addEvent('mouseout', function() { switcher(this, 'mouseout') });
            $('a.' + id).addEvent('mouseover', function() { switcher(this, 'mouseover') });
            $('a.' + id).addEvent('mouseout', function() { switcher(this, 'mouseout') });
        });

        //the switch and reset function
        function switcher(obj, state) {

            if (obj.id == '') { 							//if there is no ID...
                obj = obj.className.split(" ", 2); 		//pick the correct class name	
                obj = obj[1]; 							//and set the var to that
            } else {
                obj = obj.id								//if there is one, use the ID	
            }

            targetSplit = obj.split("-", 3); 				//to derive the gender
            targetRaw = obj.replace(/[0-9]/, '1');                              //retain number 1 in arms
            targetFileName = obj.replace(/[0-9]/, ''); 	//removes the numbers from the arm values	
            var targetImgPath = '';

            if (document.getElementById('bodymap-large')) { //checks to see if you are on large bodymap page rather than hub
                targetImgPath = 'large/';
            }

            if (targetSplit[0] != 'selected') {
                if (state == 'mouseover') {
                    var image = "#" + (targetSplit[0] + "-image");
                    $(image).attr("src", "/img/healthaz/bodymap/" + targetImgPath + targetFileName + ".gif")
                    $('a.' + targetRaw).addClass(targetSplit[0] + '-forced-hover'); //to make the link highlight
                } else {
                    switch (targetSplit[0]) 					//finds the correct base image to use depending on gender
                    {
                        case "male":
                            var imgSrc = maleSrc;
                            break
                        case "female":
                            var imgSrc = femaleSrc;
                            break
                    }
                    var image = "#" + (targetSplit[0] + "-image");
                    $(image).attr("src", imgSrc)
                    $('a.' + targetRaw).removeClass(targetSplit[0] + '-forced-hover');
                }
            }
        }


    }

});