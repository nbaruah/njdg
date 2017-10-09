package ghc.njdg;

public class RegisteredCase {
	private String caseType;
	private int caseNumber;
	private int year;
	private String longCaseNumber;
	private String regDate;

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public int getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(int caseNumber) {
		this.caseNumber = caseNumber;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getLongCaseNumber() {
		return longCaseNumber;
	}

	public void setLongCaseNumber(String longCaseNumber) {
		this.longCaseNumber = longCaseNumber;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

}
