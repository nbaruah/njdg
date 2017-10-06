package ghc.njdg;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class CommonUtil {
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
	
	public static void main(String[] args) throws Exception {
		Connection conn = CommonUtil.getconnection(CommonUtil.loadAppConfig(new File("D:\\migrations\\njdg_webservice\\njdg\\src\\config.properties")));
		Statement statement = conn.createStatement();
		String queryString = "SELECT [DistrictID],[StateID],[District] FROM [HighNIC].[Admin].[tDistricts]";
		ResultSet rs = statement.executeQuery(queryString);
		while (rs.next()) {
			System.out.println(rs.getString("DistrictID") + "\t" + rs.getString("StateID") + "\t" + rs.getString("District"));
		}
	}
	
	public static String getServiceBquery(String queryTemplateFilePath) throws ConfigurationException {
		PropertiesConfiguration queryTemplateConf = new PropertiesConfiguration();
        queryTemplateConf.setDelimiterParsingDisabled(true);
        queryTemplateConf.load(queryTemplateFilePath);
		return queryTemplateConf.getString("service.b");
	}
}
