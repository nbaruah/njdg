package ghc.njdg;

import org.apache.commons.configuration.Configuration;

import ghc.njdg.exeption.QueryBuilderException;

public interface WebServiceQuery {
	public void init(Configuration appConfig) throws QueryBuilderException;
	public String getServiceAQuery() throws QueryBuilderException;
	public String getServiceBQuery() throws QueryBuilderException;
	public String getServiceC1Query(String caseType, String year) throws QueryBuilderException;
	public String getServiceC2Query(String caseType, String year) throws QueryBuilderException;
}
