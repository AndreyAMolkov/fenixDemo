package demo.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import demo.constant.Constants;
import demo.model.City;
import demo.model.Coach;
import demo.model.Data;
import demo.model.Employee;
import demo.model.Store;

public class PrepareStaff extends Data {
	private static Logger log = LoggerFactory.getLogger("demo.dao.PrepareStaff");
	private String pathFile;
	protected ArrayList<String> arrayDataOfFile;
	protected String sheetName;
	protected List<ArrayList<String>> array;
	protected Map map;
	private List<Coach> couchList;
	private List<Employee> employeeList;
	private List<Employee> currentEmployeeList;
	private List<Employee> outOfWorkList;
	private List<Store> storeList;
	private List<Store> storeListCurrent;
	private List<City> cityList;
	private List<City> cityListCurrent;
	private List<Employee> listEmployeeError;
	private Dao dao;

	@Autowired
	public PrepareStaff(Dao dao) {
		super();
		this.cityList = Collections.synchronizedList(new ArrayList<>(10));
		this.storeList = Collections.synchronizedList(new ArrayList<>(10));
		this.arrayDataOfFile = new ArrayList<String>(0);
		this.sheetName = "Лист1";
		this.map = new HashMap<String, Integer>();
		this.array = new ArrayList<ArrayList<String>>();
		this.couchList = Collections.synchronizedList(new ArrayList<>(0));
		this.employeeList = new ArrayList<>(0);
		this.currentEmployeeList = new ArrayList<>(0);
		this.outOfWorkList = new ArrayList<>(0);
		this.storeListCurrent = new ArrayList<>(0);
		this.cityListCurrent = new ArrayList<>(0);
		this.listEmployeeError = new ArrayList<>(0);
		this.dao = dao;
	}

	public PrepareStaff() {

	}

	private ArrayList<ArrayList<String>> createFirstDataBase() throws IOException {
		String nameMethod = "createFirstDataBase";
		ArrayList<String> arrayWidth = new ArrayList<>(0);
		ArrayList<ArrayList<String>> arrayLength = new ArrayList<ArrayList<String>>(0);
		String line = "";
		// read excel file

		try (XSSFWorkbook workbook = new XSSFWorkbook(pathFile)) {

			XSSFSheet sheet = workbook.getSheet(sheetName);/////////////////////////////////// can be
															/////////////////////////////////// NullPointerException

			int maxLength = sheet.getLastRowNum();
			for (int length = 0; length < maxLength; length++) {
				arrayWidth = new ArrayList<>(0);
				try {
					int maxWidth = sheet.getRow(length).getLastCellNum();
					for (int width = 0; width < maxWidth; width++) {
						try {
							line = sheet.getRow(length).getCell(width).toString();
						} catch (NullPointerException exe) {
							line = "";// need for right width
						}
						arrayWidth.add(line);
					}

					arrayLength.add(arrayWidth);
				} catch (NullPointerException exe) {
					// got to next iteration
				}

			}
		} catch (FileNotFoundException e) {
			log.debug(nameMethod + Constants.ONE_PARAMETERS, Constants.ERROR,
					"Can't open the specified file input stream from file:" + pathFile);
		}
		log.debug(nameMethod + Constants.TWO_PARAMETERS, "pathFile", pathFile, "arrayLength.size()=",
				arrayLength.size());

		return arrayLength;
	}

	private Map<String, Integer> createMapArrayData() {
		String nameMethod = "createMapArrayData";
		Map<String, Integer> keyMarker = new HashMap<>();

		keyMarker.put(markerId, 0);
		keyMarker.put(markerFIO, 1);
		keyMarker.put(markerStore, 3);
		keyMarker.put(markerCouch, 6);
		keyMarker.put(markerDataDismissal, 10);
		keyMarker.put(markerDateEmployment, 9);
		keyMarker.put(markerPhone, 4);
		keyMarker.put(markerStatus, 5);
		log.debug(nameMethod + Constants.ONE_PARAMETERS, "map for position row is create =", true);

		this.map = keyMarker;
		return keyMarker;
	}

	public List<Employee> createStaff(String path) throws IOException {
		String nameMethod = "createStaff";
		setPathFile(path);
		createMapArrayData();
		this.array = createFirstDataBase();
		array.removeIf(e -> e.contains("Наименование"));// because it's line is title

		employeeList = array.stream().map(this::createPositionOfEmployee).collect(Collectors.toList());
		log.debug(nameMethod + Constants.ONE_PARAMETERS, "all employees in a file", employeeList.size());
		return employeeList;
	}

