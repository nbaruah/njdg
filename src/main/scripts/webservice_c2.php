<?php
  header("Content-type: text/xml; charset=utf-8");
  $app_config = parse_ini_file("/var/www/njdg/config/app_config.ini");
  $current_date = date("d-m-Y");
  $response_file = $app_config["xml_response"]["service_c2"] . '/service_C2_'. $current_date. '.xml';

   if( $_GET["case_type"] || $_GET["year"] ) {
        $case_type = $_GET["case_type"];
        $year = $_GET["year"];
        $args = ' -s C2 -t "'.$case_type.'" -y '. $year;
        $command = $app_config["webservice_script"] . $args;

        //execute the java command
        exec($command, $output, $return_code);

        //check the return code
        if ($return_code == 0) {
            //if return_code=0 then retrun the xml file as response
            $file = file_get_contents($response_file);
            echo $file;
        } else {
           //else return 500 response code
           http_response_code(500);
        }
   } else {
        http_response_code(400);
   }
?>
