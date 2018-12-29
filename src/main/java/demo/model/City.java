package demo.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import demo.dao.DaoEntity;
@Cacheable
@Entity(name = "City")
@Table(name = "cities")
public class City implements DaoEntity {
	private static Logger log = LoggerFactory.getLogger("demo.model.City");
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name = "city_id")
	private List<Store> storeListAll;
	@OneToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name = "city_id")
	private List<Employee> employeeListAll;
	@OneToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name = "city_id")
	private List<Coach> coachListAll;
	@Transient
	private List<Quarter> quarterListAll;
	@Transient
	private List<HalfYear> halfYearListAll;
	@Transient
	private List<Year> yearListAll;
//	@ManyToMany()
	@Transient
	private List<Day> dayListAll;
//	@ManyToMany()
	@Transient
	private List<Month> monthListAll;
	private String name;
	@Transient
	private List<Employee> employeeOutOfWorkListAll;
	@Transient
	private List<Employee> employeeCurrentListAll;

	public City(String name) {
		setName(name);
		initList();
	}

	public City() {
		initList();
	}

	private void initList() {
		this.yearListAll = new ArrayList<>();
		this.halfYearListAll = new ArrayList<>();
		this.quarterListAll = new ArrayList<>(5);
		this.monthListAll = new ArrayList<>();
		this.dayListAll = new ArrayList<>();
		this.storeListAll = new ArrayList<>();
		this.employeeListAll = new ArrayList<>();
		this.coachListAll = new ArrayList<>();
		this.employeeOutOfWorkListAll = new ArrayList<>();
		this.employeeCurrentListAll = new ArrayList<>();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		City other = (City) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
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
		return Arrays.asList(this);
	}

	@Override
	public void setCityListAll(City cityEmp) {
		// empty
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
		this.name = name;

	}

	@Override
	public String getName() {
		return name;
	}

	public String getCityName() {
		return getName();
	}

	public String getStoreName() {
		String result = "";
		if (!getStoreListAll().isEmpty()) {
			result = getStoreListAll().get(0).getName();
		}
		return result;
	}

	public String getCoachName() {
		String result = "";
		if (!getCoachListAll().isEmpty()) {
			result = getCoachListAll().get(0).getName();
		}
		return result;
	}

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

	@Override
	public List<Month> getMonthListAll() {
		return monthListAll;
	}

	@Override
	public List<HalfYear> getHalfYearListAll() {
		return halfYearListAll;
	}

	@Override
	public List<Quarter> getQuarterListAll() {
		return quarterListAll;
	}

}
