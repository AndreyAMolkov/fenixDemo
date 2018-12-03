package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

abstract public class Base extends Data implements PeriodDemo{
    private String name;
    private LocalDate dateOfEmployment;
    private List<Employee> employeeList;
    private List<Employee> currentEmployeeList;
    private List<Employee> outOfWorkList;
    private List<Store> storeListCurrent;
    private List<Store> storeListAll;
    private StatisticForHr statistic;

    public Base(String name) {
        this.name = name;
        this.employeeList=new ArrayList<>(10);
        this.outOfWorkList=new ArrayList<>(10);
        this.currentEmployeeList=new ArrayList<>(10);
        this.storeListCurrent =new ArrayList<>( 10);
        this.storeListAll =new ArrayList<>(2);

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
    protected LocalDate findBeginDate(List<Employee> employeeList){
        return employeeList
                .stream()
                .min((e1, e2) -> checkDateNull(e1).compareTo(checkDateNull(e2)))
                .get().getDataOfEmployment();
    }

    public void setStatistic() {
        setStoreListCurrent();
        this.statistic = new StatisticForHr(employeeList,outOfWorkList,currentEmployeeList,storeListCurrent);
    }
    public void setCurrentEmployeeList(Employee currentEmployee) {
        this.currentEmployeeList.add(currentEmployee);
//        this.currentEmployee=currentEmployeeList.size();
    }
    public void setOutOfWorkList(Employee outOfWork) {
        if(!this.outOfWorkList.contains(outOfWork))
            this.outOfWorkList.add(outOfWork);
//        this.outOfWorkEmployee = outOfWorkList.size();
    }

    public String getName() {
        return name;
    }

    public List <Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List <Employee> employeeList) {
        if(!this.employeeList.contains(employeeList))
            this.employeeList = employeeList;
    }
    public void setEmployeeList( Employee employee ) {
        if(!this.employeeList.contains(employee))
            this.employeeList.add(employee);
    }

     void setStoreListCurrent() {

        employeeList.stream().forEach(e->setStoreListCurrent(e.getStore()));
    }
    public void setStoreListAll(Store store) {
        if(!this.storeListAll.contains(store))
            this.storeListAll.add(store);
    }
    void setStoreListAll() {

        employeeList.stream().forEach(e->setStoreListAll(e.getStore()));
    }
    public void setStoreListCurrent(Store store) {
        if(!this.storeListCurrent.contains(store))
            this.storeListCurrent.add(store);
    }
    public StatisticForHr getStatistic() {
        return statistic;
    }

    public List<Employee> getCurrentEmployeeList() {
        return currentEmployeeList;
    }


    public List<Employee> getOutOfWorkList() {
        return outOfWorkList;
    }

    public List<Store> getStoreListCurrent() {
        return storeListCurrent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfEmployment() {
        return dateOfEmployment;
    }

    public void setDateOfEmployment(LocalDate dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }


}
