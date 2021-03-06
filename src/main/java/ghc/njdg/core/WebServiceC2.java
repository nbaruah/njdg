package ghc.njdg.core;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ghc.njdg.exeption.WebServiceProcessException;

public class WebServiceC2 implements WebServiceProcess {
	private Connection conn;
	private File xmlOutputFile;
	private Statement st;
	private ResultSet rs;
	private ArrayList<FiledCase> filedCases;

	private static final Logger logger = LogManager.getLogger(WebServiceC2.class);

	@Override
	public void init(Configuration appConfig) throws WebServiceProcessException {
		try {
			this.conn = CommonUtil.getconnection(appConfig);
			this.xmlOutputFile = new File(getXmlFilePath(appConfig));
			File parentDirectory = this.xmlOutputFile.getParentFile();
			if (!parentDirectory.exists()) {
				parentDirectory.mkdirs();
			}
		} catch (SQLException e) {
			throw new WebServiceProcessException("Webservice C2, Error while conecting to Data base", e);
		}
	}

	@Override
	public void executeQuery(String query) throws WebServiceProcessException {
		if (StringUtils.isBlank(query)) {
			throw new WebServiceProcessException("Webservice C2, Query String is empty.");
		}
		try {
			st = conn.createStatement();
			logger.info("Executing query: " + query);
			rs = st.executeQuery(query);
			logger.info("Query executed Successfuly");
		} catch (SQLException e) {
			throw new WebServiceProcessException("Webservice C2, Error while executing query.", e);
		}

	}

	@Override
	public void parseResultSet() throws WebServiceProcessException {
		filedCases = new ArrayList<>();
		try {
			while (rs.next()) {
				FiledCase c = new FiledCase();
				c.setCaseType(rs.getString(1));
				c.setFilingNo(rs.getString(2));
				c.setFilingYear(rs.getInt(3));
				c.setLongFilingNum();
				c.setFilingDate(rs.getString(4));
				filedCases.add(c);
			}
			st.close();
			conn.close();
		} catch (SQLException e) {
			throw new WebServiceProcessException("Webservice C2, Error while Parsing result set", e);
		}

	}

	@Override
	public void writeXML() throws WebServiceProcessException {
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer;
		try {
			writer = factory.createXMLStreamWriter(new FileWriter(xmlOutputFile));
			writer.writeStartDocument();
			writer.writeStartElement(Constants.SERVICE_C2_ROOT_ELEM);
			for (FiledCase c : filedCases) {
				writeCase(writer, c);
			}
			writer.writeEndElement();
			writer.writeEndDocument();
			writer.flush();
			writer.close();
			logger.info("Successfuly written to file: " + this.xmlOutputFile.getPath());
		} catch (XMLStreamException | IOException e) {
			throw new WebServiceProcessException("Error while writing XML to file " + xmlOutputFile.getPath(), e);
		}
	}

	private void writeCase(XMLStreamWriter writer, FiledCase c) throws XMLStreamException {
		writer.writeCharacters(Constants.NEWLINE_SINGLE_TAB);
		writer.writeStartElement("CASE");
		formatCase(writer, "CASE_TYPE", c.getCaseType());
		formatCase(writer, "FILING_NO", c.getFilingNo());
		formatCase(writer, "FILING_YEAR", String.valueOf(c.getFilingYear()));
		formatCase(writer, "FILINGNO", c.getLongFilingNum());
		formatCase(writer, "FILING_DATE", c.getFilingDate());
		writer.writeCharacters(Constants.NEWLINE_SINGLE_TAB);
		writer.writeEndElement();
	}

	private void formatCase(XMLStreamWriter writer, String tag, String data) throws XMLStreamException {
		writer.writeCharacters(Constants.NEWLINE_DOUBLE_TAB);
		writer.writeStartElement(tag);
		writer.writeCharacters(data);
		writer.writeEndElement();
	}

	private String getXmlFilePath(Configuration appConfig) {
		StringBuilder builder = new StringBuilder();
		builder.append(appConfig.getString(Constants.PATH_XML_OUTPUT));
		builder.append(Constants.PATH_SEPARATOR + "ServiceC2");
		builder.append(Constants.PATH_SEPARATOR + "service_C2_");
		builder.append(new SimpleDateFormat(Constants.XML_PATH_DATE_SUFFIX).format(new Date()) + ".xml");
		return builder.toString();
	}

}
