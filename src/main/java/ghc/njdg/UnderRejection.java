package ghc.njdg;

public enum UnderRejection {
	PARENT_TAG("CASES_UNDER_REJECTION"),
	CIVIL("CASES_UNDER_REJECTION_CIVIL"),
	CRIMINAL("CASES_UNDER_REJECTION_CRIMINAL"),
	WRIT("CASES_UNDER_REJECTION_WRIT"),
	TOTAL("CASES_UNDER_REJECTION_TOTAL");
	
	private final String tagName;
	
	private UnderRejection(String tagName) {
		this.tagName = tagName;
	}
	
	public String getXMLTag() {
		return this.tagName;
	}
}
