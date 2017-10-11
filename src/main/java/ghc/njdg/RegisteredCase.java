package ghc.njdg;

import org.apache.commons.lang.StringUtils;

public class RegisteredCase {
	private String caseType;
	private String caseNumber;
	private int year;
	private String longCaseNumber;
	private String regDate;

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNo) {
		String case_noWithYear = StringUtils.substringAfterLast(caseNo, " ");
		this.caseNumber = StringUtils.substringBefore(case_noWithYear, "/");
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getLongCaseNumber() {
		return this.longCaseNumber;
	}

	public void setLongCaseNumber(String longCaseNumber) {
		if (StringUtils.isBlank(caseNumber)) {
			this.setCaseNumber(longCaseNumber);
		}
		StringBuilder builder = new StringBuilder();
		builder.append(this.caseType);
		builder.append('/');
		builder.append(this.caseNumber);
		builder.append('/');
		builder.append(Integer.toString(this.year));
		this.longCaseNumber = builder.toString();
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RegisteredCase [caseType=");
		builder.append(caseType);
		builder.append(", caseNumber=");
		builder.append(caseNumber);
		builder.append(", year=");
		builder.append(year);
		builder.append(", longCaseNumber=");
		builder.append(longCaseNumber);
		builder.append(", regDate=");
		builder.append(regDate);
		builder.append("]");
		return builder.toString();
	}

}
