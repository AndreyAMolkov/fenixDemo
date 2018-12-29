package demo.model;

import java.time.LocalDate;
import java.time.YearMonth;

public class QuarterPeriod extends Period {

	public QuarterPeriod(Integer position, Integer startOfPeriod, Integer endOfPeriod, Integer year) {
		super(position.toString() + "Q" + "-" + year, startOfPeriod, endOfPeriod, year);
		setPosition(position);
	}

	public int getQuarterValue() {
		return getPosition();
	}

	public QuarterPeriod minusQuarters(int i) {
		YearMonth montfEndCurrent = getEnd();
		YearMonth montfBegin = null;
		YearMonth montfEnd = null;

		montfEnd = montfEndCurrent.minusMonths(i * 3);
		montfBegin = montfEnd.minusMonths(3);
		Integer year = montfEnd.getYear();

		Integer position = 0;

		int month = montfEnd.getMonthValue();
		if (0 < month && month < 4) {
			position = 1;
		} else if (3 < month && month < 7) {
			position = 2;
		} else if (6 < month && month < 10) {
			position = 3;
		} else if (10 < month && month < 13) {
			position = 4;

		} else {
			position = null;
		}

		return new QuarterPeriod(position, montfBegin.getMonthValue(), montfEnd.getMonthValue(), year);

	}

	public static QuarterPeriod now() {
		Integer year = LocalDate.now().getYear();
		int month = LocalDate.now().getMonthValue();
		return createQuarter(year, month);
	}

	public static QuarterPeriod getQuqarter(LocalDate date) {
		Integer year = date.getYear();
		int month = date.getMonthValue();
		return createQuarter(year, month);
	}

	public static QuarterPeriod createQuarter(int yearInt, int monthInt) {

		Integer position = 0;
		Integer startOfPeriod;
		Integer endOfPeriod;
		Integer year = yearInt;
		int month = monthInt;
		if (0 < month && month < 4) {
			position = 1;
			startOfPeriod = 1;
			endOfPeriod = 3;
		} else if (3 < month && month < 7) {
			position = 2;
			startOfPeriod = 4;
			endOfPeriod = 6;
		} else if (6 < month && month < 10) {
			position = 3;
			startOfPeriod = 7;
			endOfPeriod = 9;
		} else if (9 < month && month < 13) {
			position = 4;
			startOfPeriod = 10;
			endOfPeriod = 12;
		} else {
			position = null;
			startOfPeriod = null;
			endOfPeriod = null;
		}

		return new QuarterPeriod(position, startOfPeriod, endOfPeriod, year);
	}
}
