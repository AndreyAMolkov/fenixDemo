package demo.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import demo.dao.DaoEntity;

public class Quarter implements DaoEntity {
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
	private QuarterPeriod date;

	@Transient
	private List<Employee> employeeOutOfWorkListAll;
	@Transient
	private List<Employee> employeeCurrentListAll;

//	@ManyToMany(fetch = FetchType.EAGER)
//	@Fetch(value = FetchMode.SUBSELECT)
//	private List<Day> dayListAll;
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Month> monthListAll;

	public Quarter() {
		initList();
	}

	public Quarter(QuarterPeriod quarter, Month month) {
		initList();
		setDate(quarter);
		setList(month);
	}

	private void initList() {
		this.monthListAll = new ArrayList<>();
		this.storeListAll = new ArrayList<>();
		this.cityListAll = new ArrayList<>();
		this.employeeListAll = new ArrayList<>();
		this.coachListAll = new ArrayList<>();
		this.employeeOutOfWorkListAll = new ArrayList<>();
		this.employeeCurrentListAll = new ArrayList<>();
	}

	public void setList(Month month) {
		setMonthListAll(month);
		setCoachListAll(month.getCoachListAll());
		setCityListAll(month.getCityListAll());
		setStoreListAll(month.getStoreListAll());
		setEmployeeListAll(month.getEmployeeListAll());
		setEntities(month);
	}

	public void setEntities(Month month) {
		month.getEmployeeListAll().forEach(e -> e.getCoach().setMonthListAll(month));
		month.getEmployeeListAll().forEach(e -> e.getCity().setMonthListAll(month));
		month.getEmployeeListAll().forEach(e -> e.getStore().setMonthListAll(month));
	}

	@Override
	public void setWorkList(Employee employee) {
		QuarterPeriod dateEmpl = null;
		if (employee.getDataDismissal() != null) {
			dateEmpl = QuarterPeriod.getQuqarter(employee.getDataDismissal());
		}

		if (dateEmpl != null && dateEmpl.equals(date)) {
			setEmployeeOutOfWorkListAll(employee);
		} else {
			setEmployeeCurrentListAll(employee);
		}

	}

	public HalfYearPeriod getHalfYear() {
		Integer year = getDate().getYear();
		int quarterInt = getDate().getQuarterValue();
		return HalfYearPeriod.createHalfYear(year, quarterInt);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public QuarterPeriod getDate() {
		return date;
	}

	public void setDate(QuarterPeriod date) {
		this.date = date;
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
	public void setName(String name) {
//empty yet	
	}

	@Override
	public String getName() {
		return getDate().toString();
	}

	@Override
	public List<Day> getDayListAll() {
		return null;
	}

	@Override
	public List<Employee> getEmployeeOutOfWorkListAll() {
		return employeeOutOfWorkListAll;
	}

	@Override
	public List<Employee> getEmployeeCurrentListAll() {
		return employeeCurrentListAll;
	}

	@Override
	public List<Month> getMonthListAll() {
		return monthListAll;
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
				&& YearMonth.of(dateEndEmpl.getYear(), dateEndEmpl.getMonth()).isAfter(getDate().getEnd())) {
			result = true;
		}

		return result;
	}
}
