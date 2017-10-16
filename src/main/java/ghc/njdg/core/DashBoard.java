package ghc.njdg.core;

import java.util.LinkedHashMap;
import java.util.Map;
import ghc.njdg.enums.LastMonthFiled;
import ghc.njdg.enums.ListedToday;
import ghc.njdg.enums.PendingForReg;
import ghc.njdg.enums.UnderObjection;
import ghc.njdg.enums.UnderRejection;

public class DashBoard {
	private Map<String, Integer> lastMonthFiledCases;
	private Map<String, Integer> underObjectionCases;
	private Map<String, Integer> underRejectionCases;
	private Map<String, Integer> pendingForReg;
	private Map<String, Integer> todayListedCases;
	private int totalJudges;
	
	public DashBoard() {
		lastMonthFiledCases = new LinkedHashMap<String, Integer>();
		underObjectionCases = new LinkedHashMap<String, Integer>();
		underRejectionCases = new LinkedHashMap<String, Integer>();
		pendingForReg = new LinkedHashMap<String, Integer>();
		todayListedCases = new LinkedHashMap<String, Integer>();
	}

	public Map<String, Integer> getLastMonthFiledCases() {
		return lastMonthFiledCases;
	}

	public void setLastMonthFiledCases(int civil, int criminal, int writ) {
    	this.lastMonthFiledCases.put(LastMonthFiled.CIVIL.getXMLTag(), civil);
    	this.lastMonthFiledCases.put(LastMonthFiled.CRIMINAL.getXMLTag(), criminal);
    	this.lastMonthFiledCases.put(LastMonthFiled.WRIT.getXMLTag(), writ);
    	this.lastMonthFiledCases.put(LastMonthFiled.TOTAL.getXMLTag(), civil + criminal + writ);
	}

	public Map<String, Integer> getUnderObjectionCases() {
		return underObjectionCases;
	}

	public void setUnderObjectionCases(int civil, int criminal, int writ) {
		this.underObjectionCases.put(UnderObjection.CIVIL.getXMLTag(), civil);
		this.underObjectionCases.put(UnderObjection.CRIMINAL.getXMLTag(), criminal);
		this.underObjectionCases.put(UnderObjection.WRIT.getXMLTag(), writ);
		this.underObjectionCases.put(UnderObjection.TOTAL.getXMLTag(), civil + criminal + writ);
	}

	public Map<String, Integer> getUnderRejectionCases() {
		return underRejectionCases;
	}

	public void setUnderRejectionCases(int civil, int criminal, int writ) {
		this.underRejectionCases.put(UnderRejection.CIVIL.getXMLTag(), civil);
		this.underRejectionCases.put(UnderRejection.CRIMINAL.getXMLTag(), criminal);
		this.underRejectionCases.put(UnderRejection.WRIT.getXMLTag(), writ);
		this.underRejectionCases.put(UnderRejection.TOTAL.getXMLTag(), civil + criminal + writ);
	}

	public Map<String, Integer> getPendingForReg() {
		return pendingForReg;
	}

	public void setPendingForReg(int civil, int criminal, int writ) {
		this.pendingForReg.put(PendingForReg.CIVIL.getXMLTag(), civil);
		this.pendingForReg.put(PendingForReg.CRIMINAL.getXMLTag(), criminal);
		this.pendingForReg.put(PendingForReg.WRIT.getXMLTag(), writ);
		this.pendingForReg.put(PendingForReg.TOTAL.getXMLTag(), civil + criminal + writ);
	}

	public int getTotalJudges() {
		return totalJudges;
	}

	public void setTotalJudges(int totalJudges) {
		this.totalJudges = totalJudges;
	}

	public Map<String, Integer> getTodayListedCases() {
		return todayListedCases;
	}

	public void setTodayListedCases(int civil, int criminal, int writ) {
		this.todayListedCases.put(ListedToday.CIVIL.getXMLTag(), civil);
		this.todayListedCases.put(ListedToday.CRIMINAL.getXMLTag(), criminal);
		this.todayListedCases.put(ListedToday.WRIT.getXMLTag(), writ);
		this.todayListedCases.put(ListedToday.TOTAL.getXMLTag(), civil + criminal + writ);
	}
	
}
