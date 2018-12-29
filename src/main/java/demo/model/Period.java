package demo.model;

import java.time.YearMonth;

abstract class Period {
	@Override
	public String toString() {
		return name;
	}

	private YearMonth begin;
	private YearMonth end;
	private String name;
	private int position;

	public Period(String name, Integer startOfPeriod, Integer endOfPeriod, Integer year) {
		setName(name);
		setBegin(YearMonth.of(year, startOfPeriod));
		setEnd(YearMonth.of(year, endOfPeriod));
	}

	public Period(YearMonth begin, YearMonth end) {
		setBegin(begin);
		setEnd(end);
	}

	public boolean isBefore(Period period) {
		boolean result = false;
		if (period.getEnd().isBefore(getEnd()) && !period.equals(this)) {
			result = true;
		}
		return result;
	}

	public boolean isAfter(Period period) {
		return !isBefore(period);
	}

	public YearMonth getBegin() {
		return begin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBegin(YearMonth begin) {
		this.begin = begin;
	}

	public YearMonth getEnd() {
		return end;
	}

	public void setEnd(YearMonth end) {
		this.end = end;
	}
	public int getYear() {
		return getBegin().getYear();
	}

	public int getPosition() {
		return position;
	}

	protected void setPosition(int position) {
		this.position = position;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((begin == null) ? 0 : begin.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + position;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Period other = (Period) obj;
		if (begin == null) {
			if (other.begin != null)
				return false;
		} else if (!begin.equals(other.begin))
			return false;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (position != other.position)
			return false;
		return true;
	}
	
}
