var BASE_URL = "http://localhost:8080/projectdb/resources";
onload = function() {
    startMap();

};
function startMap() {
    // Also works with: var yourStartLatLng = '59.3426606750, 18.0736160278';
    var yourStartLatLng = new google.maps.LatLng(50.93781, 4.04095);
   


    $('#map_canvas').gmap({'center': yourStartLatLng,
                           
                           'mapTypeControl': true,
                           'zoomControl': true,
                           'zoom': 16});


    $('#map_canvas').gmap().bind('init', function(evt, map) {
        $.getJSON(BASE_URL + '/meldingen', function(data) {

            var resultLength = data.length;

            for (var i = 0; i < resultLength; i++) {
                var omschrijving = data[i].omschrijving;
                var soort = data[i].soort;
                $('#map_canvas').gmap('addMarker', {'position': new google.maps.LatLng(data[i].latitude, data[i].longitude),
                                                    'bounds': true,
                                                    'icon': "icons/icon33.png",
                                                    'zoom': 16
                }).click(function() {
                    $('#map_canvas').gmap('openInfoWindow', {'content': soort + ' : ' + omschrijving}, this);
                });

            }




        });
           $.getJSON(BASE_URL + '/evenementen', function(data) {

            var resultLength = data.length;

            for (var i = 0; i < resultLength; i++) {
                var omschrijving = data[i].omschrijving;
                var soort = data[i].soort;
                $('#map_canvas').gmap('addMarker', {'position': new google.maps.LatLng(data[i].latitude, data[i].longitude),
                                                    'bounds': true,
                                                    'icon': "icons/flag.png",
                                                    'zoom': 16
                }).click(function() {
                    $('#map_canvas').gmap('openInfoWindow', {'content': soort + ' : ' + omschrijving}, this);
                });

            }




        });




    });
    //init



}
;



    