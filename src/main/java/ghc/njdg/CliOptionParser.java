package ghc.njdg;

import org.apache.commons.cli.Options;

public class CliOptionParser {
    private static final String SERVICE_TYPE = "t";
    private static final String XML_FILE = "f";
    private static final String CONF_FILE = "c";
    private static final Options cliOptions;

    static {
         cliOptions = constructOptions();
    }
	
	private static Options constructOptions() {
		Options cliOptions = new Options();
		cliOptions.addOption(SERVICE_TYPE, true, "Type of webservice required");
		cliOptions.addOption(XML_FILE, true, "Location where XML file needs to be written");
		cliOptions.addOption(CONF_FILE, true, "Configuration file Path");
		return cliOptions;
	}
	
    static class CliOptions {
        private String serviceType;

        public String getEnvironment() {
             return serviceType;
        }

        public void setEnvironment(String serviceType) {
             this.serviceType = serviceType;
        }
   }
}
