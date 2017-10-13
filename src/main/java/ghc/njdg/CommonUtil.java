package ghc.njdg;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.log4j.BasicConfigurator;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

public class CommonUtil {
	private static final String SYS_PROP_LOG4J2_CONFIGURATION = "log4j.configurationFile";
	
    public static void configureLogging() throws IOException {
        String log4j2ConfigFile = System.getProperty(SYS_PROP_LOG4J2_CONFIGURATION);
        if (log4j2ConfigFile != null) {
            ConfigurationSource source = new ConfigurationSource(new FileInputStream(log4j2ConfigFile));
            Configurator.initialize(null, source);
        } else {
        	BasicConfigurator.configure();
        }
    } 
    
    /**
     * This method substitutes a template string variables (${variableName}) with corresponding value in present in Map
     * 
     * @param keysMap
     *             Map of variable and value to be replace in the string
     * @param templateString
     *             the template string with the variables.
     * @return
     */
    public static String substituteTemplate(Map<String, String> keysMap, String templateString) {
         StrSubstitutor substitutor = new StrSubstitutor(keysMap);
         String resolvedString = substitutor.replace(templateString);
         return resolvedString;
    }
	
	public static PropertiesConfiguration loadAppConfig(File confFile) throws ConfigurationException {
        PropertiesConfiguration propConf = new PropertiesConfiguration();
        propConf.setDelimiterParsingDisabled(true);
        propConf.load(confFile);
        return propConf;
	}
	
	public static Connection getconnection(Configuration conf) throws SQLException {
		String db_conn_string = conf.getString(Constants.DB_CONN_STRING);
		String db_username = conf.getString(Constants.DB_USERNAME);
		String db_password = conf.getString(Constants.DB_PASSWORD);
		Connection conn = DriverManager.getConnection(db_conn_string, db_username, db_password);
		return conn;
	}
		
}
