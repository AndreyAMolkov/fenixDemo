package demo.model;

import java.time.LocalDate;
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

public class Year implements DaoEntity {
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
	private java.time.Year date;

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
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<HalfYear> halfYearListAll;

	public Year() {
		initList();
	}

	public Year(java.time.Year currentYear, HalfYear halfYear) {
		initList();
		setDate(currentYear);
		setList(halfYear);
	}

	private void initList() {
		this.dayListAll = new ArrayList<>();
		this.monthListAll = new ArrayList<>();
		this.quarterListAll = new ArrayList<>();
		this.halfYearListAll = new ArrayList<>();
		this.storeListAll = new ArrayList<>();
		this.cityListAll = new ArrayList<>();
		this.employeeListAll = new ArrayList<>();
		this.coachListAll = new ArrayList<>();

		this.employeeOutOfWorkListAll = new ArrayList<>();
		this.employeeCurrentListAll = new ArrayList<>();
	}

	public void setList(HalfYear halfYear) {
		setHalfYearListAll(halfYear);
		setCoachListAll(halfYear.getCoachListAll());
		setCityListAll(halfYear.getCityListAll());
		setStoreListAll(halfYear.getStoreListAll());
		setEmployeeListAll(halfYear.getEmployeeListAll());

	}
	public void setEntities(HalfYear halfYear) {
		halfYear.getEmployeeListAll().forEach(e -> e.getCoach().setHalfYearListAll(halfYear));
		halfYear.getEmployeeListAll().forEach(e -> e.getCity().setHalfYearListAll(halfYear));
		halfYear.getEmployeeListAll().forEach(e -> e.getStore().setHalfYearListAll(halfYear));
	}
	public List<HalfYear> getHalfYearListAll() {
		return halfYearListAll;
	}

	@Override
	public void setHalfYearListAll(List<HalfYear> halfYearListAll) {
		this.halfYearListAll = halfYearListAll;
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
//empty
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

	public java.time.Year getDate() {
		return date;
	}

	public void setDate(java.time.Year date) {
		this.date = date;
	}

	public boolean dateIsBetween(LocalDate dateEndEmpl) {
		boolean result = false;
		if (dateEndEmpl == null) {
			result = true;
		} else if (getDate() != null && dateEndEmpl.getYear() > getDate().getValue()) {
			result = true;
		}

		return result;
	}
}