	@SuppressWarnings("unchecked")
	public List<Employee> populateDB() {
		String nameMethod = "populateDB";
		List<Employee> listForRecords = Collections.emptyList();
		try {
			listForRecords = notInDB();
		} catch (Exception e) {
			log.debug(nameMethod + Constants.ONE_PARAMETERS, nameMethod, false);
			return listForRecords;
		}

		if (dao.addAll(listForRecords)) {
			log.debug(nameMethod + Constants.ONE_PARAMETERS, nameMethod, true);
		} else {
			log.debug(nameMethod + Constants.ONE_PARAMETERS, nameMethod, false);
		}

		return listForRecords;
	}

	public List<Employee> notInDB() throws Exception {
		String nameMethod = "notInDB";
		List<Employee> listDB = dao.getAll(Employee.class);
		if (listDB.isEmpty()) {
			return getEmployeeList();
		}

		List<Employee> result = getEmployeeList().stream().filter(e -> !listDB.contains(e))
				.collect(Collectors.toList());

		log.debug(nameMethod + Constants.TWO_PARAMETERS, "sizeListAll", employeeList.size(), "size unique",
				result.size());
		return result;
	}

	private String doubleCouch(String name) {
		String nameMethod = "doubleCouch";
		String result = name;
		List<String> deucheva = new ArrayList<>(Arrays.asList("Деушева Юлия", "Ульяна Снежная", "Ширяева"));
		List<String> yurkova = new ArrayList<>(Arrays.asList("Юркова Мария", "Васина"));

		if (deucheva.contains(name))
			result = deucheva.get(0);

		if (yurkova.contains(name))
			result = yurkova.get(0);
		log.debug(nameMethod + Constants.TWO_PARAMETERS, "first name", name, Constants.RESULT, result);
		return result;
	}

	private Coach checkCouch(String aliasCouch) {
		String nameMethod = "checkCouch";
		String bufName = doubleCouch(aliasCouch);
		try {
			for (Coach coach : couchList) {
				if (coach.getName().equals(bufName))
					return coach;
			}
		} catch (NullPointerException e) {
			;// pass first case
		}
		Coach coach = new Coach(bufName);
		try {
			couchList.add(coach);
		} catch (ArrayIndexOutOfBoundsException e) {
			log.debug(nameMethod + Constants.THREE_PARAMETERS, "Error Date  for", aliasCouch, "bufName ", bufName,
					Constants.MESSAGE, e);
		}
		return coach;
	}

	private Store checkStore(String name) {
		String nameMethod = "checkStore";
		try {
			for (Store store : storeList) {
				if (store.getName().equals(name))
					return store;
			}
		} catch (NullPointerException e) {
			log.debug(nameMethod + Constants.TWO_PARAMETERS, Constants.PASS_FIRST_CASE, true, "message", e);
		}
		Store store = new Store(name);

		storeList.add(store);

		return store;
	}

	private City checkCity(String name) {
		String nameMethod = "checkCity";
		try {
			for (City city : cityList) {
				if (city.getName().equals(name))
					return city;
			}
		} catch (NullPointerException e) {
			log.debug(nameMethod + Constants.TWO_PARAMETERS, Constants.PASS_FIRST_CASE, true, "message", e);
		}
		City city = new City(name);
		cityList.add(city);
		return city;
	}

	private String findNameStore(String line) {
		String nameMethod = "findNameStore";
		String result = "empty";
		if (line.indexOf(Constants.ERROR) >= 0 || line.isEmpty() || line == "" || line.length() < 2) {
			log.debug(nameMethod + Constants.ONE_PARAMETERS, Constants.RESULT, result);
			return result;
		}
		if (!line.contains("(") && !(line).contains(")")) {
			log.debug(nameMethod + Constants.ONE_PARAMETERS, Constants.RESULT, line);
			return line;
		}
		if (line.indexOf('(') >= 0) {
			int position = line.indexOf('(');
			result = line.substring(0, position);
			result = result.replaceAll("\\s+$", "");
		}
		log.debug(nameMethod + Constants.ONE_PARAMETERS, Constants.RESULT, result);
		return result;
	}

