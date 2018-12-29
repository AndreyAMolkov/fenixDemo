package demo.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
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

public class HalfYear implements DaoEntity {
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
	private HalfYearPeriod date;
	@Transient
	private List<Employee> employeeOutOfWorkListAll;
	@Transient
	private List<Employee> employeeCurrentListAll;

	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Day> dayListAll;
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Month> monthListAll;
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Quarter> quarterListAll;

	public HalfYear() {
		initList();
	}

	public HalfYear(HalfYearPeriod halfYear, Quarter quarter) {
		initList();
		setDate(halfYear);
		setList(quarter);
	}

	private void initList() {
		this.dayListAll = new ArrayList<>();
		this.monthListAll = new ArrayList<>();
		this.quarterListAll = new ArrayList<>();
		this.storeListAll = new ArrayList<>();
		this.cityListAll = new ArrayList<>();
		this.employeeListAll = new ArrayList<>();
		this.coachListAll = new ArrayList<>();
		this.employeeOutOfWorkListAll = new ArrayList<>();
		this.employeeCurrentListAll = new ArrayList<>();
	}

	public void setList(Quarter quarter) {
		setQuarterListAll(quarter);
		setCoachListAll(quarter.getCoachListAll());
		setCityListAll(quarter.getCityListAll());
		setStoreListAll(quarter.getStoreListAll());
		setEmployeeListAll(quarter.getEmployeeListAll());
		setEntities(quarter);
	}

	public void setEntities(Quarter quarter) {
		quarter.getEmployeeListAll().forEach(e -> e.getCoach().setQuarterListAll(quarter));
		quarter.getEmployeeListAll().forEach(e -> e.getCity().setQuarterListAll(quarter));
		quarter.getEmployeeListAll().forEach(e -> e.getStore().setQuarterListAll(quarter));
	}

	@Override
	public void setWorkList(Employee employee) {
		HalfYearPeriod dateEmpl = null;
		if (employee.getDataDismissal() != null) {
			dateEmpl = HalfYearPeriod.getHalfYearPeriod(employee.getDataDismissal());
		}

		if (dateEmpl != null && dateEmpl.equals(date)) {
			setEmployeeOutOfWorkListAll(employee);
		} else {
			setEmployeeCurrentListAll(employee);
		}

	}

	public HalfYearPeriod getDate() {
		return date;
	}

	public void setDate(HalfYearPeriod date) {
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
		// empty

	}

	@Override
	public String getName() {
		return getDate().toString();
	}

	@Override
	public List<Day> getDayListAll() {
		return dayListAll;
	}

	@Override
	public List<Month> getMonthListAll() {
		return monthListAll;
	}

	@Override
	public List<HalfYear> getHalfYearListAll() {
		return Collections.emptyList();
	}

	@Override
	public List<Quarter> getQuarterListAll() {
		return quarterListAll;
	}

	@Override
	public List<Employee> getEmployeeOutOfWorkListAll() {
		return employeeOutOfWorkListAll;
	}

	@Override
	public List<Employee> getEmployeeCurrentListAll() {
		return employeeCurrentListAll;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
