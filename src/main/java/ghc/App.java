package ghc;

import org.apache.logging.log4j.Logger;

import java.io.File;

import org.apache.logging.log4j.LogManager;

import ghc.njdg.CliOptionParser;
import ghc.njdg.CliOptionParser.CliOptions;
import ghc.njdg.enums.ServiceType;
import ghc.njdg.CommonUtil;
import ghc.njdg.WebServiceB;

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
			// ExdrDqBadRecordTableSetup dqBadRecordSetup = new ExdrDqBadRecordTableSetup(dqBadRecordOptions.getEnvironment(), dqBadRecordOptions.getUser());
			// dqBadRecordSetup.createExdrDqBadRecordTables();
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
				File appConfigFile = new File(cliOptions.getConfFilepath());
				WebServiceB serviceB = new WebServiceB();
				serviceB.init(appConfigFile);
				serviceB.executeQuery();
				serviceB.parseResultSet();
				serviceB.writeXML();
				break;
				
			case C1:
				System.out.println("C1 type of service");
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
}
