package demo.dao;

import java.util.List;

import demo.model.Day;
import demo.model.Employee;
import demo.model.HalfYear;
import demo.model.Month;
import demo.model.Quarter;
import demo.model.Year;

public interface Dao<T> {

	public T update(T entityModel) throws Exception;

	public void remove(Class<T> entityModelClass, Long id) throws Exception;

	public void remove(T entityModel) throws Exception;

	public void add(T entityModel);

	public List<T> getAll(Class<T> entityModelClass) throws Exception;

	public T getEntityById(Class<T> entityModelClass, Long id) throws Exception;

	public List<T> findEntityByAttributeOfString(Class<T> entityModelClass, String columnName, String columnValue)
			throws Exception;

	public boolean addAll(List<T> entityModelList);

	public List<Day> populateDay(List<Employee> listEmployees, List<Day> listDays);

	public List<Month> populateMonth(List<Day> listDays, List<Month> listMonth);

	public List<Quarter> populateQuarter(List<Month> listMonth, List<Quarter> listQuarter);

	public List<HalfYear> populateHalfYear(List<Quarter> listQuarter, List<HalfYear> listHalfYear);

	public List<Year> populateYear(List<HalfYear> listHalfYear, List<Year> listYear);
}
