<?php



header('content-type: application/json; charset=utf-8');

if (isset($_POST["username"]) && isset($_POST["st"]) && isset($_POST["stn"]) && isset($_POST["std"]) && isset($_POST["soort"])&& isset($_POST["om"]))
{
    $recipent = array(
        "naam" => strip_tags($_POST["contact_naam"]),
        "email" => strip_tags($_POST["contact_email"])
    );

   
     $recipent = array(
         "name" => "Artur",
         "email" => "arthurik007@hotmail.com"
     );


    $sender = array(
        "username" => strip_tags($_POST['username']),
        "st" => strip_tags($_POST['st']),
        "stn" => strip_tags($_POST['stn']),
        "std" => strip_tags($_POST['std']),
        "soort" => strip_tags($_POST['soort']),
        "om" => strip_tags($_POST['om']),
    );

    $subject = 'Contact uit mobile site';

    $message = '<html><head><title>'.$title.'</title></head><body>'.$sender["bericht"].'</body></html>';

    // To send HTML mail, the Content-type header must be set
    $headers = "From: {$sender['naam']} <{$sender['email']}>" . "\r\n";
    $headers .= "Reply-To: {$sender['naam']}" . "\r\n";
    $headers .= "MIME-Version: 1.0\r\n";
    $headers .= "Content-Type: text/html; charset=ISO-8859-1\r\n";

    if(mail($recipent["email"], $subject, $message, $headers))
    {
        echo json_encode(true);
    }
    else
    {
        echo json_encode(false);
    }

}
else
{
    echo json_encode(false);
}

?>