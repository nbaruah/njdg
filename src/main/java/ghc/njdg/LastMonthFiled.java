package ghc.njdg;

public enum LastMonthFiled {
	PARENT_TAG("CASES_FILED_IN_LAST_MONTH"),
	CIVIL("CASES_FILED_LAST_MONTH_CIVIL"),
	CRIMINAL("CASES_FILED_LAST_MONTH_CRIMINAL"),
	WRIT("CASES_FILED_LAST_MONTH_WRIT"),
	TOTAL("CASES_FILED_LAST_MONTH_TOTAL");
	
	private final String tagName;
	
	private LastMonthFiled(String tagName) {
		this.tagName = tagName;
	}
	
	public String getXMLTag() {
		return this.tagName;
	}
}
