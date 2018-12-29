package demo.dao;

import java.time.LocalDate;
import java.util.List;

import demo.model.City;
import demo.model.Coach;
import demo.model.Day;
import demo.model.Employee;
import demo.model.HalfYear;
import demo.model.Month;
import demo.model.Quarter;
import demo.model.Store;

public interface DaoEntity {
	
	public List<Store> getStoreListAll();

	public default void setStoreListAll(Store store) {
		if (!getStoreListAll().contains(store)) {
			getStoreListAll().add(store);
		}
	}

	public List<City> getCityListAll();

	public default void setCityListAll(City cityEmp) {
		if (!getCityListAll().contains(cityEmp)) {
			getCityListAll().add(cityEmp);
		}
	}

	public List<Employee> getEmployeeListAll();

	public default void setEmployeeListAll(Employee employee) {
		if (!getEmployeeListAll().contains(employee)) {
			getEmployeeListAll().add(employee);
			setWorkList(employee);
		}
	}

	public List<Coach> getCoachListAll();

	public default void setCoachListAll(Coach coach) {
		if (!getCoachListAll().contains(coach)) {
			getCoachListAll().add(coach);
		}
	}

	public void setName(String name);

	public String getName();

	public List<Day> getDayListAll();

	public default void setStoreListAll(List<Store> storeList) {
		if (storeList != null) {
			storeList.forEach(this::setStoreListAll);
		}
	}

	public default void setEmployeeListAll(List<Employee> employeeList) {
		if (employeeList != null) {
			employeeList.forEach(this::setEmployeeListAll);
		}
	}

	public default void setCoachListAll(List<Coach> coachList) {
		if (coachList != null) {
			coachList.forEach(this::setCoachListAll);
		}
	}

	public default void setCityListAll(List<City> cityList) {
		if (cityList != null) {
			cityList.forEach(this::setCityListAll);
		}
	}

	public default void setDayListAll(Day day) {
		if (!getDayListAll().contains(day)) {
			getDayListAll().add(day);
		}
	}

	public default void setDayListAll(List<Day> dayList) {
		if (dayList != null) {
			dayList.forEach(this::setDayListAll);
		}
	}

	public default void setMonthListAll(Month month) {
		if (!getMonthListAll().contains(month)) {
			getMonthListAll().add(month);
		}
	}

	public List<Month> getMonthListAll();

	public default void setMonthListAll(List<Month> monthList) {
		if (monthList != null) {
			monthList.forEach(this::setMonthListAll);
		}
	}

	public default void setQuarterListAll(Quarter quarter) {
		if(getQuarterListAll()==null) {
			System.out.println(getName()+ " and =="+ getQuarterListAll());
		}
		
		
		try {
			
			
		if (getQuarterListAll()==null||!getQuarterListAll().contains(quarter)) {
			getQuarterListAll().add(quarter);
		}
		}catch (NullPointerException e) {
			System.out.println(getName()+ "----"+ e);
		//	int i =2;
		}
	}

	public List<HalfYear> getHalfYearListAll();

	public default void setHalfYearListAll(List<HalfYear> halfYearList) {
		if (halfYearList != null) {
			halfYearList.forEach(this::setHalfYearListAll);
		}
	}

	public default void setHalfYearListAll(HalfYear halfYear) {
		if (!getHalfYearListAll().contains(halfYear)) {
			getHalfYearListAll().add(halfYear);
		}
	}

	public List<Quarter> getQuarterListAll();

	public default void setQuarterListAll(List<Quarter> quarterList) {
		if (quarterList != null) {
			quarterList.forEach(this::setQuarterListAll);
		}
	}

	public List<Employee> getEmployeeOutOfWorkListAll();

	public default void setEmployeeOutOfWorkListAll(List<Employee> employeeOutOfWorkListAll) {
		if (employeeOutOfWorkListAll != null) {
			employeeOutOfWorkListAll.forEach(this::setEmployeeOutOfWorkListAll);
		}
	}

	public default void setEmployeeOutOfWorkListAll(Employee employeeOutOfWork) {
		if (!getEmployeeOutOfWorkListAll().contains(employeeOutOfWork)) {
			getEmployeeOutOfWorkListAll().add(employeeOutOfWork);

		}
	}

	public List<Employee> getEmployeeCurrentListAll();

	public default void setEmployeeCurrentListAll(Employee employeeCurrent) {
		if (!getEmployeeCurrentListAll().contains(employeeCurrent)) {
			getEmployeeCurrentListAll().add(employeeCurrent);
		}
	}

	public default void setEmployeeCurrentListAll(List<Employee> employeeCurrentListAll) {
		if (employeeCurrentListAll != null) {
			employeeCurrentListAll.forEach(this::setEmployeeCurrentListAll);
		}
	}

	public default void setWorkList(Employee empl) {

		LocalDate dateEmpl = empl.getDataDismissal();
		if (dateEmpl != null) {
			setEmployeeOutOfWorkListAll(empl);
		} else {
			setEmployeeCurrentListAll(empl);
		}
	}

	public default void setWorkList(List<Employee> list) {
		list.forEach(this::setWorkList);
	}

	public default int createKtek() {
		// Ктек = Кув × 100 (%) : S, где: Ктек – коэффициент текучести персонала;
		// Кув – количество уволенных сотрудников за рассматриваемый период;
		// S – среднесписочная численность персонала за рассматриваемый период.
		int Ktek = 0;
		int KYB = 0;
		if (getEmployeeOutOfWorkListAll() != null)
			KYB = getEmployeeOutOfWorkListAll().size();

		int S = 0;
		try {
			S = averageCountOfEmployee();
			Ktek = (KYB * 100) / S;
		} catch (ArithmeticException e) {
			System.out.println("for " + "   " + e);
		}
		return Ktek;
	}
	public default int averageCountOfEmployee() {
		return (getDayListAll().stream().mapToInt(e -> e.getEmployeeCurrentListAll().size())
				.reduce((s1, s2) -> (s1 + s2)).orElse(0)) / getDayListAll().size();
		
	}
}
