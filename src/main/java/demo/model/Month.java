package demo.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import demo.constant.Constants;
import demo.dao.DaoEntity;

@Entity(name = "Month")
@Table(name = "months")
public class Month implements DaoEntity {
	private static Logger log = LoggerFactory.getLogger("demo.dao.Month");
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Store> storeListAll;
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Employee> employeeListAll;
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Coach> coachListAll;
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<City> cityListAll;
	private java.time.YearMonth date;
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Day> dayListAll;

	@Transient
	private List<Employee> employeeOutOfWorkListAll;
	@Transient
	private List<Employee> employeeCurrentListAll;

	public Month() {
		initList();
	}

	public Month(java.time.YearMonth yearMonth, Day day) {
		initList();
		setDate(yearMonth);
		setList(day);
	}

	private void initList() {
		this.dayListAll = new ArrayList<>();
		this.storeListAll = new ArrayList<>();
		this.cityListAll = new ArrayList<>();
		this.employeeListAll = new ArrayList<>();
		this.coachListAll = new ArrayList<>();
		this.employeeOutOfWorkListAll = new ArrayList<>();
		this.employeeCurrentListAll = new ArrayList<>();
	}

	public void setList(Day day) {
		setDayListAll(day);
		setCoachListAll(day.getCoachListAll());
		setCityListAll(day.getCityListAll());
		setStoreListAll(day.getStoreListAll());
		setEmployeeListAll(day.getEmployeeListAll());
		setEntities(day);
	}
	public void setEntities(Day day) {
	day.getEmployeeListAll().forEach(e->e.getCoach().setDayListAll(day));
	day.getEmployeeListAll().forEach(e->e.getCity().setDayListAll(day));
	day.getEmployeeListAll().forEach(e->e.getStore().setDayListAll(day));
	}

	@Override
	public void setWorkList(Employee employee) {

		LocalDate dateEmpl = employee.getDataDismissal();
		if (dateEmpl != null && dateEmpl.getMonth().equals(date.getMonth())) {
			setEmployeeOutOfWorkListAll(employee);
		} else {
			setEmployeeCurrentListAll(employee);
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public List<Store> getStoreListAll() {
		return storeListAll;
	}

	@Override
	public List<City> getCityListAll() {
		return cityListAll;
	}

	@Override
	public List<Employee> getEmployeeListAll() {
		return employeeListAll;
	}

	@Override
	public List<Coach> getCoachListAll() {
		return coachListAll;
	}

	@Override
	public void setName(String localDataDDMMYYYY) {
		String nameMethod = "setName";
		YearMonth yearMonth = null;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		try {
			yearMonth = YearMonth.parse(localDataDDMMYYYY, formatter);
		} catch (DateTimeParseException e) {
			log.debug(nameMethod + Constants.THREE_PARAMETERS, "Error find date for line = ", localDataDDMMYYYY,
					"made default value = ", yearMonth, Constants.MESSAGE, e);
		}
		if (yearMonth != null) {
			setDate(yearMonth);
		}
	}

	private void setDate(java.time.YearMonth yearMonth) {
		this.date = yearMonth;
	}

	@Override
	public String getName() {
		return getDate().toString();
	}

	public java.time.YearMonth getDate() {
		return date;
	}

	@Override
	public List<Day> getDayListAll() {
		return dayListAll;
	}

	@Override
	public List<Employee> getEmployeeOutOfWorkListAll() {

		return employeeOutOfWorkListAll;
	}

	@Override
	public List<Employee> getEmployeeCurrentListAll() {

		return employeeCurrentListAll;
	}
	public QuarterPeriod getQuarter() {
		Integer position = 0;
		Integer startOfPeriod;
		Integer endOfPeriod;
		Integer year = getDate().getYear();
		int month = getDate().getMonthValue();
		if (0 < month && month < 4) {
			position=1;	
			startOfPeriod=1;
			endOfPeriod=3;
		}else if(3 < month && month < 7) {
			position=2;	
			startOfPeriod=4;
			endOfPeriod=6;
		}else if(6 < month && month < 10) {
			position=3;	
			startOfPeriod=7;
			endOfPeriod=9;
		}else if(9 < month && month < 13) {
			position=4;	
			startOfPeriod=10;
			endOfPeriod=12;
		}else {
			position=null;	
			startOfPeriod=null;
			endOfPeriod=null;
		}
		
		return new QuarterPeriod(position,startOfPeriod,endOfPeriod,year);
	}

	@Override
	public List<Month> getMonthListAll() {
		return Arrays.asList(this);
	}

	@Override
	public List<HalfYear> getHalfYearListAll() {
		return null;
	}

	@Override
	public List<Quarter> getQuarterListAll() {
		return null;
	}
	public boolean dateIsBetween(LocalDate dateEndEmpl) {
		boolean result = false;
		if (dateEndEmpl == null) {
			result = true;
		} else if (getDate() != null
				&& YearMonth.of(dateEndEmpl.getYear(), dateEndEmpl.getMonth()).isAfter(getDate())) {
			result = true;
		}

		return result;
	}
}
