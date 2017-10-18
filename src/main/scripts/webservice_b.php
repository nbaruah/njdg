<?php
        header("Content-type: text/xml; charset=utf-8");
        //execute the java command
        exec("/var/www/njdg/bin/webservice.sh -s B", $output, $return_code);
        
        //check the return code
        if ($return_code == 0) {
            //if return_code=0 then retrun the xml file as response
            $file = file_get_contents('/var/www/njdg/webservice_response/ServiceB/service_B_16-10-2017.xml');
            echo $file;
        } else {
           //else return 500 response code
           http_response_code(500);
        }
?>
