package ghc.njdg.core;

public class FiledCase {
	private String caseType;
	private String filingNo;
	private int filingYear;
	private String longFilingNum;
	private String filingDate;

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getFilingNo() {
		return filingNo;
	}

	public void setFilingNo(String filingNo) {
		this.filingNo = filingNo;
	}

	public int getFilingYear() {
		return filingYear;
	}

	public void setFilingYear(int filingYear) {
		this.filingYear = filingYear;
	}

	public String getLongFilingNum() {
		return longFilingNum;
	}

	public void setLongFilingNum() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.caseType);
		builder.append("/");
		builder.append(this.filingNo);
		builder.append("/");
		builder.append(this.filingYear);
		this.longFilingNum = builder.toString();
	}

	public String getFilingDate() {
		return filingDate;
	}

	public void setFilingDate(String filingDate) {
		this.filingDate = filingDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FiledCase [caseType=");
		builder.append(caseType);
		builder.append(", filingNo=");
		builder.append(filingNo);
		builder.append(", filingYear=");
		builder.append(filingYear);
		builder.append(", longFilingNum=");
		builder.append(longFilingNum);
		builder.append(", filingDate=");
		builder.append(filingDate);
		builder.append("]");
		return builder.toString();
	}

}
