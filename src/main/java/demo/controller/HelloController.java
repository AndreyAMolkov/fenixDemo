package demo.controller;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import demo.constant.Constants;
import demo.dao.Dao;
import demo.dao.DaoEntity;
import demo.dao.PrepareStaff;
import demo.model.City;
import demo.model.Coach;
import demo.model.Day;
import demo.model.Employee;
import demo.model.HalfYear;
import demo.model.InfoMessage;
import demo.model.Month;
import demo.model.Quarter;
import demo.model.Store;
import demo.model.Year;

@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Controller
public class HelloController {
	private static Logger log = LoggerFactory.getLogger("demo.controller.HelloController");

	@Autowired
	private ModelAndView model;
	@Autowired
	private InfoMessage infoMessage;
	@Autowired
	private List<Employee> listEmployees;
	@Autowired
	private List<Store> listStores;
	@Autowired
	private List<City> listCities;
	@Autowired
	private List<Coach> listCoaches;
	@Autowired
	private List<Day> listDays;
	@Autowired
	private List<Month> listMonths;
	@Autowired
	private List<Quarter> listQuarters;
	@Autowired
	private List<HalfYear> listHalfYears;
	@Autowired
	private List<Year> listYears;

	@Autowired
	private PrepareStaff prepareStaff;
	@Autowired
	private Dao dao;
//	@Autowired
//	private Dao dayD;//=new DaoDay();

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView start() {
		model.setViewName(Constants.REDIRECT + "entity");
		return model;
	}

	@RequestMapping(value = "/entity", method = RequestMethod.POST)
	public ModelAndView loadEntity(@RequestParam(value = "nameEntity") String nameEntity) {
		String nameMethod = "loadEntity";
		boolean result = true;
		loadAll(nameEntity);
		populateYears();
		model.setViewName(nameEntity);
		for (Year year : listYears) {

			for (Coach coach : year.getCoachListAll()) {
				System.out.println(coach.getName());
				for (HalfYear hy : coach.getHalfYearListAll()) {

					System.out.println(hy.getName() + "-" + hy.createKtek());
				}
			}
		}
		if (infoMessage.getCause() != null) {
			result = false;
		}
		log.debug(nameMethod + Constants.ONE_PARAMETERS, Constants.MESSAGE, result);
		return model;
	}

	@RequestMapping(value = "/entity", method = RequestMethod.GET)
	public ModelAndView loadEmployee() {
		String nameMethod = "loadEmployee";
		boolean result = true;
		loadAll(Constants.EMPLOYEE);
		model.setViewName(Constants.EMPLOYEE);
		if (infoMessage.getCause() != null) {
			result = false;
		}
		log.debug(nameMethod + Constants.ONE_PARAMETERS, Constants.MESSAGE, result);
		return model;
	}

	@RequestMapping(value = "/prepare/populateDB", method = RequestMethod.POST)
	public ModelAndView populateDB() {
		String nameMethod = "populateDB";
		List<Employee> listEmpl = populateStaff();
		List<Day> listDay = populateDay(listEmpl);
		// List<Month> listMonth = populateMonth(listDay);
		// List<Quarter> listQuarter = populateQuarter(listMonth);
		//// records in db
		int size = prepareStaff.populateDB().size();

		handlerEvents("all employees = " + prepareStaff.getEmployeeList().size() + ", " + "download= " + size);
		loadAll(Constants.EMPLOYEE);
		model.setViewName(Constants.EMPLOYEE);
		log.debug(nameMethod + Constants.ONE_PARAMETERS, Constants.END, true);
		return model;
	}

	public List<Employee> populateStaff() {
		String nameMethod = "populateStaff";
		String path = "D:\\Project\\Fenix\\src\\main\\resources\\staff100.xlsx";
		log.debug(nameMethod + Constants.ONE_PARAMETERS, Constants.BEGIN, true);
		try {
			prepareStaff.createStaff(path);
		} catch (Exception e1) {
			handlerEvents("Error create staff for " + path);
			log.warn(nameMethod + Constants.ONE_PARAMETERS, Constants.ERROR, e1);
		}
		return prepareStaff.getEmployeeList();
	}

