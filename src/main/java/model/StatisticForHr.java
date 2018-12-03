package model;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class StatisticForHr {
    private List<Year> yearList;
    private List<HalfYear>halfYearList;
    private List<Quarter>quarterList;
    private List<Month>monthList;
    private int countYear;
    private List<AverageCountOfEmployee>  averageCountOfEmployeeFromOutside;
    private List<AverageCountOfEmployee>  averageCountOfEmployeeCurrent;
    private List<Employee> employeeList;
    private List<Employee> outOfWorkList;
    private  List<Employee>employeeListCurrent;
    private List<Store> storeListAll;
    private List<Store> storeListCurrent;
    private LocalDate beginDate;
    private LocalDate lastDate;
    private int averageCountOfEmployee;
    //Ктек = Кув × 100 (%) : S, где: Ктек – коэффициент текучести персонала; Кув – количество уволенных сотрудников за рассматриваемый период; S – среднесписочная численность персонала за рассматриваемый период.
    private int Ktek;

    public StatisticForHr(List<AverageCountOfEmployee> averageCountOfEmployeeFrom, List<Employee> employeeList,
                          List<Employee> outOfWorkList, List<Employee>employeeListCurrent, List<Store> storeListAll,
                          LocalDate beginDate, LocalDate lastDate) {
        this.averageCountOfEmployeeFromOutside = averageCountOfEmployeeFrom;
        this.storeListAll = storeListAll;
        this.storeListCurrent =storeListCurrent;
        this.employeeList = employeeList;
        this.outOfWorkList = outOfWorkList;
        this.employeeListCurrent =employeeListCurrent;
        this.beginDate = beginDate;
        this.lastDate = lastDate;
        createKtek();
        this.yearList = new ArrayList<>(2);
        setYear();
        setYearList();
    }
    public StatisticForHr( List<Employee> employeeList, List<Employee> outOfWorkList, List<Employee>employeeListCurrent,
                           List<Store> storeListCurrent ) {
        this.employeeList = employeeList;
        this.outOfWorkList = outOfWorkList;
        this.employeeListCurrent = employeeListCurrent;
        this.storeListCurrent = storeListCurrent;
        createAllPeriod();
        createAverageCountOfEmployee();
        createKtek();
        this.yearList = new ArrayList<>(2);
        setYear();
        setYearList();
    }

    public List<Year> getYearList() {
        return yearList;
    }

    public int getCountHalfYear() {
        return countYear;
    }

    public List<Employee> getEmployeeListCurrent() {
        return employeeListCurrent;
    }

    public void setYear() {
        this.countYear =2;//default value
    }

    public void setYearList() {
        for (LocalDate date = beginDate; date.isBefore(lastDate); date = date.plusMonths(12)){
            LocalDate endDate = date.plusMonths(12).minusDays(1);
            yearList.add(new Year(averageCountOfEmployeeFromOutside,employeeList,date,endDate));
        }

    }

    public StatisticForHr( ) {
        createAllPeriod();
        createAverageCountOfEmployee();
        createKtek();
    }
    private LocalDate checkDateNull(Employee employee){
        try {
            if (employee.getDataOfEmployment().equals(null))
                return LocalDate.now();
        }catch (NullPointerException e){
            System.out.println( "for employee - " + employee + "  not fount data of employment  " + e);
        }
        return employee.getDataOfEmployment();

    }
    private void createKtek(){
    //    Ктек = Кув × 100 (%) : S, где: Ктек – коэффициент текучести персонала;
        // Кув – количество уволенных сотрудников за рассматриваемый период;
        // S – среднесписочная численность персонала за рассматриваемый период.
        int KYB =outOfWorkList.size();
        int S=0;
        try {
             S = (averageCountOfEmployeeFromOutside
                    .stream()
                    .mapToInt(e -> e.getCountEmployee())
                    .reduce((s1, s2) -> (s1 + s2))
                    .orElse(0)) / averageCountOfEmployeeFromOutside.size();
            this.Ktek = (KYB * 100) / S;
        }catch (ArithmeticException e) {
            System.out.println("for " +this + "   " + e );
            this.Ktek = 0;
        }
        this.averageCountOfEmployee =S;
    }

    public int getAverageCountOfEmployee() {
        return averageCountOfEmployee;
    }

    public List<AverageCountOfEmployee> createAllPeriod(){
        Employee employeeMinDateOfEmployee
                = employeeList
                .stream()
                .min((e1, e2) -> checkDateNull(e1).compareTo(checkDateNull(e2)))
                .get();

        this.beginDate = employeeMinDateOfEmployee.getDataOfEmployment();
        this.lastDate = LocalDate.now();
        return createPeriodForDate();
    }
    private List<AverageCountOfEmployee> createPeriodForDate() {
    List<AverageCountOfEmployee> allTime = new ArrayList<> (10);;


        for (LocalDate date = beginDate; date.isBefore(lastDate); date = date.plusDays(1))
        {
            AverageCountOfEmployee oneDay = new AverageCountOfEmployee(date);
            allTime.add(oneDay);
        }
        this.averageCountOfEmployeeFromOutside = allTime;
        return allTime;
    }
    private void createAverageCountOfEmployee() {
        employeeList
                .stream()
                .forEach(e-> createOneDayAverageCountOfEmployee(e));
    }

    private void createOneDayAverageCountOfEmployee(Employee e) {
        LocalDate begin;
        LocalDate last;
        begin =e.getDataOfEmployment();
        last =e.getDataDismissal();
        try{
            if(begin.equals(null)) {
                System.out.println("for employee  - " + e + "  not found date of empployment , pass it");
                return;
            }
        }catch (NullPointerException e1)  {
            System.out.println("for employee  - " + e + "  not found date of empployment , pass it");
            return;
        }

        try{
            if (last.equals(null))
                last = LocalDate.now();
        }catch (NullPointerException e1)  {
            last = LocalDate.now();
        }
        for (LocalDate date = begin; date.isBefore(last); date = date.plusDays(1)) {
            findLocalDate(date).setListEmployee(e);
        }

    }
    private AverageCountOfEmployee findLocalDate(LocalDate day){

        return averageCountOfEmployeeFromOutside
                .stream()
                .filter(x -> (x.getDate()).equals(day))
                .findFirst()
                .get();



    }
    public List<AverageCountOfEmployee>getAverageCountOfEmployeeFromCompany() {
        return averageCountOfEmployeeFromOutside;
    }

    public void setAverageCountOfEmployeeFromCompany(List<AverageCountOfEmployee> averageCountOfEmployeeFrom) {
        this.averageCountOfEmployeeFromOutside = averageCountOfEmployeeFrom;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<Employee> getOutOfWorkList() {
        return outOfWorkList;
    }

    public void setOutOfWorkList(List<Employee> outOfWorkList) {
        this.outOfWorkList = outOfWorkList;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }

    public int getKtek() {
        return Ktek;
    }

    public void setKtek(int Ktek) {
        this.Ktek = Ktek;
    }

    public void createHalfYearList(){
        this.halfYearList = new ArrayList<>(2);

    }
}
