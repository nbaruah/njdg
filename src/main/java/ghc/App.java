package ghc;

import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;

import ghc.njdg.CliOptionParser;
import ghc.njdg.CliOptionParser.CliOptions;
import ghc.njdg.enums.ServiceType;
import ghc.njdg.exeption.QueryBuilderException;
import ghc.njdg.CommonUtil;
import ghc.njdg.WebServiceA;
import ghc.njdg.WebServiceB;
import ghc.njdg.WebServiceProcess;
import ghc.njdg.WebServiceQuery;
import ghc.njdg.WebServiceQueryBuilder;
import ghc.njdg.WebServiceC1;
import ghc.njdg.WebServiceC2;

public class App {
	private static final Logger logger = LogManager.getLogger(App.class);

	public static void main(String[] args) {
		try {
			if (args.length < 2) {
				CliOptionParser.printHelp();
				System.exit(-1);
			}

			CommonUtil.configureLogging();
			CliOptions cliOptions = CliOptionParser.parse(args);
			File appConfigFile = new File(cliOptions.getConfFilepath());
			Configuration appConfig = CommonUtil.loadAppConfig(appConfigFile);
			WebServiceQuery queryBuilder = new WebServiceQueryBuilder();
			queryBuilder.init(appConfig);
			ServiceType service;

			try {
				service = ServiceType.valueOf(cliOptions.getServiceType());
			} catch (Exception e) {
				throw new IllegalArgumentException("Invalid service type", e);
			}
			switch (service) {
			case A:
				String aQuery = queryBuilder.getServiceAQuery();
				WebServiceProcess webServiceA = new WebServiceA();
				webServiceA.init(appConfig);
				webServiceA.executeQuery(aQuery);
				webServiceA.parseResultSet();
				webServiceA.writeXML();
				break;

			case B:
				String bQuery = queryBuilder.getServiceBQuery();
				WebServiceProcess webServiceB = new WebServiceB();
				webServiceB.init(appConfig);
				webServiceB.executeQuery(bQuery);
				webServiceB.parseResultSet();
				webServiceB.writeXML();
				break;

			case C1:
				String caseType = cliOptions.getCaseType();
				String caseYear = cliOptions.getCaseYear();
				validateServiceCargs(caseType, caseYear);
				String query = queryBuilder.getServiceC1Query(caseType, caseYear);
				WebServiceProcess webServiceC1 = new WebServiceC1();
				webServiceC1.init(appConfig);
				webServiceC1.executeQuery(query);
				webServiceC1.parseResultSet();
				webServiceC1.writeXML();
				break;

			case C2:
				String c2CaseType = cliOptions.getCaseType();
				String c2CaseYear = cliOptions.getCaseYear();
				validateServiceCargs(c2CaseType, c2CaseYear);
				String c2Query = queryBuilder.getServiceC2Query(c2CaseType, c2CaseYear);
				WebServiceProcess webServiceC2 = new WebServiceC2();
				webServiceC2.init(appConfig);
				webServiceC2.executeQuery(c2Query);
				webServiceC2.parseResultSet();
				webServiceC2.writeXML();
				break;
			}

		} catch (IOException e) {
			logger.error("Error while loading Logging configuartion. ", e);
			System.exit(-1);
		} catch (ConfigurationException e) {
			logger.error("Error while loading Application configuration file. ", e);
			System.exit(-1);
		} catch (QueryBuilderException e) {
			logger.error("Error while generating Webservice Query. ", e);
			System.exit(-1);
		} catch (Exception e) {
			logger.error("Error while running WebService. ", e);
			System.exit(-1);
		}
	}

	private static void validateServiceCargs(String caseType, String caseYear) {
		if (StringUtils.isBlank(caseType) || StringUtils.isBlank(caseYear)) {
			throw new IllegalArgumentException("CaseType and CaseYear cannot be blank");
		}
	}
}
