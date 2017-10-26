<?php
        header("Content-type: text/xml; charset=utf-8");
        $app_config = parse_ini_file("/var/www/njdg/config/app_config.ini");
        $command = $app_config["webservice_script"] . " -s A";
        $current_date = date("d-m-Y");
        $response_file = $app_config["xml_response"]["service_a"] . '/service_A_'. $current_date. '.xml';

        //execute the java command
        exec($command, $output, $return_code);

        //check the return code
        if ($return_code == 0) {
            //if return_code=0 then retrun the xml file as response
            //$file_name="/var/www/njdg/webservice_response/ServiceA/service_A_16-10-2017.xml";
            //$myfile = fopen($file_name, "r");
            //echo fread($myfile,filesize($file_name));
            //fclose($myfile);
            $file = file_get_contents($response_file);
            echo $file;
        } else {
           //else return 500 response code
           http_response_code(500);
        }
?>