package demo.model;

import java.time.LocalDate;

public class HalfYearPeriod extends Period {

	public HalfYearPeriod(String name, Integer startOfPeriod, Integer endOfPeriod, Integer year) {
		super(name, startOfPeriod, endOfPeriod, year);
	}

	public HalfYearPeriod(Integer position, Integer startOfPeriod, Integer endOfPeriod, Integer year) {
		super(position.toString() + "H" + "-" + year, startOfPeriod, endOfPeriod, year);
		setPosition(position);
	}

	public int getHalfYearValue() {
		return getPosition();
	}

	public java.time.Year getYearJavaTime() {
		return java.time.Year.of(this.getYear());
	}

	public static HalfYearPeriod now() {
		Integer year = LocalDate.now().getYear();
		int monthInt = LocalDate.now().getMonthValue();
		QuarterPeriod quarter = QuarterPeriod.createQuarter(year, monthInt);
		return createHalfYear(year, quarter.getQuarterValue());
	}

	public static HalfYearPeriod getHalfYearPeriod(LocalDate date) {
		Integer year = date.getYear();
		QuarterPeriod quarter = QuarterPeriod.createQuarter(year, date.getMonthValue());
		return createHalfYear(year, quarter.getQuarterValue());
	}

	public static HalfYearPeriod createHalfYear(int yearInt, int quarterInt) {

		Integer position = 0;
		Integer startOfPeriod;
		Integer endOfPeriod;
		Integer year = yearInt;
		int quarter = quarterInt;
		if (0 < quarter && quarter < 3) {
			position = 1;
			startOfPeriod = 1;
			endOfPeriod = 6;
		} else if (2 < quarter && quarter < 5) {
			position = 2;
			startOfPeriod = 7;
			endOfPeriod = 12;
		} else {
			position = null;
			startOfPeriod = null;
			endOfPeriod = null;
		}

		return new HalfYearPeriod(position, startOfPeriod, endOfPeriod, year);
	}
}
