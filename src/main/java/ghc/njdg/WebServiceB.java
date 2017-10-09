package ghc.njdg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;

import ghc.njdg.exeption.ServiceBException;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class WebServiceB {
	private Configuration appConf;
	private Connection conn;
	private String query;
	private File xmlFile;
	private Statement st;
	private ResultSet rs;
	private ArrayList<Case> cases;

	//Initialization
	// data base connectivity
	public void init(File confFile) throws ServiceBException {
		try {
			appConf = CommonUtil.loadAppConfig(confFile);
			conn = CommonUtil.getconnection(appConf);
			query = CommonUtil.getServiceBquery(appConf.getString("path.webservice.query"));
			xmlFile = new File(appConf.getString("path.xml.output"));
		} catch (ConfigurationException e) {
			throw new ServiceBException("Error while loading Application Configuration", e);
		} catch (SQLException e) {
			throw new ServiceBException("Error while Connecting to database", e);
		}
	}

	// execute query
	public void executeQuery() throws ServiceBException {
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			throw new ServiceBException("Error while executing query.", e);
		}
	}

	// parse result
	// write xml file
	public void parseResultSet() throws ServiceBException {
		cases = new ArrayList<>();
		try {
			while (rs.next()) {
				Case c = new Case();
				c.setType(rs.getString(1));
				c.setYear(rs.getInt(2));
				c.setTotalCount(rs.getInt(3));
				cases.add(c);
			}
			st.close();
			conn.close();
		} catch (SQLException e) {
			throw new ServiceBException("Error while Parsing result set", e);
		}
	}

	public void writeXML() throws ServiceBException {
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer;
		try {
			writer = factory.createXMLStreamWriter(new FileWriter(xmlFile));
			writer.writeStartDocument();
			for (Case c : cases) {
				writeCase(writer, c);
			}
			writer.writeEndDocument();
			writer.flush();
			writer.close();
		} catch (XMLStreamException | IOException e) {
			throw new ServiceBException("Error while writing XML to file " + xmlFile.getPath(), e);
		}

	}

	private void writeCase(XMLStreamWriter writer, Case c) throws XMLStreamException {
		writer.writeCharacters("\n\t");
		writer.writeStartElement("CASE");
		formatCase(writer, "CASE_TYPE", c.getType());
		formatCase(writer, "CASE_YEAR", String.valueOf(c.getYear()));
		formatCase(writer, "TOTALCOUNT", String.valueOf(c.getTotalCount()));
		formatCase(writer, "SENIORCOUNT", String.valueOf(c.getSeniorCount()));
		formatCase(writer, "WOMEN_COUNT", String.valueOf(c.getWomenCount()));
		formatCase(writer, "DISPOSAL_LAST_MONTH", String.valueOf(c.getLastMonthDisposal()));
		writer.writeCharacters("\n\t");
		writer.writeEndElement();
	}

	private void formatCase(XMLStreamWriter writer, String tag, String data) throws XMLStreamException {
		writer.writeCharacters("\n\t\t");
		writer.writeStartElement(tag);
		writer.writeCharacters(data);
		writer.writeEndElement();
	}
}
