package demo.dao;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import demo.constant.Constants;
import demo.model.Day;
import demo.model.Employee;
import demo.model.HalfYear;
import demo.model.HalfYearPeriod;
import demo.model.Month;
import demo.model.Quarter;
import demo.model.QuarterPeriod;
import demo.model.Year;

@Repository
public class DaoImp extends BaseDao<T> implements Dao<T> {
	private static Logger log = LoggerFactory.getLogger("demo.dao.DaoImp");

	public List<Day> populateDay(List<Employee> listEmployees, List<Day> listDays) {
		String nameMethod = "populateDay";
		List<Day> resultList = new ArrayList<>();
		Day day = null;
		for (Employee empl : listEmployees) {
			LocalDate begin = empl.getDataOfEmployment();
			LocalDate defaultDate = LocalDate.now();
			LocalDate last = LocalDate.now().plusDays(1);
			if (empl.getDataDismissal() != null) {
				last = empl.getDataDismissal().plusDays(1);
			}
			if (begin == null) {
				if (empl.getDataDismissal() != null) {
					defaultDate = empl.getDataDismissal();
				}
				log.warn(nameMethod + Constants.TWO_PARAMETERS, Constants.EMPLOYEE, empl.getName(), "data employeement",
						begin, "made for the method - 'defaultDate'", defaultDate);
				begin=defaultDate;

			}
			for (LocalDate currentDate = begin; currentDate.isBefore(last); currentDate = currentDate.plusDays(1)) {
				day = getDayWhithCurrentDate(resultList, currentDate, empl);
				if (day == null) {
					day = getDayWhithCurrentDate(listDays, currentDate, empl);
				}
				if (day == null) {
					resultList.add(new Day(currentDate, empl));
				}
			}
		}
		return resultList;
	}

	public List<Month> populateMonth(List<Day> listDays, List<Month> listMonth) {
		List<Month> resultList = new ArrayList<>();
		java.time.YearMonth currentDate = null;
		Month month = null;
		for (Day day : listDays) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
			String line = day.getDate().toString().substring(0, 7);
			currentDate = YearMonth.parse(line, formatter);
			month = getMonthWhithCurrentDate(resultList, currentDate, day);
			if (month == null) {
				month = getMonthWhithCurrentDate(listMonth, currentDate, day);
			}
			if (month == null) {
				resultList.add(new Month(currentDate, day));
			}
		}

