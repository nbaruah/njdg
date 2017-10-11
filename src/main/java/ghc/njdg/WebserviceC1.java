package ghc.njdg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;

import ghc.njdg.exeption.WebServiceProcessException;

public class WebserviceC1 implements WebServiceProcess {
	private Configuration appConf;
	private Connection conn;
	private File xmlOutputFolder;
	private Statement st;
	private ResultSet rs;
	private ArrayList<RegisteredCase> RegisteredCases;

	@Override
	public void init(Configuration appConfig) throws WebServiceProcessException {
		try {
			this.appConf = appConfig; // ????????
			this.conn = CommonUtil.getconnection(appConf);
			this.xmlOutputFolder = new File(appConf.getString("path.xml.output")+"\\ServiceC1");
		} catch (SQLException e) {
			throw new WebServiceProcessException("Webservice C1, Error while conecting to Data base", e);
		}
	}

	@Override
	public void executeQuery(String query) throws WebServiceProcessException {
		if (StringUtils.isBlank(query)) {
			throw new WebServiceProcessException("Webservice C1, Query String is empty.");
		}
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			throw new WebServiceProcessException("Webservice C1, Error while executing query.", e);
		}

	}

	@Override
	public void parseResultSet() throws WebServiceProcessException {
		RegisteredCases = new ArrayList<>();
		try {
			while (rs.next()) {
				RegisteredCase c = new RegisteredCase();
				c.setCaseType(rs.getString(1));
				c.setCaseNumber(rs.getString(2));
				c.setLongCaseNumber(rs.getString(2));
				c.setYear(rs.getInt(3));
				c.setRegDate(rs.getString(4));
				RegisteredCases.add(c);
			}
			st.close();
			conn.close();
		} catch (SQLException e) {
			throw new WebServiceProcessException("Webservice C1, Error while Parsing result set", e);
		}

	}

	@Override
	public void writeXML() throws WebServiceProcessException {
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer;
		try {
			writer = factory.createXMLStreamWriter(new FileWriter(xmlOutputFolder));
			writer.writeStartDocument();
			for (RegisteredCase c : RegisteredCases) {
				writeCase(writer, c);
			}
			writer.writeEndDocument();
			writer.flush();
			writer.close();
		} catch (XMLStreamException | IOException e) {
			throw new WebServiceProcessException("Error while writing XML to file " + xmlOutputFolder.getPath(), e);
		}
	}
	
	private void writeCase(XMLStreamWriter writer, RegisteredCase c) throws XMLStreamException {
		writer.writeCharacters("\n\t");
		writer.writeStartElement("CASE");
		formatCase(writer, "CASE_TYPE", c.getCaseType());
		formatCase(writer, "CASE_NO", c.getCaseNumber());
		formatCase(writer, "CASE_YEAR", String.valueOf(c.getYear()));
		formatCase(writer, "CASENO", c.getLongCaseNumber());
		formatCase(writer, "REGISTRATION_DATE", c.getRegDate());
		writer.writeCharacters("\n\t");
		writer.writeEndElement();
	}

	private void formatCase(XMLStreamWriter writer, String tag, String data) throws XMLStreamException {
		writer.writeCharacters("\n\t\t");
		writer.writeStartElement(tag);
		writer.writeCharacters(data);
		writer.writeEndElement();
	}
	
	private String getXmlFilePath() {
		return this.xmlOutputFolder + "\\service_C1_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date());
	}

}
