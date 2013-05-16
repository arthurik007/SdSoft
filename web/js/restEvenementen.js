
var evenementen = [];
var selectedGroupIndex = undefined;

window.onload = init;

function init() {
	getEvenementen();
}

//
// This function is written using XMLHttpRequest Level 1, so if you're
// using IE or Opera, or a really old version of Safari, Firefox or
// Chrome, you can use this instead of Level 2 (below).
//
function getEvenementen_XHRv1() {
	// change the URL to match the location where you
	// put the sales.json file
	var url = "http://localhost:8080/projectdb/resources/evenementen";
	var request = new XMLHttpRequest();
	request.open("GET", url);
	request.onreadystatechange = function() {
		if (request.readyState === 4 && request.status === 200) {
			updateEvenementen(request.responseText);
		}
	};
	request.send(null);
}

//
// With XMLHttpRequest Level 2 (implemented in new versions of Firefox, Safari
// and Chrome) you can check progress and check for the "load" event with the
// onload event handler instead of checking the onreadystatechange
//
function getEvenementen() {
	// change the URL to match the location where you
	// put the sales.json file
	var url = "http://localhost:8080/projectdb/resources/evenementen";
	var request = new XMLHttpRequest();
	request.open("GET", url);
	request.onload = function() {
		if (request.status === 200) {
			updateEvenementen(request.responseText);
		}
	};
	request.send(null);
}

function updateEvenementen(responseText) {
	var EvenementenDiv = document.getElementById("evenementen");
	var evenementen = JSON.parse(responseText);
	for (var i = 0; i < evenementen.length; i++) {
		var evenement = evenementen[i];
		var div = document.createElement("div");
		//div.setAttribute("class", "saleItem");
                div.innerHTML = 
                        "<table>\
                        <tr>\
                        <td><h1>"+evenement.evenementenID+"."+evenement.soort+"</h1></td>\n\
                        </tr>\
                        <tr>\
                        <td>"+evenement.straat+"&nbsp;"+evenement.straatnummer+"&nbsp;"+evenement.stad+"</td>\n\
                        </tr>\
                        <tr>\
                        <td>"+evenement.omschrijving+"</td>\n\
                        </tr>\
                        <tr>\
                        <td>"+evenement.gebruikernaam+"</td>\n\
                        </tr>\
                        </table>";
               
                div.id = evenement.evenementenID;
                EvenementenDiv.appendChild(div);
	}
}


function createEvenement()
{
    var evenement = {};
    
    evenement.soort = jQuery.trim($("#soort").val()); 
    evenement.straat = jQuery.trim($("#st").val());
    evenement.stad = jQuery.trim($("#std").val());   
    evenement.straatnummer = jQuery.trim($("#stn").val());
    evenement.omschrijving = jQuery.trim($("#om").val());
    evenement.latitude = jQuery.trim($("#latFld").val());
    evenement.longitude = jQuery.trim($("#lngFld").val());
    evenement.gebruikernaam = jQuery.trim($("#username").val());
    //evenement.gebruikernaam = ingelogdeUser;
    //alert(evenement.straat);
    //alert(evenement.gebruikernaam);
    var url = "http://localhost:8080/projectdb/resources/evenementen/";
    var request = new XMLHttpRequest();
    request.open("POST" , url);
    request.onload = function() {
        if (request.status === 201)
        {
             evenement.evenementenID = request.getResponseHeader("Location").split("/").pop();
             evenementen.push(evenement);
            // alert("werkt wel");
             //fb post
             publishEvent();
        }
        else
        {
             alert("werkt niet");
             console.log("Error creating group: " + request.status + " " + request.responseText);
        }
    };
    request.setRequestHeader("Content-Type", "application/json");
    request.send(JSON.stringify(evenement));
}

function knoppenVoorbereiden()
{
    $("#evenementSave").click(function()
    {
        createEvenement();
    });
    
    $("evenementDelete").click(function()
    {
        deleteEvenement();
    });   
    
    $("evenementUpdate").click(function()
    {
        updateEvenement();
    });
}

function deleteEvenement()
{
    var nummer;
    var evenement = {};
    evenement.evenementenID = jQuery.trim($("#eveid").val());
    nummer = evenement.evenementenID;
    var login = prompt("Geef login in");
    var wachtwoord = prompt("Geef wachtwoord in");
    if (wachtwoord === "heyhovvlne374" && login === "admin")
    {
    var request = new XMLHttpRequest();
    var url = "http://localhost:8080/projectdb/resources/evenementen/";
    request.open("DELETE",url + evenement.evenementenID);
    request.onload = function() {
        if (request.status === 204)
        {
            var div = document.getElementById(nummer);
            div.parentNode.removeChild(div);
            evenementen.pop(evenement);
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
 

function updateEvenement()
{
    
    var evenement = {};
    evenement.evenementenID = jQuery.trim($("#eid").val());
    evenement.soort = jQuery.trim($("#soort").val()); 
    evenement.straat = jQuery.trim($("#st").val());
    evenement.stad = jQuery.trim($("#std").val());   
    evenement.straatnummer = jQuery.trim($("#stn").val());
    evenement.omschrijving = jQuery.trim($("#om").val());
    evenement.latitude = jQuery.trim($("#la").val());
    evenement.longitude = jQuery.trim($("#lo").val());
    evenement.gebruikernaam = jQuery.trim($("#username").val());
    var url = "http://localhost:8080/projectdb/resources/evenementen/";
    var request = new XMLHttpRequest();
    request.open("PUT",url + evenement.evenementenID);
    request.onload = function(){
        if (request.status === 204) {      
           alert(evenement.stad);
           evenementen.splice(evenement.evenementenID,1,evenement);
           alert("werkt");
        } 
        else 
        {
            console.log("Error updating evenement: " + request.status + " - " + request.statusText);
            alert("werkt niet");
        }
    };
    request.setRequestHeader("Content-Type", "application/json");
    request.send(JSON.stringify(evenement));
}

