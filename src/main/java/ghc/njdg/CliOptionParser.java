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
	private static final String CASE_YEAR = "y";
	private static final String CASE_TYPE = "t";
	private static final String SERVICE_TYPE = "s";
	private static final String CONF_FILE = "c";

	private static final Options cliOptions;

	private static final Logger logger = LogManager.getLogger(CliOptionParser.class);

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
		cliOptions.setCaseType(cmd.getOptionValue(CASE_TYPE));
		cliOptions.setCaseYear(cmd.getOptionValue(CASE_YEAR));
		logger.info("Parsing cli options. " + cliOptions.toString());
		return cliOptions;
	}

	private static Options constructOptions() {
		final Option confFile = Option.builder(CONF_FILE).required().longOpt("conf").hasArg()
				.desc("Configuration file for the application.").build();
		final Option serviceType = Option.builder(SERVICE_TYPE).required().longOpt("service").hasArg()
				.desc("Type of webservice required").build();
		final Option case_type = Option.builder(CASE_TYPE).required(false).longOpt("case_type").hasArg()
				.desc("Case type parameter.").build();
		final Option case_year = Option.builder(CASE_YEAR).required(false).longOpt("case_year").hasArg()
				.desc("Case year parameter.").build();

		final Options options = new Options();
		options.addOption(confFile);
		options.addOption(serviceType);
		options.addOption(case_type);
		options.addOption(case_year);
		return options;
	}

	public static void printHelp() {
		new HelpFormatter().printHelp(CliOptionParser.class.getCanonicalName() + " provides the following options -",
				cliOptions);
	}

	public static class CliOptions {
		private String serviceType;
		private String confFilepath;
		private String caseType;
		private String caseYear;

		public String getCaseType() {
			return caseType;
		}

		public void setCaseType(String caseType) {
			this.caseType = caseType;
		}

		public String getCaseYear() {
			return caseYear;
		}

		public void setCaseYear(String caseYear) {
			this.caseYear = caseYear;
		}

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
			StringBuilder builder = new StringBuilder();
			builder.append("CliOptions [serviceType=");
			builder.append(serviceType);
			builder.append(", confFilepath=");
			builder.append(confFilepath);
			builder.append(", caseType=");
			builder.append(caseType);
			builder.append(", caseYear=");
			builder.append(caseYear);
			builder.append("]");
			return builder.toString();
		}

	}
}
