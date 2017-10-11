package ghc;

import org.apache.logging.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;

import ghc.njdg.CliOptionParser;
import ghc.njdg.CliOptionParser.CliOptions;
import ghc.njdg.enums.ServiceType;
import ghc.njdg.CommonUtil;
import ghc.njdg.WebServiceB;
import ghc.njdg.WebServiceProcess;
import ghc.njdg.WebServiceQuery;
import ghc.njdg.WebServiceQueryBuilder;
import ghc.njdg.WebServiceC1;

public class App {
	private static final Logger LOG = LogManager.getLogger(App.class);

	public static void main(String[] args) throws Exception {
		try {
			System.out.println(args.length);
			if (args.length < 2) {
				CliOptionParser.printHelp();
				System.exit(-1);
			}
			
			CommonUtil.configureLogging();
			CliOptions cliOptions = CliOptionParser.parse(args);
			File appConfigFile = new File(cliOptions.getConfFilepath());
			Configuration appConfig = CommonUtil.loadAppConfig(appConfigFile); //catch exception
			WebServiceQuery queryBuilder = new WebServiceQueryBuilder();
			queryBuilder.init(appConfig);
			ServiceType service;
			
			try{
				service = ServiceType.valueOf(cliOptions.getServiceType());
			} catch (Exception e) {
				throw new IllegalArgumentException("Invalid service type", e);
			}
			switch (service) {
			case A:
				System.out.println("A type of service");
				break;
				
			case B:
				System.out.println("B type of service");
				WebServiceB serviceB = new WebServiceB();
				serviceB.init(appConfigFile);
				serviceB.executeQuery();
				serviceB.parseResultSet();
				serviceB.writeXML();
				break;
				
			case C1:
				String caseType = cliOptions.getCaseType();
				String caseYear = cliOptions.getCaseYear();
				validateServiceCargs(caseType, caseYear);
				String query = queryBuilder.getServiceC1Query(caseType, caseYear);
				WebServiceProcess webService = new WebServiceC1();
				webService.init(appConfig); 
				webService.executeQuery(query);
				webService.parseResultSet();
				webService.writeXML();
				break;
			
			case C2:
				System.out.println("C2 type of service");
				break;
			}
			
			
		} catch (Exception e) {
			LOG.error("", e);
			System.exit(-1);
		}
	}
	
	private static void validateServiceCargs(String caseType, String caseYear) {
		if(StringUtils.isBlank(caseType) || StringUtils.isBlank(caseYear)) {
			throw new IllegalArgumentException("CaseType and CaseYear cannot be blank");
		}
	}
}
