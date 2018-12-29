package demo.model;

import java.util.ArrayList;
import java.util.Collections;
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

import demo.dao.DaoEntity;
@Cacheable
@Entity(name = "Store")
@Table(name = "stores")
public class Store implements DaoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name = "store_id")
	private List<City> cityListAll;
	@OneToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name = "store_id")
	private List<Employee> employeeListAll;
	@OneToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name = "store_id")
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
	
	private String nameCity;
	private String name;
	@Transient
	private List<Employee> employeeOutOfWorkListAll;
	@Transient
	private List<Employee> employeeCurrentListAll;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Store(String name) {
		setName(name);
		initList();
	}

	public Store() {
		initList();
	}

	private void initList() {
		this.yearListAll = new ArrayList<>();
		this.halfYearListAll = new ArrayList<>();
		this.quarterListAll = new ArrayList<>();
		this.monthListAll = new ArrayList<>();
		this.dayListAll = new ArrayList<>();
		this.cityListAll = new ArrayList<>();
		this.employeeListAll = new ArrayList<>();
		this.coachListAll = new ArrayList<>();
		this.employeeOutOfWorkListAll = new ArrayList<>();
		this.employeeCurrentListAll = new ArrayList<>();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		this.nameCity = getNameCity();
		result = prime * result + ((nameCity == null) ? 0 : nameCity.hashCode());
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
		Store other = (Store) obj;
		if (coachListAll == null) {
			if (other.coachListAll != null)
				return false;
		} else if (!coachListAll.equals(other.coachListAll))
			return false;
		if (employeeListAll == null) {
			if (other.employeeListAll != null)
				return false;
		} else if (!employeeListAll.equals(other.employeeListAll))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public void setStoreListAll(Store store) {
		// empty
	}

	@Override
	public List<Store> getStoreListAll() {
		return Collections.emptyList();
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
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public String getNameCity() {
		String result = null;
		if (!getCityListAll().isEmpty()) {
			result = getCityListAll().get(0).getName();
		}
		return result;
	}

	public void setNameCity(String nameCity) {
		this.nameCity = nameCity;
	}

	public String getCityName() {
		String result = "";
		if (!getCityListAll().isEmpty()) {
			result = getCityListAll().get(0).getName();
		}
		return result;
	}

	public String getStoreName() {
		return getName();
	}

	public String getCoachName() {
		String result = "";
		if (!getCoachListAll().isEmpty()) {
			result = getCoachListAll().get(0).getName();
		}
		return result;
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
