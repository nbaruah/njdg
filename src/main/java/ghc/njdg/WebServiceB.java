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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ghc.njdg.exeption.WebServiceProcessException;

public class WebServiceB implements WebServiceProcess {
	private Connection conn;
	private File xmlOutputFile;
	private Statement st;
	private ResultSet rs;
	private ArrayList<Case> pendingCases;

	private static final Logger logger = LogManager.getLogger(WebServiceB.class);

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
			throw new WebServiceProcessException("Webservice B, Error while conecting to Data base", e);
		}
	}

	@Override
	public void executeQuery(String query) throws WebServiceProcessException {
		if (StringUtils.isBlank(query)) {
			throw new WebServiceProcessException("Webservice B, Query String is empty.");
		}
		try {
			st = conn.createStatement();
			logger.debug("Executing query: " + query);
			rs = st.executeQuery(query);
			logger.info("Query executed Successfuly");
		} catch (SQLException e) {
			throw new WebServiceProcessException("Webservice B, Error while executing query.", e);
		}

	}

	@Override
	public void parseResultSet() throws WebServiceProcessException {
		pendingCases = new ArrayList<>();
		try {
			while (rs.next()) {
				Case c = new Case();
				c.setType(rs.getString(1));
				c.setYear(rs.getInt(2));
				c.setTotalCount(rs.getInt(3));
				pendingCases.add(c);
			}
			st.close();
			conn.close();
		} catch (SQLException e) {
			throw new WebServiceProcessException("Webservice B, Error while Parsing result set", e);
		}

	}

	@Override
	public void writeXML() throws WebServiceProcessException {
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer;
		try {
			writer = factory.createXMLStreamWriter(new FileWriter(xmlOutputFile));
			writer.writeStartDocument();
			for (Case c : pendingCases) {
				writeCase(writer, c);
			}
			writer.writeEndDocument();
			writer.flush();
			writer.close();
		} catch (XMLStreamException | IOException e) {
			throw new WebServiceProcessException("Error while writing XML to file " + xmlOutputFile.getPath(), e);
		}
	}

	private void writeCase(XMLStreamWriter writer, Case c) throws XMLStreamException {
		writer.writeCharacters(Constants.NEWLINE_SINGLE_TAB);
		writer.writeStartElement("CASE");
		formatCase(writer, "CASE_TYPE", c.getType());
		formatCase(writer, "CASE_YEAR", String.valueOf(c.getYear()));
		formatCase(writer, "TOTALCOUNT", String.valueOf(c.getTotalCount()));
		formatCase(writer, "SENIORCOUNT", String.valueOf(c.getSeniorCount()));
		formatCase(writer, "WOMEN_COUNT", String.valueOf(c.getWomenCount()));
		formatCase(writer, "DISPOSAL_LAST_MONTH", String.valueOf(c.getLastMonthDisposal()));
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
		builder.append(Constants.PATH_SEPARATOR + "ServiceB");
		builder.append(Constants.PATH_SEPARATOR + "service_B_");
		builder.append(new SimpleDateFormat(Constants.XML_PATH_DATE_SUFFIX).format(new Date()) + ".xml");
		return builder.toString();
	}
}