	private String findNameCity(String line) {
		String nameMethod = "findNameCity";
		String result = Constants.ERROR;
		try {
			if (line.indexOf(Constants.ERROR) >= 0 || line.isEmpty() || line == "" || line.length() < 2) {
				log.debug(nameMethod + Constants.ONE_PARAMETERS, Constants.RESULT, result);
				return result;
			}
			if (!line.contains("(") && !(line).contains(")")) {
				log.debug(nameMethod + Constants.ONE_PARAMETERS, Constants.RESULT, result);
				return result;
			}
			int positionStart = line.indexOf('(') + 1;
			int positionEnd = line.indexOf(')');

			result = line.substring(positionStart, positionEnd);
		} catch (NullPointerException e) {
			log.debug(nameMethod + Constants.THREE_PARAMETERS, "for", line, "use default city = ", result,
					Constants.MESSAGE, e);
		}
		log.debug(nameMethod + Constants.ONE_PARAMETERS, Constants.RESULT, result);
		return result;
	}

	private String findId(String line) {
		String nameMethod = "findId";
		String result = null;
		if (!line.isEmpty()) {
			if (line.indexOf('.') >= 0) {
				result = line.substring(0, line.indexOf('.'));
			} else {
				result = line;
			}
		}
		log.debug(nameMethod + Constants.ONE_PARAMETERS, Constants.RESULT, result);
		return result;
	}

	private LocalDate findDate(String line) {
		String nameMethod = "findDate";
		LocalDate localDate = null;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		try {
			localDate = LocalDate.parse(line, formatter);
		} catch (DateTimeParseException e) {
			log.debug(nameMethod + Constants.THREE_PARAMETERS, "Error find date for line = ", line,
					"made default value = ", localDate, Constants.MESSAGE, e);
		}
		log.debug(nameMethod + Constants.ONE_PARAMETERS, "date=", localDate);
		return localDate;
	}

	private Employee createPositionOfEmployee(ArrayList<String> arrayLine) {
		String nameMethod = "createPositionOfEmployee";
		String fio = arrayLine.get((Integer) map.get(markerFIO));
		String id = findId(arrayLine.get((Integer) map.get(markerId)));

		Coach coach = checkCouch(arrayLine.get((Integer) map.get(markerCouch)));
		String status = arrayLine.get((Integer) map.get(markerStatus));
		String storeAndCity = "error";
		try {
			storeAndCity = arrayLine.get((Integer) map.get(markerStore));
		} catch (NullPointerException e) {
			log.debug(nameMethod + Constants.TWO_PARAMETERS, "Error store for line ", arrayLine, Constants.MESSAGE, e);
		}

		City city = checkCity(findNameCity(storeAndCity));

		String nameStore = findNameCity(storeAndCity) + " / " + findNameStore(storeAndCity);

		Store store = checkStore(nameStore);

		LocalDate dateEmploymen = findDate(arrayLine.get((Integer) map.get(markerDateEmployment)));

		LocalDate dateDismissal = findDate(arrayLine.get((Integer) map.get(markerDataDismissal)));

		String phone = arrayLine.get((Integer) map.get(markerPhone));

		Employee employee = new Employee(fio, phone, status, coach, dateEmploymen, dateDismissal, city, store, id);

		city.setEmployeeListAll(employee);
		city.setStoreListAll(store);
		city.setCoachListAll(coach);

		store.setEmployeeListAll(employee);
		store.setCityListAll(city);
		store.setCoachListAll(coach);

		coach.setEmployeeListAll(employee);
		coach.setStoreListAll(store);
		coach.setCityListAll(city);

		log.debug(nameMethod + Constants.FOUR_PARAMETERS, "emploee", employee.getName(), "coach", coach.getName(),
				"city", city.getName(), "store", store.getName());
		return employee;
	}

	public List<Coach> getCouchList() {
		return couchList;
	}

	public List<Store> getStoreList() {
		return storeList;
	}

	public List<City> getCityList() {
		return cityList;
	}

	public List<Store> getStoreListCurrent() {
		return storeListCurrent;
	}

	public void setStoreListCurrent(Store storeCurrent) {
		if (!storeListCurrent.contains(storeCurrent))
			this.storeListCurrent.add(storeCurrent);
	}

	public List<City> getCityListCurrent() {
		return cityListCurrent;
	}

	public void setCityListCurrent(City cityCurrent) {
		if (!cityListCurrent.contains(cityCurrent))
			this.cityListCurrent.add(cityCurrent);
	}

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}

	public List<Employee> getListEmployeeError() {
		return listEmployeeError;
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public List<ArrayList<String>> getArray() {
		return array;
	}

	public Map<String, Integer> getMap() {
		return map;
	}
}
