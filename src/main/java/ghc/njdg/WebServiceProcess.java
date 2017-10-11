package ghc.njdg;

import org.apache.commons.configuration.Configuration;

import ghc.njdg.exeption.WebServiceProcessException;

public interface WebServiceProcess {
	//initialize
	public void init(Configuration appConfig) throws WebServiceProcessException;
	
	//Execute Query
	public void executeQuery(String query) throws WebServiceProcessException;
	
	//Parse ResultSet
	public void parseResultSet() throws WebServiceProcessException;
	
	//Write XML
	public void writeXML() throws WebServiceProcessException;
}
