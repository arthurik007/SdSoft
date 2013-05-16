 var meldingen = [];
 var selectedGroupIndex = undefined;

window.onload = init;

function init() {
	getMeldingen();
}

//
// This function is written using XMLHttpRequest Level 1, so if you're
// using IE or Opera, or a really old version of Safari, Firefox or
// Chrome, you can use this instead of Level 2 (below).
//
function getMeldingen_XHRv1() {
	// change the URL to match the location where you
	// put the sales.json file
	var url = "http://localhost:8080/projectdb/resources/meldingen";
	var request = new XMLHttpRequest();
	request.open("GET", url);
	request.onreadystatechange = function() {
		if (request.readyState === 4 && request.status === 200) {
			updateMeldingen(request.responseText);
		}
	};
	request.send(null);
}

//
// With XMLHttpRequest Level 2 (implemented in new versions of Firefox, Safari
// and Chrome) you can check progress and check for the "load" event with the
// onload event handler instead of checking the onreadystatechange
//
function getMeldingen() {
	// change the URL to match the location where you
	// put the sales.json file
	var url = "http://localhost:8080/projectdb/resources/meldingen";
	var request = new XMLHttpRequest();
	request.open("GET", url);
	request.onload = function() {
		if (request.status === 200) {
			updateMeldingen(request.responseText);
		}
	};
	request.send(null);
}

function updateMeldingen(responseText) {
	var MeldingenDiv = document.getElementById("meldingen");
	var meldingen = JSON.parse(responseText);
	for (var i = 0; i < meldingen.length; i++) {
		var melding = meldingen[i];
		var div = document.createElement("div");
		//div.setAttribute("class", "saleItem");
		div.innerHTML =
                       "<table>\
                        <tr>\
                        <td><h1>"+melding.meldingID+"."+melding.soort+"</h1></td>\n\
                        </tr>\
                        <tr>\
                        <td>"+melding.straat+"&nbsp;"+melding.straatnummer+"&nbsp;"+melding.stad+"</td>\n\
                        </tr>\
                        <tr>\
                        <td>"+melding.omschrijving+"</td>\n\
                        </tr>\
                        <tr>\
                        <td>"+melding.gebruikernaam+"</td>\n\
                        </tr>\
                        </table>";          
		div.id = melding.meldingID;
                MeldingenDiv.appendChild(div);
	}
}

function knoppenVoorbereiden()
{
    $("#meldingSave").click(function()
    {
        createMelding()();
    });
    
    $("meldingDelete").click(function()
    {
        deleteMelding();
    });   
    
    $("meldingUpdate").click(function()
    {
        updateMelding();
    });
}

function createMelding()
{
   var melding = {};
    
    melding.soort = jQuery.trim($("#soort1").val()); 
    melding.straat = jQuery.trim($("#st1").val());
    melding.stad = jQuery.trim($("#std1").val());   
    melding.straatnummer = jQuery.trim($("#stn1").val());
    melding.omschrijving = jQuery.trim($("#om1").val());
    melding.latitude = jQuery.trim($("#latFld1").val());
    melding.longitude = jQuery.trim($("#lngFld1").val());
    melding.gebruikernaam = jQuery.trim($("#username1").val());
    //melding.gebruikernaam = ingelogdeUser;
    //alert(melding.straat);
    //alert(melding.gebruikemrnaam);
    var url = "http://localhost:8080/projectdb/resources/meldingen/";
    var request = new XMLHttpRequest();
    request.open("POST" , url);
    request.onload = function() {
        if (request.status === 201)
        {
             melding.meldingID = request.getResponseHeader("Location").split("/").pop();
             meldingen.push(melding);
             //alert("werkt wel");
             //fb post
             publishMelding();
        }
        else
        {
             alert("werkt niet");
             console.log("Error creating group: " + request.status + " " + request.responseText);
        }
    };
    request.setRequestHeader("Content-Type", "application/json");
    request.send(JSON.stringify(melding));
}



function deleteMelding()
{
    var nummer;
    var melding = {};
    melding.meldingID = jQuery.trim($("#meldid").val());
    nummer = melding.meldingID;
    var login = prompt("Geef login in");
    var wachtwoord = prompt("Geef wachtwoord in");
    if (wachtwoord === "heyhovvlne374" && login === "admin")
    {
    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/projectdb/resources/meldingen/";
    request.open("DELETE",url + melding.meldingID);
    request.onload = function() {
        if (request.status === 204)
        {
            var div = document.getElementById(nummer);
            div.parentNode.removeChild(div);
            meldingen.pop(melding);
        } else {
            console.log("Error deleting group: " + request.status + " - " + request.statusText);
        }
    };
    request.send(null);
    }
    else
    {
        alert("Toegang geweigerd");
    }
 }



function updateMelding()
{
    var melding = {};
    melding.meldingID = jQuery.trim($("#mid").val());
    melding.soort = jQuery.trim($("#soort1").val()); 
    melding.straat = jQuery.trim($("#st1").val());
    melding.stad = jQuery.trim($("#std1").val());   
    melding.straatnummer = jQuery.trim($("#stn1").val());
    melding.omschrijving = jQuery.trim($("#om1").val());
    melding.latitude = jQuery.trim($("#latFld1").val());
    melding.longitude = jQuery.trim($("#longFld1").val());
    melding.gebruikernaam = jQuery.trim($("#username1").val());
    var url = "http://localhost:8080/projectdb/resources/evenementen/";
    var request = new XMLHttpRequest();
    request.open("PUT",url + melding.meldingID);
    request.onload = function(){
        if (request.status === 204) {      
           alert(melding.stad);
           meldingen.splice(melding.meldingID,1,evenement);
           alert("werkt");
        } 
        else 
        {
            console.log("Error updating evenement: " + request.status + " - " + request.statusText);
            alert("werkt niet");
        }
    };
    request.setRequestHeader("Content-Type", "application/json");
    request.send(JSON.stringify(melding));
}





