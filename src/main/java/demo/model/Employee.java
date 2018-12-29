package demo.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import demo.constant.Constants;
@Cacheable
@Entity(name = "Employee")
@Table(name = "employees")
public class Employee {
	private static Logger log = LoggerFactory.getLogger("demo.model.Employee");
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "coach_id")
	private Coach coach;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "city_id")
	private City city;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "store_id")
	private Store store;
//	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Transient
	private List<Day> dayListAll;

	private String idFromFile;
	private String name;
	private String phone;
	private String status;
	private String workSchedule;
	private String employment;
	private LocalDate dataOfEmployment;
	private LocalDate dataDismissal;
	private int age;
	private int operatingTime;
	private Period dayOfwork;

	public Employee(String name) {
		this.name = name;
		this.dayListAll = new ArrayList<>();
	}

	public Employee() {
		this.dayListAll = new ArrayList<>();
	}

	public Employee(String name, String phone, String status, Coach coach, LocalDate dataOfEmployment,
			LocalDate dataDismissal, City city, Store store, String idfromFile) {
		this.dayOfwork = null;
		this.idFromFile = idfromFile;
		this.name = name;
		this.phone = phone;
		this.status = status;
		this.coach = coach;
		this.dataOfEmployment = dataOfEmployment;
		this.dataDismissal = dataDismissal;
		this.city = city;
		this.store = store;
		this.dayListAll = new ArrayList<>();
		setDayOfwork(dataOfEmployment, dataDismissal);
	}
	private void initList() {

		this.coach = coach;
		this.city = city;
		this.store = store;
		this.dayListAll = new ArrayList<>();
		setDayOfwork(dataOfEmployment, dataDismissal);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataOfEmployment == null) ? 0 : dataOfEmployment.hashCode());
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
		Employee other = (Employee) obj;
		if (dataOfEmployment == null) {
			if (other.dataOfEmployment != null)
				return false;
		} else if (!dataOfEmployment.equals(other.dataOfEmployment))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public Period getDayOfwork() {
		return dayOfwork;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Coach getCoach() {
		return coach;
	}

	public void setCoach(Coach coach) {
		this.coach = coach;
	}

	public String getWorkSchedule() {
		return workSchedule;
	}

	public void setWorkSchedule(String workSchedule) {
		this.workSchedule = workSchedule;
	}

	public String getEmployment() {
		return employment;
	}

	public void setEmployment(String employment) {
		this.employment = employment;
	}

	public LocalDate getDataOfEmployment() {
		return dataOfEmployment;
	}

	public void setDataOfEmployment(LocalDate dataOfEmployment) {
		this.dataOfEmployment = dataOfEmployment;
	}

	public LocalDate getDataDismissal() {
		return dataDismissal;
	}

	public void setDataDismissal(LocalDate dataDismissal) {
		this.dataDismissal = dataDismissal;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public int getOperatingTime() {
		return operatingTime;
	}

	public void setOperatingTime(int operatingTime) {
		this.operatingTime = operatingTime;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getIdFromFile() {
		return idFromFile;
	}

	public void setIdFromFile(String idFromFile) {
		this.idFromFile = idFromFile;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDayOfwork(LocalDate start, LocalDate end) {
		String nameMethod = "setDayOfwork";
		if (start == null) {
			log.warn(nameMethod + Constants.THREE_PARAMETERS, "start parametr", start, " output result",
					LocalDate.now());
			start = LocalDate.now();
		}
		if (end == null) {
			log.warn(nameMethod + Constants.THREE_PARAMETERS, "end parametr", end, " output result", LocalDate.now());
			end = LocalDate.now();
		}

		this.dayOfwork = Period.between(start, end);

	}

	public String getCityName() {
		String result = "";
		if (getCity() != null) {
			result = getCity().getName();
		}
		return result;
	}

	public String getStoreName() {
		String result = "";
		if (getStore() != null) {
			result = getStore().getName();
		}
		return result;
	}

	public String getCoachName() {
		String result = "";
		if (getCoach() != null) {
			result = getCoach().getName();
		}
		return result;
	}

	public List<Day> getDayListAll() {
		return dayListAll;
	}

	public void setDayListAll(Day day) {
		if (!getDayListAll().contains(day)) {
			getDayListAll().add(day);
		}
	}

	public String printDay() {
		StringBuilder bld = new StringBuilder();
		for (Day day : getDayListAll()) {
			bld.append(day);
		}
		return bld.toString();
	}

	public String printDayID() {
		String nameMethod = "printDayID";
		StringBuilder bld = new StringBuilder();
		for (Day day : getDayListAll()) {
			bld.append(day.getEmployeeListAll().size() + ", ");

		}
//		log.debug(nameMethod + Constants.ONE_PARAMETERS, "line",bld.toString());

		return bld.toString();
	}

}
