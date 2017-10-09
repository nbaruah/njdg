package ghc.njdg.enums;

public enum UnderObjection {
	PARENT_TAG("CASES_UNDER_OBJECTION"),
	CIVIL("CASES_UNDER_OBJECTION_CIVIL"),
	CRIMINAL("CASES_UNDER_OBJECTION_CRIMINAL"),
	WRIT("CASES_UNDER_OBJECTION_WRIT"),
	TOTAL("CASES_UNDER_OBJECTION_TOTAL");
	
	private final String tagName;
	
	private UnderObjection(String tagName) {
		this.tagName = tagName;
	}
	
	public String getXMLTag() {
		return this.tagName;
	}
}