	public List<Day> populateDay(List<Employee> list) {
		String nameMethod = "populateDay";
		log.debug(nameMethod + Constants.ONE_PARAMETERS, "populate day begin", true);
		loadAll(Constants.DAY);
		List<Day> listResult = dao.populateDay(list, listDays);
		log.debug(nameMethod + Constants.ONE_PARAMETERS, "populate day end", true);
		return listResult;
	}

	public List<Month> populateMonth(List<Day> list) {
		String nameMethod = "populateMonth";
		log.debug(nameMethod + Constants.ONE_PARAMETERS, "populate month begin", true);
		loadAll(Constants.MONTH);
		List<Month> listResult = dao.populateMonth(list, listMonths);
		log.debug(nameMethod + Constants.ONE_PARAMETERS, "populate month end", true);
		return listResult;
	}

	public List<Quarter> populateQuarter(List<Month> list) {
		String nameMethod = "populateQuarter";
		log.debug(nameMethod + Constants.ONE_PARAMETERS, "populate quarter begin", true);
		loadAll(Constants.QUARTER);
		List<Quarter> listResult = dao.populateQuarter(list, listQuarters);
		log.debug(nameMethod + Constants.ONE_PARAMETERS, "populate quarter end", true);
		return listResult;
	}

	public List<HalfYear> populateHalfYear(List<Quarter> list) {
		String nameMethod = "populateHalfYear";
		log.debug(nameMethod + Constants.ONE_PARAMETERS, "populate halfYear begin", true);
		loadAll(Constants.HALFYEAR);
		List<HalfYear> listResult = dao.populateHalfYear(list, listHalfYears);
		log.debug(nameMethod + Constants.ONE_PARAMETERS, "populate halfYear end", true);
		return listResult;
	}

	public List<Year> populateYear(List<HalfYear> list) {
		String nameMethod = "populateYear";
		log.debug(nameMethod + Constants.ONE_PARAMETERS, "populate year begin", true);
		loadAll(Constants.YEAR);
		List<Year> listResult = dao.populateYear(list, listYears);
		log.debug(nameMethod + Constants.ONE_PARAMETERS, "populate year end", true);
		return listResult;
	}

	@RequestMapping(value = "/prepare/populateQuarter", method = RequestMethod.POST)
	public ModelAndView populateQuarters() {
		String nameMethod = "populateQuarters";
		loadAll(Constants.DAY);
		List<Month> listMonth = populateMonth(listDays);
		List<Quarter> listQuarter = populateQuarter(listMonth);
		model.addObject("listQuarters", listQuarter);
		model.setViewName(Constants.QUARTER);
		log.debug(nameMethod + Constants.ONE_PARAMETERS, Constants.END, true);
		return model;
	}

	@RequestMapping(value = "/prepare/populateYear", method = RequestMethod.POST)
	public ModelAndView populateYears() {
		String nameMethod = "populateYears";
		loadAll(Constants.DAY);
		List<Month> listMonth = populateMonth(listDays);
		List<Quarter> listQuarter = populateQuarter(listMonth);
		List<HalfYear> listHalfYear = populateHalfYear(listQuarter);
		listYears = populateYear(listHalfYear);
		System.out.println(listYears);
		model.addObject("listYears", listYears);
		model.setViewName(Constants.YEAR);
		log.debug(nameMethod + Constants.ONE_PARAMETERS, Constants.END, true);
		return model;
	}

	@RequestMapping(value = "/prepare/populateHalfYear", method = RequestMethod.POST)
	public ModelAndView populateHalfYears() {
		String nameMethod = "populateHalfYears";
		loadAll(Constants.DAY);
		List<Month> listMonth = populateMonth(listDays);
		List<Quarter> listQuarter = populateQuarter(listMonth);
		List<HalfYear> listHalfYear = populateHalfYear(listQuarter);
		model.addObject("listHalfYears", listHalfYear);
		model.setViewName(Constants.HALFYEAR);
		log.debug(nameMethod + Constants.ONE_PARAMETERS, Constants.END, true);
		return model;
	}

