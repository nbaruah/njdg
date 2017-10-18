<?php
   header("Content-type: text/xml; charset=utf-8");
   if( $_GET["case_type"] || $_GET["year"] ) {
        $case_type = urldecode($_GET["case_type"]);
        $year = $_GET["year"];
        $current_date = date("d-m-Y");

        $command = '/var/www/njdg/bin/webservice.sh -s C1 -t "'. $case_type .'" -y '. $year;
        //execute the java command
        exec($command, $output, $return_code);

        //check the return code
        if ($return_code == 0) {
            //if return_code=0 then retrun the xml file as response
            $file = file_get_contents('/var/www/njdg/webservice_response/ServiceC1/service_C1_'. $current_date. '.xml');
            echo $file;
        } else {
           //else return 500 response code
           http_response_code(500);
        }
   } else {
        http_response_code(400);
   }
?>
