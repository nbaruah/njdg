package ghc.njdg.core;

public class Case implements Comparable<Case>{
	private String type;
	private int year;
	private int totalCount;
	private int seniorCount;
	private int womenCount;
	private int lastMonthDisposal;
		
	public Case() {
		super();
	}

	public Case(String type, int year, int totalCount) {
		this.type = type;
		this.year = year;
		this.totalCount = totalCount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getSeniorCount() {
		return seniorCount;
	}

	public void setSeniorCount(int seniorCount) {
		this.seniorCount = seniorCount;
	}

	public int getWomenCount() {
		return womenCount;
	}

	public void setWomenCount(int womenCount) {
		this.womenCount = womenCount;
	}

	public int getLastMonthDisposal() {
		return lastMonthDisposal;
	}

	public void setLastMonthDisposal(int lastMonthDisposal) {
		this.lastMonthDisposal = lastMonthDisposal;
	}

	public int compareTo(Case other) {
		return Integer.compare(this.year, other.year);
	}

}
