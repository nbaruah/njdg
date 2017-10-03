package ghc.njdg;

public enum TotalJudges {
	PARENT_TAG("TOTALJUDGES"),
	TOTAL("TOTAL_JUDGE");

	
	private final String tagName;
	
	private TotalJudges(String tagName) {
		this.tagName = tagName;
	}
	
	public String getXMLTag() {
		return this.tagName;
	}
}
