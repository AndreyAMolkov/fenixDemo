package demo.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Cacheable;
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

@Cacheable
@Entity(name = "Day")
@Table(name = "days")
public class Day implements DaoEntity {
	private static Logger log = LoggerFactory.getLogger("demo.dao.Day");
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
	private LocalDate date;

	@Transient
	private List<Employee> employeeOutOfWorkListAll;
	@Transient
	private List<Employee> employeeCurrentListAll;

	public Day() {
		initList();
	}

	public Day(LocalDate currentDate, Employee empl) {
		initList();
		setDate(currentDate);
		setList(empl);
	}

	public void setList(Employee empl) {
		LocalDate date=empl.getDataDismissal();

		empl.setDayListAll(this);
		empl.getCoach().setDayListAll(this);
		empl.getCity().setDayListAll(this);
		empl.getStore().setDayListAll(this);	

		setEmployeeListAll(empl);
		setCoachListAll(empl.getCoach());
		setCityListAll(empl.getCity());
		setStoreListAll(empl.getStore());
	}

	private void initList() {
		this.storeListAll = new ArrayList<>();
		this.cityListAll = new ArrayList<>();
		this.employeeListAll = new ArrayList<>();
		this.coachListAll = new ArrayList<>();
		this.employeeOutOfWorkListAll = new ArrayList<>();
		this.employeeCurrentListAll = new ArrayList<>();
	}

	@Override
	public void setWorkList(Employee empl) {

		LocalDate dateEmpl = empl.getDataDismissal();
		if (dateEmpl != null && dateEmpl.equals(date)) {
			setEmployeeOutOfWorkListAll(empl);
		} else {
			setEmployeeCurrentListAll(empl);
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
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
		Day other = (Day) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date)) {
			return false;
		}
		return true;
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
		LocalDate localDate = null;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		try {
			localDate = LocalDate.parse(localDataDDMMYYYY, formatter);
		} catch (DateTimeParseException e) {
			log.debug(nameMethod + Constants.THREE_PARAMETERS, "Error finding date for line", localDataDDMMYYYY,
					"made default value", localDate, Constants.MESSAGE, e);
		}
		setDate(localDate);
	}

	@Override
	public String getName() {
		return date.toString();
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate day) {
		this.date = day;
	}

	@Override
	public List<Day> getDayListAll() {
		return null;
	}

	@Override
	public String toString() {
		return " " + date + ",";
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
		return Collections.emptyList();
	}

	@Override
	public List<HalfYear> getHalfYearListAll() {
		return null;
	}

	@Override
	public List<Quarter> getQuarterListAll() {
		return null;
	}

}
