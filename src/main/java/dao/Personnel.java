package dao;

import model.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Personnel extends Data implements PeriodDemo{
    private String name;
    protected ArrayList<String>arrayDataOfFile;
    protected String sheetName;
    protected ArrayList<ArrayList<String>>array;
    protected Map map;
    private List<Coach> couchList;
    private List<Employee>employeeList;
    private List<Employee>currentEmployeeList;
    private List<Employee>outOfWorkList;
    private List<Store>storeList;
    private List<Store>storeListCurrent;
    private List<City> cityList;
    private List<City> cityListCurrent;
    private StatisticForHr statistic;


    public Personnel(String pathFile) throws IOException {
        this.name = "Organization";
        this.cityList = Collections.synchronizedList(new ArrayList<>(10));
        this.storeList = Collections.synchronizedList(new ArrayList<>(10));
        this.arrayDataOfFile=new ArrayList <String>(0);
        this.sheetName="Лист1";
        this.map=new HashMap<String,Integer>();
        this.array=new ArrayList<ArrayList<String>>();
        this.couchList=Collections.synchronizedList(new ArrayList <>(0));
        this.employeeList=new ArrayList <>(0);
        this.currentEmployeeList=new ArrayList <>(0);
        this.outOfWorkList=new ArrayList <>(0);
        this.storeListCurrent = new ArrayList <>(0);
        this.cityListCurrent = new ArrayList <>(0);
        this.array=createFirstDataBase(pathFile);
        this.map=createMapArrayData();
        createStaff();
        this.statistic = new StatisticForHr(employeeList,outOfWorkList,outOfWorkList, storeListCurrent);
        couchList.forEach(c->c.setStatistic());
        cityList.forEach(c->c.setStatistic());
        cityListCurrent.forEach(c->c.setStatistic());
        storeListCurrent.forEach(c->c.setStatistic());
        storeList.forEach(c->c.setStatistic());


    }

    public StatisticForHr getStatistic() {
        return statistic;
    }

    @Override
    public List<Employee> getCurrentEmployeeList() {
        return currentEmployeeList;
    }

    @Override
    public List<Employee> getOutOfWorkList() {
        return outOfWorkList;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public LocalDate getDateOfEmployment() {
        return null;
    }

    @Override
    public void setDateOfEmployment(LocalDate dateOfEmployment) {

    }

    @Override
    public void setStatistic() {
        new StatisticForHr(employeeList,outOfWorkList,outOfWorkList, storeListCurrent);
    }

    @Override
    public void setCurrentEmployeeList(Employee currentEmployee) {

    }

    @Override
    public void setOutOfWorkList(Employee outOfWork) {

    }

    @Override
    public String getName() {
        return name;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    @Override
    public void setEmployeeList(List<Employee> employeeList) {

    }

    @Override
    public void setEmployeeList(Employee employee) {

    }

    public ArrayList <ArrayList<String>> getArray(){
        return array;
    }

    public Map getMap() {
        return map;
    }

    private ArrayList <ArrayList <String>>  createFirstDataBase (String pathFile) throws IOException {
        String[][] buf = new String[0][0];
        ArrayList <String> arrayWidth = new ArrayList <String>(0);
        ArrayList <ArrayList <String>> arrayLength = new ArrayList <ArrayList <String>>(0);
        String line = "";
        //read excel file
        InputStream inputStream = File.class.getResourceAsStream(pathFile);

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheet(sheetName);/////////////////////////////////// can be NullPointerException

        int maxLength = sheet.getLastRowNum();
        for (int length = 0; length < maxLength; length++) {
            arrayWidth = new ArrayList <String>(0);
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
     //   this.array=arrayLength;
        workbook.close();
        inputStream.close();
inputStream.close();
        return  arrayLength;
    }
    private Map createMapArrayData (){
        Map keyMarker=new HashMap<String,Integer>();
//        //ArrayList<ArrayList<String>> buf =(ArrayList<ArrayList<String>>)array.clone();
//        for (int i=0;i<array.size();i++) {
//           String line  = array.get(i).get(0);
//            int pos    =   line.indexOf(markerId);
//            Boolean pos1 = line.contains(markerId.subSequence(0,markerId.length()));
//            if( (positionId=array.get(i).indexOf(markerId))<=0){
//                array.remove(i);
//                i=-1;// need for compensation iteration(i++)
//                continue;
//            }
//            if( (positionFio=array.get(i).indexOf(markerFIO))<0 || (positionStatus=array.get(i).indexOf(markerStatus))<0
//                    ||(positionCouch=array.get(i).indexOf(markerCouch))<0||(positionDataDismissale=array.get(i).indexOf(markerDataDismissal))<0
//                    ||(positionPhone=array.get(i).indexOf(positionPhone))<0||(positionDateEmployment=array.get(i).indexOf(positionDateEmployment))<0){
//            }  else {
//                keyMarker.put(markerId, positionId);
//                keyMarker.put(markerFIO, positionFio);
//                keyMarker.put(markerCouch, positionCouch);
//                keyMarker.put(markerDataDismissal, positionDataDismissale);
//                keyMarker.put(markerDateEmployment, positionDateEmployment);
//                keyMarker.put(markerPhone,positionPhone);
//                keyMarker.put(markerStatus,positionStatus);
//
//                return keyMarker;
//
//            }
//        }
//        return null;
                keyMarker.put(markerId, 0);
                keyMarker.put(markerFIO, 1);
                keyMarker.put(markerStore,3 );
                keyMarker.put(markerCouch, 6);
                keyMarker.put(markerDataDismissal, 10);
                keyMarker.put(markerDateEmployment, 9);
                keyMarker.put(markerPhone,4);
                keyMarker.put(markerStatus,5);
//        this.positionStore=0;
//        this.positionPhone=4;
//        this.positionFio=1;
//        this.positionStatus=5;
//        this.positionCouch=6;
//        this.positionDataDismissale=9;
//        this.positionDateEmployment=10;
        return keyMarker;
    }
    private void createStaff() {

        int sizeStaff = array.size();
        Employee employee;
        // i=1 compensation first line , because the first line is title
        ArrayList<ArrayList<String>> clone = (ArrayList<ArrayList<String>>) array.clone();

        clone.removeIf(e->e.contains("Наименование"));//because it's  line is title

       employeeList = clone
                .stream()
                .map(e -> createPositionOfEmployee(e))
                .collect(Collectors.toList());



    }

    private String doubleCouch(String name){
        String result =name;
        List<String> deucheva =new ArrayList<String>(Arrays.asList("Деушева Юлия","Ульяна Снежная", "Ширяева"));
        List<String> yurkova =new ArrayList<String>(Arrays.asList("Юркова Мария","Васина"));

        if(deucheva.contains(name) )
            result =deucheva.get(0);

        if(yurkova.contains(name) )
            result =yurkova.get(0);

        return result;
    }

    private Coach checkCouch(String aliasCouch){

        String bufName =doubleCouch(aliasCouch);
        try {
            for (Coach coach : couchList) {
                if (coach.getName().equals(bufName))
                    return coach;
            }
        }catch (NullPointerException e){
            ;//pass first case
        }
        Coach coach=new Coach(bufName);
        try {
            couchList.add(coach);
        }catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Error Date  for  " + aliasCouch + " bufName   " + bufName + "   " + e);
        }
        return coach;
    }
    private Store checkStore(String name) {

        try {
            for (Store store : storeList) {
                if (store.getName().equals(name))
                    return store;
            }
        } catch (NullPointerException e) {
            ;//pass first case
        }
        Store store = new Store(name);

            storeList.add(store);

        return store;
    }
    private City checkSity(String name) {

        try {
            for (City city : cityList) {
                if (city.getName().equals(name))
                    return city;
            }
        } catch (NullPointerException e) {
            ;//pass first case
        }
        City city = new City(name);
        cityList.add(city);
        return city;
    }
    private String findNameStore(String line){
        String result="empty";
        if(line.indexOf("error")>=0 || line.isEmpty() || line == "" || line.length() < 2)
           return result;
        if(!line.contains("(") && !(line).contains(")"))
            return line;
        if(line.indexOf("(")>=0) {
            int position = line.indexOf("(");
            result = line.substring(0, position);
            result = result.replaceAll("\\s+$", "");
        }
        return result;
    }
    private String findNameSity(String line){
        String result="error";
        try{
        if(line.indexOf("error")>=0 || line.isEmpty() || line == "" || line.length() < 2)
            return result;
        if(!line.contains("(") && !(line).contains(")"))
            return result;

        int positionStart = line.indexOf("(") + 1;
        int positionEnd= line.indexOf(")");

        result = line.substring(positionStart,positionEnd);
        }catch (NullPointerException e){
            System.out.print("for " + line + " use default sity = " + result);
            e.printStackTrace();

        }
        return result;
    }
    private int findId(String line){
        int result=-999;
        String buf;
        if(!line.isEmpty()) {
            if (line.indexOf(".") >= 0){
                return Integer.parseInt(line.substring(0,line.indexOf(".")));
            }
              return   Integer.parseInt(line);
        }

        return result;
    }
    private LocalDate findDate(String line){
        LocalDate localDate = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            localDate = LocalDate.parse(line, formatter);
        }catch (DateTimeParseException e) {
            System.out.println("Error find date for line = " + line + "made default value = " +localDate + "    " + e);
        }
        return localDate;
    }

    private Employee createPositionOfEmployee(ArrayList<String>arrayLine){

        String fio=arrayLine.get((Integer) map.get(markerFIO));
        int id=findId(arrayLine.get((Integer) map.get(markerId)));

        Coach coach=checkCouch(arrayLine.get((Integer) map.get(markerCouch)));
        String status=arrayLine.get((Integer) map.get(markerStatus));
        String storeAndSity = "error";
        try{ storeAndSity = arrayLine.get((Integer) map.get(markerStore)); }catch (NullPointerException e){ System.out.println("Error store for line " + arrayLine + " error " + e );}

        City city = checkSity(findNameSity(storeAndSity));

        String nameStore = findNameSity(storeAndSity) + " / " +findNameStore(storeAndSity);

        Store store = checkStore(nameStore);

        LocalDate dateEmploymen = findDate(arrayLine.get((Integer)map.get(markerDateEmployment)));

        LocalDate dateDismissal = findDate(arrayLine.get((Integer)map.get(markerDataDismissal)));

        String phone = arrayLine.get((Integer)map.get(markerPhone));

       Employee employee = new Employee(fio,phone,status,coach,dateEmploymen,dateDismissal, city,store,id);
        employee.getCity().setEmployeeList(employee);
        employee.getStore().setEmployeeList(employee);
        employee.getCoach().setEmployeeList(employee);

        City cityEmp =employee.getCity();
        cityEmp.setStoreListAll(employee.getStore());
        Store storeEmp = employee.getStore();
        storeEmp.setCity(cityEmp);

        sortEmployee(employee);

        return employee;
    }

    private void sortEmployee(Employee e){
            LocalDate firstDate= e.getDataOfEmployment();
            LocalDate secondDate = e.getDataDismissal();
            Period period;
            try {
                period = Period.between(secondDate, firstDate);
  //              System.out.println(period);
            }catch (NullPointerException e1) {
                currentEmployeeList.add(e);

                e.getCity().setCurrentEmployeeList(e);
                e.getStore().setCurrentEmployeeList(e);
                setCityListCurrent(e.getCity());
                setStoreListCurrent(e.getStore());
                e.getCoach().setCurrentEmployeeList(e);
                e.getCoach().setStoreListCurrent(e.getStore());


                return;
            }

            e.setDayOfwork(period);
             outOfWorkList.add(e);
             e.getCity().setOutOfWorkList(e);
             e.getStore().setOutOfWorkList(e);
             e.getCoach().setOutOfWorkList(e);

        }
    public List <Coach> getCouchList() {
        return couchList;
    }

    public List <Employee> getAllEmployee() {
        return employeeList;
    }

    public List <Store> getStoreList() {
        return storeList;
    }

    public List <City> getCityList() {
        return cityList;
    }

    public List<Store> getStoreListCurrent() {
        return storeListCurrent;
    }

    public void setStoreListCurrent(Store storeCurrent) {
        if(!storeListCurrent.contains(storeCurrent))
            this.storeListCurrent.add(storeCurrent);
    }

    public List<City> getCityListCurrent() {
        return cityListCurrent;
    }

    public void setCityListCurrent(City cityCurrent) {
       if(!cityListCurrent.contains(cityCurrent))
            this.cityListCurrent.add(cityCurrent);
    }
}
