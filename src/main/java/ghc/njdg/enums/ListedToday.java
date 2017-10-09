package ghc.njdg.enums;

public enum ListedToday {
	PARENT_TAG("CASES_LISTED_TODAY"),
	CIVIL("CASES_LISTED_TODAY_CIVIL"),
	CRIMINAL("CASES_LISTED_TODAY_CRIMINAL"),
	WRIT("CASES_LISTED_TODAY_WRIT"),
	TOTAL("CASES_LISTED_TODAY_TOTAL");
	
	private final String tagName;
	
	private ListedToday(String tagName) {
		this.tagName = tagName;
	}
	
	public String getXMLTag() {
		return this.tagName;
	}
}
