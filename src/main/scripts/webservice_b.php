<?php
        header("Content-type: text/xml; charset=utf-8");
        $app_config = parse_ini_file("/var/www/njdg/config/app_config.ini");
        $command = $app_config["webservice_script"] . " -s B";
        $current_date = date("d-m-Y");
        $response_file = $app_config["xml_response"]["service_b"] . '/service_B_'. $current_date. '.xml';
		
		if(file_exists($response_file)){
			$file = file_get_contents($response_file);
            echo $file;
		}else{
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
		}
?>
