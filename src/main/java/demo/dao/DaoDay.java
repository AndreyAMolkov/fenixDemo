package demo.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import demo.model.Day;
import demo.model.Employee;
import demo.model.HalfYear;
import demo.model.Month;
import demo.model.Quarter;
import demo.model.Year;

@Cacheable
@Repository
public class DaoDay extends BaseDao<Day> implements Dao<Day>{

	public DaoDay() {
		super();
	}

	@Override
	public List<Day> populateDay(List<Employee> listEmployees, List<Day> listDays) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Month> populateMonth(List<Day> listDays, List<Month> listMonth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Quarter> populateQuarter(List<Month> listMonth, List<Quarter> listQuarter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HalfYear> populateHalfYear(List<Quarter> listQuarter, List<HalfYear> listHalfYear) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Year> populateYear(List<HalfYear> listHalfYear, List<Year> listYear) {
		// TODO Auto-generated method stub
		return null;
	}

	


}
