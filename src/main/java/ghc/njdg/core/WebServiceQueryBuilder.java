package ghc.njdg.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;

import ghc.njdg.exeption.QueryBuilderException;

public class WebServiceQueryBuilder implements WebServiceQuery{
	private String serviceATemplate;
	private String serviceBTemplate;
	private String serviceC1Template;
	private String serviceC2Template;
	
	@Override
	public void init(Configuration appConfig) throws QueryBuilderException {
		PropertiesConfiguration queryTemplateConf = new PropertiesConfiguration();
		String queryTemplateFilePath = appConfig.getString(Constants.PATH_QUERY_TEMPLATE);
        queryTemplateConf.setDelimiterParsingDisabled(true);
        try {
			queryTemplateConf.load(queryTemplateFilePath);
			serviceATemplate = queryTemplateConf.getString(Constants.SERVICE_A_QUERY_KEY);
			serviceBTemplate = queryTemplateConf.getString(Constants.SERVICE_B_QUERY_KEY);
			serviceC1Template = queryTemplateConf.getString(Constants.SERVICE_C1_QUERY_KEY);
			serviceC2Template = queryTemplateConf.getString(Constants.SERVICE_C2_QUERY_KEY);
		} catch (ConfigurationException e) {
			throw new QueryBuilderException("Error while loading query template file " + queryTemplateFilePath, e);
		}
	}

	@Override
	public String getServiceAQuery() throws QueryBuilderException {
		return this.serviceATemplate;
	}

	@Override
	public String getServiceBQuery() throws QueryBuilderException {
		return this.serviceBTemplate;
	}

	@Override
	public String getServiceC1Query(String caseType, String year) throws QueryBuilderException {
		if(StringUtils.isBlank(caseType)) {
			throw new QueryBuilderException("Error while creating Service C1 query, caseType cannot be blank");
		}
		
		if(StringUtils.isBlank(year)) {
			throw new QueryBuilderException("Error while creating Service C1 query, caseYear cannot be blank");
		}
		Map<String, String> placeHolderMap = new HashMap<>();
        placeHolderMap.put(Constants.TEMPLATE_CASE_TYPE, caseType);
        placeHolderMap.put(Constants.TEMPLATE_CASE_YEAR, year);
		return CommonUtil.substituteTemplate(placeHolderMap, serviceC1Template);
	}

	@Override
	public String getServiceC2Query(String caseType, String year) throws QueryBuilderException {
		if(StringUtils.isBlank(caseType)) {
			throw new QueryBuilderException("Error while creating Service C2 query, caseType cannot be blank");
		}
		
		if(StringUtils.isBlank(year)) {
			throw new QueryBuilderException("Error while creating Service C2 query, caseYear cannot be blank");
		}
		Map<String, String> placeHolderMap = new HashMap<>();
        placeHolderMap.put(Constants.TEMPLATE_CASE_TYPE, caseType);
        placeHolderMap.put(Constants.TEMPLATE_CASE_YEAR, year);
		return CommonUtil.substituteTemplate(placeHolderMap, serviceC2Template);
	}

}
