package ghc.njdg;

import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CliOptionParser {
	private static final String SERVICE_TYPE = "s";
	private static final String CONF_FILE = "c";

	private static final Options cliOptions;
	
	private static final Logger LOG = LogManager.getLogger(CliOptionParser.class);

	static {
		cliOptions = constructOptions();
	}

	public static CliOptions parse(String[] args) {
		final CommandLineParser cmdLineParser = new DefaultParser();
		CommandLine cmd = null;
		try {
			cmd = cmdLineParser.parse(cliOptions, args);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Unable to parse arguments. " + Arrays.toString(args), e);
		}

		final CliOptions cliOptions = new CliOptions();
		cliOptions.setServiceType(cmd.getOptionValue(SERVICE_TYPE));
		cliOptions.setConfFilepath(cmd.getOptionValue(CONF_FILE));
		LOG.error("Parsing cli options" + cliOptions.toString());
		return cliOptions;
	}

	private static Options constructOptions() {
		final Option confFile = Option.builder(CONF_FILE).required().longOpt("conf").hasArg()
				.desc("Configuration file for the app.").build();
		final Option serviceType = Option.builder(SERVICE_TYPE).required().longOpt("service").hasArg()
				.desc("Type of webservice required").build();
		final Options options = new Options();
		options.addOption(confFile);
		options.addOption(serviceType);
		return options;
	}

	public static void printHelp() {
		new HelpFormatter().printHelp(CliOptionParser.class.getCanonicalName() + " provides the following options -",
				cliOptions);
	}

	public static class CliOptions {
		private String serviceType;
		private String confFilepath;

		public String getServiceType() {
			return serviceType;
		}

		public void setServiceType(String serviceType) {
			this.serviceType = serviceType;
		}

		public String getConfFilepath() {
			return confFilepath;
		}

		public void setConfFilepath(String confFilepath) {
			this.confFilepath = confFilepath;
		}

		@Override
		public String toString() {
			return "CliOptions [serviceType=" + serviceType + ", confFilepath=" + confFilepath + "]";
		}

	}
}
