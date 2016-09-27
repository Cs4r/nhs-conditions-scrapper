
function Rating() {

  var eventSubscribers = new Array();

  this.AddRatingSubscriber = function(subscriber) {
    eventSubscribers.push(subscriber);
  }

  function OnRated(rating) {
    $.each(eventSubscribers, function(index, value) {
      value(rating);
    });

  }

  function addRatingsToPage(data) {
    if (data.length == 0) return;

    var currentRating = $('#AverageRating > .the-rating').data("stars").options.value;
    var averageRating = $(data).find('Average').text();
    $('#AverageRating > .the-rating').stars("select", averageRating);
    updateRatings(data, 'Average', 'TotalNumberOfRatings');
    updateRatings(data, 'OneStar');
    updateRatings(data, 'TwoStar');
    updateRatings(data, 'ThreeStar');
    updateRatings(data, 'FourStar');
    updateRatings(data, 'FiveStar');

    var rating = $(data).find('TotalNumberOfRatings').text();
    OnRated(rating);

    if ((currentRating == 0) && (averageRating > 0)) {
      setupRatingBreakdown();
    }
  }

  function updateRatings(data, starRatingId, dataPropName) {
    var rating = $(data).find(dataPropName ? dataPropName : starRatingId).text();
    $('#' + starRatingId + 'Rating').find('.no-of-ratings').text(rating);
  }

  function setupRatingBreakdown() {
    $viewall.appendTo(".the-average");
    $close.appendTo(".rating-breakdown");

    $("#AverageRating .view-all").click(function() {
      $(this).hide();
      $('#rating-breakdown').attr({ 'aria-expanded': 'true', 'aria-hidden': 'false' }).slideDown("slow");
    });
    $("#rating-breakdown .close a").click(function() {
      $("#AverageRating .view-all").show();
      $('#rating-breakdown').attr({ 'aria-expanded': 'false', 'aria-hidden': 'true' }).slideUp("slow");
    });
  }

  this.Setup = function() {
    //go and fetch current ratings from the server
    //this is done purely to avoid the page-level output caching
    //$.get("/nhschoices/handlers/submitrating.ashx?rating=0", addRatingsToPage, "xml");

    if ($(".ratings-wrap").length) {

      $(".ratings-wrap").addClass("ratings-js");
      $(".submit-rating").remove();
      $(".star-ratings h4").remove();

      $('.static-ratings').find('.the-rating').stars({
        inputType: "select",
        disabled: true
      });

      $viewall = $('<a class="view-all" tabindex="0" role="button" aria-controls="rating-breakdown">View all ratings</a>');
      $close = $('<p class="close"><a name="close" tabindex="0" role="button" aria-label="close">Close</a></p>');
      $label = $('<label id="leave-rating">Please leave your rating</label>');
      $caption = $('<span id="rating"></span>');
      $('#phValidateRating').hide();

      $('#UserRating').stars({
        inputType: "select",
        captionEl: $caption,
        oneVoteOnly: true,
        callback: function(ui, type, value) {
          $.get("/nhschoices/handlers/submitrating.ashx?rating=" + value, addRatingsToPage, "xml");

          $('.thanks-rating').text("Thanks for your rating.");

          if (value < 4) {
            $('.feedback-rating').html('<a id="lnkFeedback" href=' + feedbackurl + '>Help us improve by leaving your feedback</a>');
          }
        }
      });

      var rating = $('#AverageRating > .the-rating').data("stars");
      if (rating.options.value > 0) {
        setupRatingBreakdown();
      }

      $label.appendTo(".user-rating");
      $caption.appendTo(".user-rating");

      $('#UserRating').mouseenter(function() {
        //$('#leave-rating').hide();
        // this method needed due to flicker in IE when leaving rollover of the stars
        $('#leave-rating').html("&nbsp;");
      });

      $('#UserRating').mouseleave(function() {
        //user hasn't made a selection, so show the leave-your-rating message again.
        var rating = $('#UserRating').data("stars");
        if (rating.options.value < 1) {
          //$('#leave-rating').show();
          $('#leave-rating').text("Please leave your rating");
        }
      });

      $("#rating-breakdown").hide();

    }

    // make stars accessible and keyboard navigable
    $('.user-rating .ui-stars-star a').each(function (i) {

      // add some explanatory text for screen readers
      switch (i) {
        case 0:
          $(this).text('Select to rate this article 1 star - unhelpful');
          break;

        case 1:
          $(this).text('Select to rate this article 2 stars - not very helpful');
          break;

        case 2:
          $(this).text('Select to rate this article 3 stars - fairly helpful');
          break;

        case 3:
          $(this).text('Select to rate this article 4 stars - helpful');
          break;

        case 4:
          $(this).text('Select to rate this article 5 stars - very helpful');
          break;

        default:
          break;
      }

      // add tabindex and href so it can be focused on
      $(this).attr({ 'tabIndex': '0', 'href': '' });

      // add further click actions
      $(this).click(function (e) {
        e.preventDefault();
        $('.user-rating .ui-stars-star a').attr('tabIndex', '-1'); // remove tabability
        updateAverage();
        disableRatings();
        $('.user-rating .ui-stars-star a').off();
      });

      // when focused on via keyboard
      $(this).focus(function () {
        $(this).trigger('mouseover');
      });

      // keyboard interaction when focused on
      $(this).keydown(function (e) {
        if (e.which === 13 || e.which === 32) { // pressing enter or space bar
          e.preventDefault();
          $(this).click();
          $('.user-rating .ui-stars-star a').attr('tabIndex', '-1'); // remove tabability
        }
      });
    });

    // when average has been updated, update text for screen readers
    function updateAverage() {
      var delay = setTimeout(function () {
        var srTxt = 'The average rating out of 5 for this page is ' + $('#AverageRating .the-rating input').val();
        $('.the-average .sr-only').text(srTxt);
      }, 1000);
    }

    // when rating is selected, update so can't be selected again and update information for screen readers
    function disableRatings() {
      $('#leave-rating').remove();
      $('#UserRating .ui-stars-star').attr('aria-hidden', 'true');
      var delay = setTimeout(function () {
        var theRating = $('#UserRating input').val();
        if (theRating === '1') {
          var star = ' star';
        } else {
          var star = ' stars';
        }
        var srTxt = '<span class="sr-only rated">You have rated this article ' + theRating + star + ' out of 5 - </span>';
        $('#rating').prepend(srTxt);
      }, 1000);
      $('#rating').attr('tabindex', '-1').focus();
    }

    // clear stars when focus occurs outside of navigation (ie tabs out of main nav)
    var resetStars = (function () {
      $('.user-rating .ui-stars-star').find('a').last().keydown(function (e) {
        if (e.which === 9) {
          $(this).trigger('mouseout');
        }
      });
      $('.user-rating .ui-stars-star').find('a').first().keydown(function (e) {
        if (e.shiftKey && e.which === 9) {
          $(this).trigger('mouseout');
        }
      });
    }());

    // make the view all ratings dropdown accessible
    var viewAllRatings = (function () {

      // add aria labels
      $('#rating-breakdown').attr({ 'aria-expanded': 'false', 'aria-hidden': 'true' });

      // add keyboard control and update aria labels
      $('.the-average .view-all, .rating-breakdown .close a').keydown(function (e) {
        if (e.which === 13 || e.which === 32) { // pressing enter or space bar
          e.preventDefault();
          $(this).click();
          if ($(this).hasClass('view-all')) {
            $('.rating-breakdown .close a').focus();
            $('#rating-breakdown').attr({ 'aria-expanded': 'true', 'aria-hidden': 'false' });
          } else {
            $('.the-average .view-all').focus();
            $('#rating-breakdown').attr({ 'aria-expanded': 'false', 'aria-hidden': 'true' });
          }
        }
      });

      // add info for screen readers so it makes more sense
      var captions = $('#rating-breakdown .the-rating').length;
      $('#rating-breakdown .the-rating').each(function (i) {
        var j = captions - i;
        if (j == 1) {
          var srRating = '<span class="sr-only">' + j + ' star has been given by </span>';
        } else {
          var srRating = '<span class="sr-only">' + j + ' stars have been given by </span>';
        }
        $(this).prepend(srRating);
      });

    }());


    // tidy up markup - remove/replace unneccessary labels
    var removeLabels = (function () {
      $('#AverageRating label, #UserRating .the-rating label, #rating-breakdown .the-rating label').remove();
      $('#rating-breakdown .ratings-caption .no-of-ratings').unwrap();
      $('#leave-rating').replaceWith('<span id="leave-rating">' + $('#leave-rating').text() + '</span>');
    }());


    // be nice to screen readers
    var screenReaders = (function () {

      // add average info so it makes more sense
      var srTxt = '<span class="sr-only">The average rating out of 5 for this page is ' + $('#AverageRating .the-rating input').val() + '</span> ';
      $('#AverageRating .the-average').prepend(srTxt);

      // hide disabled stars so not read out
      $('.ui-stars-star-disabled').attr('aria-hidden', 'true');

      // add aria label so it can update the screen with new info
      $('#AverageRating .the-average').attr({ 'aria-live': 'polite' });

    }());

  }
}

$(document).ready(function() {

  RatingInstance = new Rating();
  RatingInstance.AddRatingSubscriber(handleUserRating);
  RatingInstance.Setup();

});


//added so that the media library can hook into the rating 
function handleUserRating(rating) {
  $('li > .ratings').text(rating + ' ratings');
}

