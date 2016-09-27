var NHSUK = NHSUK || {};

NHSUK.typeahead =
(function ($) {
  var maxResultCount = 10,
          hideLinkId = 'hide-link',
             baseUrl = 'http://' + location.hostname,
           searchUrl = baseUrl + '/search?collection=nhs-meta',
             fullUrl = baseUrl + '/s/suggest.json?collection=nhs-meta&partial_query=%QUERY&sort=0&fmt=json++&profile=&show=' + maxResultCount,
         suggestions = new Bloodhound({
                    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('disp'),
                    queryTokenizer: Bloodhound.tokenizers.whitespace,
                             limit: maxResultCount,
                            remote: { url : fullUrl,wildcard: '%QUERY' }
                    });

  function hideMenuOnClick() {
    $('.twitter-typeahead').on('click', '#' + hideLinkId,
      function(){
        $('#q').typeahead('close');
        return false;
      }
    );
  }

  function init() {
    suggestions.initialize();

    $('#q').typeahead({
        minLength: 2,
        highlight: true,
      },
     {
       name : 'suggestions',
       limit: maxResultCount,
       display: 'disp',
       source: suggestions.ttAdapter(),
       templates: {
        footer: '<p><a id="' + hideLinkId + '" title="hide search suggestions" href="#">hide<span class="hidden"> search suggestions</span></a></p>',
        suggestion: function(data){
          var displayitem = "";
          switch (data.disp_t) {
              case 'J':
                  $.each(data.disp, function(key, value) {
                      displayitem += (key, value);
                      displayitem += " ";
                  });
                  break;
              default:
                if (data.disp.length > 36)
                    displayitem = data.disp.substring(0,36)+'...';
                else
                  displayitem = data.disp;
                break;
          }
          switch (data.action_t) {
              case 'Q':
                  displayitem = '<a href="' + searchUrl + '&query=' + data.action + '">' + displayitem + '</a>';
                  break;
              case 'E':
                  displayitem = '<a href="' + searchUrl + '&query=' + data.key + '&' + data.action + '">' + displayitem + '</a>';
                  break;
              case 'U':
                  displayitem = '<a href="' + data.action + '">' + displayitem + '</a>';
                  break;
              case 'C':
                  displayitem = '<a href="#" onClick="' + data.action + '">'  + displayitem + '</a>';
                  break;
              default:
                  displayitem = '<a href="' + searchUrl + '&query=' + data.disp + '">' + displayitem + '</a>';
                  break;
          }
          return displayitem;
          }
        }
      })
      .bind('typeahead:open', function() {
        var val = $('#q').typeahead('val'),
          value = $('#q').attr('value');

        if (val === value) {
          $('#q').typeahead('val', value);
        }
        if (val.toLowerCase() === 'enter a search term'){
          $('#q').typeahead('val', '');
        }
      });
    hideMenuOnClick();
  }

  return {
    init: init
  };
}(jq1113));

$(function(){NHSUK.typeahead.init();});