package ghc.njdg;

import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CliOptionParser {
	private static final String SERVICE_TYPE = "t";
	private static final String XML_FILE = "f";
	private static final String CONF_FILE = "c";
	
	private static final Options cliOptions;

	static {
		cliOptions = constructOptions();
	}

	@SuppressWarnings("deprecation")
	public static CliOptions parse(String[] args) {
		if (args.length < 3) {
			System.exit(-1);
		}
		
        CommandLine cmd = null;
        try {
             cmd = new GnuParser().parse(cliOptions, args);
        } catch (ParseException e) {
             throw new IllegalArgumentException("Unable to parse arguments. " +
                       Arrays.toString(args), e);
        }
        
        CliOptions cliOptions = new CliOptions();
        cliOptions.setServiceType(cmd.getOptionValue(SERVICE_TYPE));
        cliOptions.setXmlFilePath(XML_FILE);
        cliOptions.setConfFilepath(CONF_FILE);
        return cliOptions;
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
		private String xmlFilePath;
		private String confFilepath;

		public String getServiceType() {
			return serviceType;
		}

		public void setServiceType(String serviceType) {
			this.serviceType = serviceType;
		}

		public String getXmlFilePath() {
			return xmlFilePath;
		}

		public void setXmlFilePath(String xmlFilePath) {
			this.xmlFilePath = xmlFilePath;
		}

		public String getConfFilepath() {
			return confFilepath;
		}

		public void setConfFilepath(String confFilepath) {
			this.confFilepath = confFilepath;
		}

	}
}