	@RequestMapping(value = "/prepare/populateMonth", method = RequestMethod.POST)
	public ModelAndView populateMOnths() {
		String nameMethod = "populateMOnths";
		loadAll(Constants.EMPLOYEE);
		List<Day> listresult = dao.populateDay(listEmployees, listDays);
		// loadAll(Constants.DAY);

		List<Month> list = populateMonth(listresult);
		model.addObject("listMonths", list);
		model.setViewName(Constants.MONTH);
		log.debug(nameMethod + Constants.ONE_PARAMETERS, Constants.END, true);
		return model;
	}

	@RequestMapping(value = "/remove/entity", method = RequestMethod.POST)
	public ModelAndView deleteEntity(@RequestParam(value = "id") Long id,
			@RequestParam(value = "nameClazz") String nameClazz) {
		String nameMethod = "deleteEntity";
		log.debug(nameMethod + Constants.ONE_PARAMETERS, "begine", true);
		removeOneEntityByName(nameClazz, id);

		return model;
	}

	@RequestMapping(value = "/remove/dataOfOneEntity", method = RequestMethod.POST)
	public ModelAndView removeDataOfOneEntity(@RequestParam(value = "nameClazz") String nameClazz) {
		String nameMethod = "removeDataOfOneEntity";
		log.debug(nameMethod + Constants.ONE_PARAMETERS, "begine", true);
		removeOneEntity(nameClazz);
		return model;
	}

	private void removeOneEntity(String nameClass) {
		String nameMethod = "removeOneEntity";

		if (!Constants.LIST_CLASSES.contains(nameClass)) {
			handlerEvents("Error remove all " + nameClass);
			log.error(nameMethod + Constants.ONE_PARAMETERS, "not found", nameClass);
			return;
		}
		try {
			switch (nameClass) {
			case Constants.EMPLOYEE:
				removeAllEmployeeInDB();
				model.addObject("listEmployees", listEmployees);
				loadAll(Constants.EMPLOYEE);
				model.setViewName(Constants.EMPLOYEE);
				break;
			case Constants.STORE:
				removeAllStoreInDB();
				model.addObject("listStores", listStores);
				loadAll(Constants.STORE);
				model.setViewName(Constants.STORE);
				break;
			case Constants.CITY:
				removeAllCityInDB();
				model.addObject("listCities", listCities);
				loadAll(Constants.CITY);
				model.setViewName(Constants.CITY);
				break;
			case Constants.COACH:
				removeAllCoachInDB();
				model.addObject("listCoaches", listCoaches);
				loadAll(Constants.COACH);
				model.setViewName(Constants.COACH);
				break;
			case Constants.DAY:
				removeAllDayInDB();
				model.addObject("listDays", listDays);
				loadAll(Constants.DAY);
				model.setViewName(Constants.DAY);
				break;
			case Constants.MONTH:
				removeAllDayInDB();
				model.addObject("listMonths", listMonths);
				loadAll(Constants.MONTH);
				model.setViewName(Constants.MONTH);
				break;
			case Constants.QUARTER:
				removeAllDayInDB();
				model.addObject("listQuarters", listQuarters);
				loadAll(Constants.QUARTER);
				model.setViewName(Constants.QUARTER);
				break;
			case Constants.HALFYEAR:
				removeAllDayInDB();
				model.addObject("listHalfYears", listHalfYears);
				loadAll(Constants.HALFYEAR);
				model.setViewName(Constants.HALFYEAR);
				break;
			case Constants.YEAR:
				removeAllDayInDB();
				model.addObject("listYears", listYears);
				loadAll(Constants.YEAR);
				model.setViewName(Constants.YEAR);
				break;
			default:
				handlerEvents("Error - remove for" + nameClass);
			}

		} catch (Exception e) {
			handlerEvents("Error download " + nameClass);
		}

	}

	@RequestMapping(value = "/remove/dataAllInDb", method = RequestMethod.POST)
	public ModelAndView deleteAllData() {
		String nameMethod = "deleteAllData";
		removeAllDataInDB();
		log.debug(nameMethod);
		model.setViewName(Constants.EMPLOYEE);
		return model;
	}