		return resultList;
	}

	public List<Quarter> populateQuarter(List<Month> listMonth, List<Quarter> listQuarter) {
		String nameMethod = "populateQuarter";
		List<Quarter> resultList = new ArrayList<>();
		QuarterPeriod currentDate = null;
		Quarter quarter = null;
		for (Month month : listMonth) {
			currentDate = month.getQuarter();
			quarter = getQuarterWhithCurrentDate(resultList, currentDate, month);
			if (quarter == null) {
				quarter = getQuarterWhithCurrentDate(listQuarter, currentDate, month);
			}
			if (quarter == null) {
				resultList.add(new Quarter(currentDate, month));
			}
		}

		return resultList;
	}

	public List<HalfYear> populateHalfYear(List<Quarter> listQuarter, List<HalfYear> listHalfYear) {
		String nameMethod = "populateHalfYear";
		List<HalfYear> resultList = new ArrayList<>();
		HalfYearPeriod currentDate = null;
		HalfYear halfYear = null;
		for (Quarter quarter : listQuarter) {
			currentDate = quarter.getHalfYear();
			halfYear = getHalfYearWhithCurrentDate(resultList, currentDate, quarter);
			if (halfYear == null) {
				halfYear = getHalfYearWhithCurrentDate(listHalfYear, currentDate, quarter);
			}
			if (halfYear == null) {
				resultList.add(new HalfYear(currentDate, quarter));
			}
		}

		return resultList;
	}

	public List<Year> populateYear(List<HalfYear> listHalfYear, List<Year> listYear) {
		String nameMethod = "populateYear";
		List<Year> resultList = new ArrayList<>();
		java.time.Year currentDate = null;
		Year year = null;
		for (HalfYear halfYear : listHalfYear) {
			currentDate = halfYear.getDate().getYearJavaTime();
			year = getYearWhithCurrentDate(resultList, currentDate, halfYear);
			if (year == null) {
				year = getYearWhithCurrentDate(listYear, currentDate, halfYear);
			}
			if (year == null) {
				resultList.add(new Year(currentDate, halfYear));
			}
		}

		return resultList;
	}

	private Day getDayWhithCurrentDate(List<Day> listDays, LocalDate date, Employee employee) {
		Day result = null;
		if (isContains(listDays, date)) {
			Day day = listDays.stream().filter(e -> date.equals(e.getDate())).findFirst().get();
			if (!day.getEmployeeListAll().contains(employee)) {
				day.setList(employee);
			}
			result = day;
		}

		return result;
	}

	private Month getMonthWhithCurrentDate(List<Month> listMonth, java.time.YearMonth date, Day day) {
		Month result = null;
		if (isContains(listMonth, date)) {
			Month month = listMonth.stream().filter(e -> date.equals(e.getDate())).findFirst().get();
			if (!month.getDayListAll().contains(day)) {
				month.setList(day);
			}
			result = month;
		}
		return result;
	}

	private Quarter getQuarterWhithCurrentDate(List<Quarter> listQuarter, QuarterPeriod date, Month month) {
		Quarter result = null;
		if (isContains(listQuarter, date)) {
			Quarter quarter = listQuarter.stream().filter(e -> date.equals(e.getDate())).findFirst().get();
			if (!quarter.getMonthListAll().contains(month)) {
				quarter.setList(month);
			}
			result = quarter;
		}
		return result;
	}

	private HalfYear getHalfYearWhithCurrentDate(List<HalfYear> listHalfYear, HalfYearPeriod date, Quarter quarter) {
		HalfYear result = null;
		if (isContains(listHalfYear, date)) {
			HalfYear halfYear = listHalfYear.stream().filter(e -> date.equals(e.getDate())).findFirst().get();
			if (!halfYear.getQuarterListAll().contains(quarter)) {
				halfYear.setList(quarter);
			}
			result = halfYear;
		}
		return result;
	}

	private Year getYearWhithCurrentDate(List<Year> listYear, java.time.Year date, HalfYear halfYear) {
		Year result = null;
		if (isContains(listYear, date)) {
			Year year = listYear.stream().filter(e -> date.equals(e.getDate())).findFirst().get();
			if (!year.getHalfYearListAll().contains(halfYear)) {
				year.setList(halfYear);
			}
			result = year;
		}
		return result;
	}

	private boolean isContains(List<Day> listDays, LocalDate day) {
		List<LocalDate> list = listDays.stream().map(Day::getDate).collect(Collectors.toList());
		return list.contains(day);
	}

	private boolean isContains(List<Month> listMonth, java.time.YearMonth month) {
		List<java.time.YearMonth> list = listMonth.stream().map(Month::getDate).collect(Collectors.toList());
		return list.contains(month);
	}

	private boolean isContains(List<Quarter> listQuarter, QuarterPeriod quarter) {
		List<QuarterPeriod> list = listQuarter.stream().map(Quarter::getDate).collect(Collectors.toList());
		return list.contains(quarter);
	}

	private boolean isContains(List<HalfYear> listHalfYear, HalfYearPeriod halfYear) {
		List<HalfYearPeriod> list = listHalfYear.stream().map(HalfYear::getDate).collect(Collectors.toList());
		boolean result = list.contains(halfYear);
		return result;
	}

	private boolean isContains(List<Year> listYear, java.time.Year YearPeriod) {
		List<java.time.Year> list = listYear.stream().map(Year::getDate).collect(Collectors.toList());
		boolean result = list.contains(YearPeriod);
		return result;
	}
}
