var button;
var userInfo;
var menu;
var shareFB;
var shareButton;

$(document).ready(function() {
    loadFB();
});

function loadFB() {
    window.fbAsyncInit = function() {
        FB.init({appId: '153798394787450',
            status: true,
            cookie: true,
            xfbml: true,
            oauth: true
        });

        function updateButton(response) {
            button = document.getElementById('fb-auth');
            userInfo = document.getElementById('userInfo');

            if (response.authResponse) {
                //user is already logged in and connected
                FB.api('/me', function(info) {
                    login(response, info);
                });

                button.onclick = function() {
                    FB.logout(function(response) {
                        logout(response);
                    });
                };
            } else {
                button.onclick = function() {
                    FB.login(function(response) {
                        if (response.authResponse) {
                            FB.api('/me', function(info) {
                                login(response, info);
                            });
                        } else {
                            //user cancelled login or did not grant authorization
                        }
                    }, {scope: 'email,user_birthday,publish_stream,user_about_me'});
                };
            }
        }

        // run once with current status and whenever the status changes
        FB.getLoginStatus(updateButton);
        FB.Event.subscribe('auth.statusChange', updateButton);
    };
    (function() {
        var e = document.createElement('script');
        e.async = true;
        e.src = document.location.protocol
                + '//connect.facebook.net/en_US/all.js';
        document.getElementById('fb-root').appendChild(e);
    }());
}

function login(response, info) {
    if (response.authResponse) {
        if (window.location == 'http://localhost:8080/projectdb/' || window.location == 'http://localhost:8080/projectdb/index.html') {
            window.location = "index.html#begin";
        }
        document.getElementById('fblogout').style.display = "block";
        document.getElementById("userInfo").style.display = "block";
        document.getElementById("username").value = info.name;
        document.getElementById("username1").value = info.name;
        userInfo.innerHTML = '<img src="https://graph.facebook.com/' + info.id + '/picture">' + '</br>' +
                '<form>' +
                'Naam: <input type="text" size="10" value="' + info.name + '">' +
                'Stad: <input type="text" size="10" value="' + info.hometown + '">' +
                'Geslacht: <input type="text" size="10" value="' + info.gender + '">' +
                '</form>';
    }
}

function logout() {
    FB.logout(function(response) {
        alert("U bent nu uitgelogd.");
        window.location = "index.html";
    });
}

function publishEvent() {

    var FBshareSlider = $("#flip-1");
    if (FBshareSlider.val() == "on") {
        var straat = $('#st');
        var strnr = $('#stn');
        var stad = $('#std');
        var soort = $('#soort');
        var omsch = $('#om');
        var eventInfo = "Nieuw event: " + soort.val() + "\n" + straat.val() + " " +
                strnr.val() + "\n" + stad.val() + "\n\n" + "Omschrijving:\n" + omsch.val();

        FB.api('/me/feed', 'post',
                {
                    message: eventInfo,
                    link: 'http://webs.hogent.be/neir/',
                    picture: 'http://webs.hogent.be/neir/images/logosmall.png',
                    name: 'Onze Buurt',
                    description: 'Onze Buurt, een app voor iedereen.'
                }
        );
    }
    window.location = 'index.html#begin';
}

function publishMelding() {

    var FBshareSlider = $("#flip-2");
    if (FBshareSlider.val() == "on") {
        var straat = $('#st1');
        var strnr = $('#stn1');
        var stad = $('#std1');
        var soort = $('#soort1');
        var omsch = $('#om1');
        var meldingInfo = "Nieuwe melding: " + soort.val() + "\n" + straat.val() + " " +
                strnr.val() + "\n" + stad.val() + "\n\n" + "Omschrijving:\n" + omsch.val();

        FB.api('/me/feed', 'post',
                {
                    message: meldingInfo,
                    link: 'http://webs.hogent.be/neir/',
                    picture: 'http://webs.hogent.be/neir/images/logosmall.png',
                    name: 'Onze Buurt',
                    description: 'Onze Buurt, een app voor iedereen.'
                }
        );
    }
    window.location = 'index.html#begin';
    window.location = 'index.html#begin';
}