var count;
var path = 'images/';
var ext = '.png';
var max = 5;

$(document).ready(function() {
    resetImages();
});

function resetImages() {
    count = 1;
    $('#prevImg').hide();
    $('#imgHeader').html(getHeader(count));
    $('#current').attr('src', path + count + ext);
    $('#nextImg').show();
}

function prevImage() {
    count--;
    $('#nextImg').show();
    $('#imgHeader').html(getHeader(count));
    $('#current').attr('src', path + count + ext);
    if (count <= 1) {
        $('#prevImg').hide();
    }
}

function nextImage() {
    count++;
    $('#imgHeader').html(getHeader(count));
    $('#current').attr('src', path + count + ext);
    if (count >= 2) {
        $('#prevImg').show();
        $('#nextImg').show();
        if(count === max) {
            $('#nextImg').hide();
        }
    }
}

function getHeader(count) {
    switch(count) {
       case 1 : return "Inloggen";
       case 2 : return "Het begin scherm";
       case 3 : return "Menu";
       case 4 : return "Evenement/Melding plaatsen";
       case 5 : return "Contact";
       default : "Error, gelieve pagina te vernieuwen.";
    }
}