	private void handlerEvents(String dataForPass) {
		String nameMethod = "handlerEvents";
		log.warn(nameMethod + Constants.ONE_PARAMETERS, "dataForPass", dataForPass);
		infoMessage.setCause(dataForPass);
		model.addObject("message", infoMessage);
	}

	private void loadAll(String nameClass) {
		String nameMethod = "loadAll";

		if (!Constants.LIST_CLASSES.contains(nameClass)) {
			handlerEvents("Error download employees");
			log.error(nameMethod + Constants.ONE_PARAMETERS, "not found", nameClass);
			return;
		}
		DaoEntity daoEntity;
		try {
			switch (nameClass) {
			case Constants.EMPLOYEE:
				listEmployees = dao.getAll(Employee.class);
				model.addObject("listEmployees", listEmployees);
				break;
			case Constants.STORE:
				listStores = dao.getAll(Store.class);
				model.addObject("listStores", listStores);
				break;
			case Constants.CITY:
				listCities = dao.getAll(City.class);
				model.addObject("listCities", listCities);
				break;
			case Constants.COACH:
				listCoaches = dao.getAll(Coach.class);
				model.addObject("listCoaches", listCoaches);
				break;
			case Constants.DAY:
				if (listDays.isEmpty()) {
					listDays = dao.getAll(Day.class);
				}
//				listDays = dayD.getAll(Day.class);
				listDays.forEach(day -> day.setWorkList(day.getEmployeeListAll()));
				sort(listDays);
				model.addObject("listDays", listDays);
				model.setViewName(Constants.DAY);
				break;
			case Constants.MONTH:
				listMonths = dao.getAll(Month.class);
				model.addObject("listMonths", listMonths);
				model.setViewName(Constants.MONTH);
				break;
			case Constants.QUARTER:
				listQuarters = dao.getAll(Quarter.class);
				model.addObject("listQuarters", listQuarters);
				model.setViewName(Constants.QUARTER);
				break;
			case Constants.HALFYEAR:
				listHalfYears = dao.getAll(HalfYear.class);
				model.addObject("listHalfYears", listHalfYears);
				model.setViewName(Constants.HALFYEAR);
				break;
			case Constants.YEAR:
				listYears = dao.getAll(Year.class);
				model.addObject("listYears", listYears);
				model.setViewName(Constants.YEAR);
				break;
			default:
				handlerEvents("Error - download for" + nameClass);
			}

		} catch (Exception e) {
			handlerEvents("Error download " + nameClass);
			log.debug(nameMethod + Constants.ONE_PARAMETERS, Constants.ERROR, e);
		}

	}

	private void removeOneEntityByName(String nameClass, Long id) {
		String nameMethod = "removeOneEntityByName";

		if (!Constants.LIST_CLASSES.contains(nameClass)) {
			handlerEvents("Error download entity" + nameClass + " id= " + id);
			log.error(nameMethod + Constants.ONE_PARAMETERS, "not found", nameClass);
			return;
		}

		try {
			switch (nameClass) {
			case Constants.EMPLOYEE:
				dao.remove(Employee.class, id);
				model.addObject("listEmployees", listEmployees);
				loadAll(Constants.EMPLOYEE);
				model.setViewName(Constants.EMPLOYEE);
				break;
			case Constants.STORE:
				dao.remove(Store.class, id);
				model.addObject("listStores", listStores);
				loadAll(Constants.STORE);
				model.setViewName(Constants.STORE);
				break;
			case Constants.CITY:
				dao.remove(City.class, id);
				model.addObject("listCities", listCities);
				loadAll(Constants.CITY);
				model.setViewName(Constants.CITY);
				break;
			case Constants.COACH:
				dao.remove(Coach.class, id);
				model.addObject("listCoaches", listCoaches);
				loadAll(Constants.COACH);
				model.setViewName(Constants.COACH);
				break;
			case Constants.DAY:
				dao.remove(Coach.class, id);
				model.addObject("listDays", listDays);
				loadAll(Constants.DAY);
				model.setViewName(Constants.DAY);
				break;
			case Constants.MONTH:
				dao.remove(Month.class, id);
				model.addObject("listMonths", listMonths);
				loadAll(Constants.MONTH);
				model.setViewName(Constants.MONTH);
				break;
			case Constants.QUARTER:
				dao.remove(Quarter.class, id);
				model.addObject("listQuarters", listQuarters);
				loadAll(Constants.QUARTER);
				model.setViewName(Constants.QUARTER);
				break;
			case Constants.HALFYEAR:
				dao.remove(HalfYear.class, id);
				model.addObject("listHalfYears", listHalfYears);
				loadAll(Constants.HALFYEAR);
				model.setViewName(Constants.HALFYEAR);
				break;
			case Constants.YEAR:
				dao.remove(Year.class, id);
				model.addObject("listYears", listYears);
				loadAll(Constants.YEAR);
				model.setViewName(Constants.YEAR);
				break;
			default:
				handlerEvents("Error - download for" + nameClass);
			}

		} catch (Exception e) {
			handlerEvents("Error remove " + nameClass + ",id=" + id);
			log.warn(nameMethod + Constants.ONE_PARAMETERS, Constants.ERROR, e);
		}

	}

