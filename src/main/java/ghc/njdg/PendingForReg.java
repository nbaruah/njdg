package ghc.njdg;

public enum PendingForReg {
	PARENT_TAG("PENDING_FOR_REGISTRATION"),
	CIVIL("PENDING_FOR_REGISTRATION_CIVIL"),
	CRIMINAL("PENDING_FOR_REGISTRATION_CRIMINAL"),
	WRIT("PENDING_FOR_REGISTRATION_WRIT"),
	TOTAL("PENDING_FOR_REGISTRATION_TOTAL");
	
	private final String tagName;
	
	private PendingForReg(String tagName) {
		this.tagName = tagName;
	}
	
	public String getXMLTag() {
		return this.tagName;
	}
}
