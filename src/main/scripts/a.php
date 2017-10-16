<?php
	header("Content-type: text/xml; charset=utf-8");
	//execute the java command
	exec("./webservice.sh -s A", $output, $return_code);
	
	//check the return code
	if ($return_code == 0) {
    	//if return_code=0 then retrun the xml file as response
    	$file_name="../webservice_response/ServiceA/service_A_16-10-2017.xml";
    	$myfile = fopen($file_name, "r") or die("Unable to open file!");
		echo fread($myfile,filesize($file_name));
		fclose($myfile);
	} else {
    	//else return 500 response code
	}
?>