	private void removeAllDataInDB() {
		String nameMethod = "removeAllDataInDB";
		log.debug(nameMethod + Constants.ONE_PARAMETERS, "begine remove all date", true);
		removeAllCityInDB();
		removeAllCoachInDB();
		removeAllEmployeeInDB();
		removeAllStoreInDB();

	}

	private void removeAllEmployeeInDB() {
		String nameMethod = "removeAllEmployeeInDB";
		int countError = 0;
		loadAll(Constants.EMPLOYEE);
		// remove all data
		for (Employee empl : listEmployees) {
			try {
				dao.remove(empl);
			} catch (Exception e) {
				handlerEvents("can't employee=" + empl.getName());

			}
		}
		log.debug(nameMethod + Constants.TWO_PARAMETERS, "sizeOfAllEmployee", listEmployees.size(), "countError",
				countError);
	}

	private void removeAllCityInDB() {
		String nameMethod = "removeAllCityInDB";
		int countError = 0;
		loadAll(Constants.CITY);
		// remove all data
		for (City city : listCities) {
			try {
				dao.remove(city);
			} catch (Exception e) {
				handlerEvents("can't city=" + city.getName());

			}
		}
		log.debug(nameMethod + Constants.TWO_PARAMETERS, "sizeOfAllCity", listCities.size(), "countError", countError);
	}

	private void removeAllStoreInDB() {
		String nameMethod = "removeAllStoreInDB";
		int countError = 0;
		loadAll(Constants.STORE);
		// remove all data
		for (Store store : listStores) {
			try {
				dao.remove(store);
			} catch (Exception e) {
				handlerEvents("can't store=" + store.getName());

			}
		}
		log.debug(nameMethod + Constants.TWO_PARAMETERS, "sizeOfAllStore", listStores.size(), "countError", countError);
	}

	private void removeAllCoachInDB() {
		String nameMethod = "removeAllCoachInDB";
		int countError = 0;
		loadAll(Constants.COACH);
		// remove all data
		for (Coach coach : listCoaches) {
			try {
				dao.remove(coach);
			} catch (Exception e) {
				handlerEvents("can't store=" + coach.getName());

			}
		}
		log.debug(nameMethod + Constants.TWO_PARAMETERS, "sizeOfAllCoach", listCoaches.size(), "countError",
				countError);
	}

	private void removeAllDayInDB() {
		String nameMethod = "removeAllDayInDB";
		int countError = 0;
		loadAll(Constants.DAY);
		// remove all data
		for (Day day : listDays) {
			try {
				dao.remove(day);
			} catch (Exception e) {
				handlerEvents("can't day=" + day.getName());

			}
		}
		log.debug(nameMethod + Constants.TWO_PARAMETERS, "sizeOfAllDay", listDays.size(), "countError", countError);
	}

	private void sort(List<Day> list) {
		Collections.sort(list, (e1, e2) -> e1.getName().compareTo(e2.getName()));
	}

}
