package ghc.njdg.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ghc.njdg.enums.LastMonthFiled;
import ghc.njdg.enums.ListedToday;
import ghc.njdg.enums.PendingForReg;
import ghc.njdg.enums.TotalJudges;
import ghc.njdg.enums.UnderObjection;
import ghc.njdg.enums.UnderRejection;
import ghc.njdg.exeption.WebServiceProcessException;

public class WebServiceA implements WebServiceProcess{
	
	private Connection conn;
	private File xmlOutputFile;
	private Statement st;
	private ResultSet rs;
	private DashBoard dashboard;

	private static final Logger logger = LogManager.getLogger(WebServiceA.class);

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
			throw new WebServiceProcessException("Webservice A, Error while conecting to Database", e);
		}
	}

	@Override
	public void executeQuery(String query) throws WebServiceProcessException {
		if (StringUtils.isBlank(query)) {
			throw new WebServiceProcessException("Webservice A, Query String is empty.");
		}
		try {
			st = conn.createStatement();
			logger.info("Executing query: " + query);
			rs = st.executeQuery(query);
			logger.info("Query executed Successfuly!!");
		} catch (SQLException e) {
			throw new WebServiceProcessException("Webservice A, Error while executing query.", e);
		}

	}

	@Override
	public void parseResultSet() throws WebServiceProcessException {
		dashboard = new DashBoard();
		try {
			rs.next();
			dashboard.setLastMonthFiledCases(rs.getInt(1), rs.getInt(2), rs.getInt(3)); 
			rs.next();
			dashboard.setUnderObjectionCases(rs.getInt(1), rs.getInt(2), rs.getInt(3));
			dashboard.setUnderRejectionCases(0, 0, 0);
			rs.next();
			dashboard.setPendingForReg(rs.getInt(1), rs.getInt(2), rs.getInt(3));
			rs.next();
			dashboard.setTodayListedCases(rs.getInt(1), rs.getInt(2), rs.getInt(3));
			st.close();
			conn.close();
		} catch (SQLException e) {
			throw new WebServiceProcessException("Webservice A, Error while Parsing result set", e);
		}

	}

	@Override
	public void writeXML() throws WebServiceProcessException {
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer;
		try {
			writer = factory.createXMLStreamWriter(new FileWriter(xmlOutputFile));
			writer.writeStartDocument();
			writer.writeStartElement(Constants.SERVICE_A_ROOT_ELEM);
			writeMap(writer, dashboard.getLastMonthFiledCases(), LastMonthFiled.PARENT_TAG.getXMLTag());
			writeMap(writer, dashboard.getUnderObjectionCases(), UnderObjection.PARENT_TAG.getXMLTag());
			writeMap(writer, dashboard.getUnderRejectionCases(), UnderRejection.PARENT_TAG.getXMLTag());
			writeMap(writer, dashboard.getPendingForReg(), PendingForReg.PARENT_TAG.getXMLTag());
			writeTotalJudges(writer);
			writeMap(writer, dashboard.getTodayListedCases(), ListedToday.PARENT_TAG.getXMLTag());
			writer.writeEndElement();
			writer.writeEndDocument();
			writer.flush();
			writer.close();
			logger.info("Successfuly written to file: " + this.xmlOutputFile.getPath());
		} catch (XMLStreamException | IOException e) {
			throw new WebServiceProcessException("Error while writing XML to file " + xmlOutputFile.getPath(), e);
		}
	}

	private void writeMap(XMLStreamWriter writer, Map<String, Integer> map, String parentTag) throws XMLStreamException {
		writer.writeCharacters(Constants.NEWLINE_SINGLE_TAB);
		writer.writeStartElement(parentTag);
		for (Map.Entry<String, Integer> element : map.entrySet()) {
			writer.writeCharacters(Constants.NEWLINE_DOUBLE_TAB);
			writer.writeStartElement(element.getKey());
			writer.writeCharacters(element.getValue().toString());
			writer.writeEndElement();
		}
		writer.writeCharacters(Constants.NEWLINE_SINGLE_TAB);
		writer.writeEndElement();
	}
	
	private void writeTotalJudges(XMLStreamWriter writer) throws XMLStreamException {
		writer.writeCharacters(Constants.NEWLINE_SINGLE_TAB);
		writer.writeStartElement(TotalJudges.PARENT_TAG.getXMLTag());
		writer.writeCharacters(Constants.NEWLINE_DOUBLE_TAB);
		writer.writeStartElement(TotalJudges.TOTAL.getXMLTag());
		writer.writeCharacters(Integer.toString(dashboard.getTotalJudges()));
		writer.writeEndElement();
		writer.writeCharacters(Constants.NEWLINE_SINGLE_TAB);
		writer.writeEndElement();
	}

	private String getXmlFilePath(Configuration appConfig) {
		StringBuilder builder = new StringBuilder();
		builder.append(appConfig.getString(Constants.PATH_XML_OUTPUT));
		builder.append(Constants.PATH_SEPARATOR + "ServiceA");
		builder.append(Constants.PATH_SEPARATOR + "service_A_");
		builder.append(new SimpleDateFormat(Constants.XML_PATH_DATE_SUFFIX).format(new Date()) + ".xml");
		return builder.toString();
	}
}
