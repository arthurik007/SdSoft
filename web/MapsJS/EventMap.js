var map;
function getLocation() {
    if (navigator.geolocation) {
        var markersArray = [];
        function hasPosition(position) {
            var point = new google.maps.LatLng(position.coords.latitude, position.coords.longitude),
                    myOptions = {
                zoom: 18,
                center: point,
                mapTypeId: google.maps.MapTypeId.HYBRID,
                mapTypeControl: true,
                zoomControl: true,
                zoomControlOptions: {style: google.maps.ZoomControlStyle.SMALL},
                mapTypeControlOptions: {
                    style: google.maps.MapTypeControlStyle.DROPDOWN_MENU}

            },
            map = new google.maps.Map(document.getElementById("map_canvas1"), myOptions),
                    marker = new google.maps.Marker({
                position: point,
                icon: "fhf",
                map: map,
                title: "U bent hier"
            })
            google.maps.event.addListener(map, 'click', function(event) {
                deleteOverlays();
                document.getElementById("latFld").value = event.latLng.lat();
                document.getElementById("lngFld").value = event.latLng.lng();

                var lat = event.latLng.lat();
                var long = event.latLng.lng();

                var markerEvent_position = event.latLng;
                markerEvent = new google.maps.Marker({
                    map: map,
                    draggable: false,
                    title: "Mijn event",
                    icon: "icons/flag.png"
                });
                markersArray.push(markerEvent);
                markerEvent.setPosition(markerEvent_position);

                var geo = new google.maps.Geocoder;
                //var cord = lat + "," + long
                var latlng = new google.maps.LatLng(lat, long);

                geo.geocode({'latLng': latlng}, function(results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        //return results[0].geometry.location;
                        //alert('The address is' + " "+ results[0].formatted_address );
                        var adres = results[0].formatted_address;
                        function getfirstPart(str) {

                            return str.split(/\d+/g)[0];

                        }
                        function getSecondPart(str) {

                            return str.match(/\d+/g)[0];
                        }
                        function getTussenstuk(str) {
                            return str.split(', ')[1];

                        }
                        var deelTwee = getTussenstuk(adres);
                        function getThirdPart(str) {
                            return str.split(' ')[0];
                        }
                        function getFourthPart(str) {
                            return str.split(' ')[1];
                        }

                        document.getElementById("st").value = getfirstPart(adres);
                        document.getElementById("stn").value = getSecondPart(adres);
                        document.getElementById("std").value = getThirdPart(deelTwee) + ' ' + getFourthPart(deelTwee);


                    } else {
                        alert("Geocode was not successful for the following reason: " + status);
                    }

                });

// INFOWINDOW SHIZZLE

// var contentstring ='<div id=\"insInfowindow\"><a  onclick="myOpenPopup();"><h1>Plaats</h1></a></div>';
                /*var infowindow = new google.maps.InfoWindow ({
                 content: contentstring
                 })*/


                google.maps.event.addListener(markerEvent, 'click', function() {
                    infowindow = new google.maps.InfoWindow({
                        content: contentstring});

                    infowindow.open(map, markerEvent);
                });

            })


//EINDE INFOWINDOW SHIZZLE


        }
// Deletes all markers in the array by removing references to them
        function deleteOverlays() {
            if (markersArray) {
                for (i in markersArray) {
                    markersArray[i].setMap(null);
                }
                markersArray.length = 0;
            }
        }
    }
    navigator.geolocation.getCurrentPosition(hasPosition);
}
window.onload = getLocation();