package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

abstract public class DecreaseAndCreate {
    private List<AverageCountOfEmployee> averageCountOfEmployeeFromOutside;
    private List<AverageCountOfEmployee>  averageCountOfEmployeeCurrent;
    private List<Employee> employeeList;
    private List<Employee> outOfWorkList;
    private List<Employee>employeeListCurrent;
    private List<Store> storeListCurrent;
    private List<Store> storeListAll;
    private String storeListAllSize;
    private String storeListCurrentSize;
    private LocalDate beginDate;
    private LocalDate lastDate;
    private String employeeListSize;
    private String outOfWorkListSize;
    private String employeeListCurrentSize;
    private String averageCountOfEmployee;
    private String periodString;
    //Ктек = Кув × 100 (%) : S, где: Ктек – коэффициент текучести персонала;
    // Кув – количество уволенных сотрудников за рассматриваемый период;
    // S – среднесписочная численность персонала за рассматриваемый период.
    private String Ktek;

    public DecreaseAndCreate(List<AverageCountOfEmployee> averageCountOfEmployeeFromOutside, List<Employee> employeeListOut,
                             LocalDate beginDate, LocalDate lastDate) {
        this.averageCountOfEmployeeFromOutside = averageCountOfEmployeeFromOutside;
        this.beginDate = beginDate;
        this.lastDate = lastDate;
        setPeriodString();
        this.employeeListCurrentSize = "none";
        this.employeeListSize = "none";
        this.outOfWorkListSize = "none";
        this.storeListCurrentSize = "none";
        this.storeListAllSize ="none";
        this.Ktek = "none";
        this.employeeList = deleteNotValidate(employeeListOut);
        setEmployeeListSize(employeeList);
        this.employeeListCurrent = sortCurrentEmployee(getEmployeeList());
        this.outOfWorkList = sortOutOfWorkEmployee(getEmployeeList());
        this.storeListCurrent = new ArrayList<>(2);
        this.storeListAll = new ArrayList<>(2);
         createStoreListAll(getEmployeeList());
        createStoreListCurrent(employeeListCurrent);
        this.averageCountOfEmployeeCurrent =new ArrayList<>(30);
        this.averageCountOfEmployeeCurrent = createValidateAverageCount();
        createKtek();
    }

    private void setEmployeeListSize(List<Employee> employeeList) {
        if(employeeList!=null)
            this.employeeListSize = setSize(employeeList.size());
    }

    protected abstract void setPeriodString();
    public String getPeriodString(){
        return periodString;
    }

    private List<Employee> sortCurrentEmployee(List<Employee> list){

        List<Employee>result =list.stream()
                .filter(e->e.getDataDismissal()==null || e.getDataDismissal().isAfter(lastDate))
                .collect(Collectors.toList());

        this.employeeListCurrentSize = setSize(result.size());
        return result;
    }
    private List<Employee> sortOutOfWorkEmployee(List<Employee> list){
       String nameMethod = "sortOutOfWorkEmployee";
        List<Employee>result = null;
        try {
            result = list.stream()
                    .filter(e -> !employeeListCurrent.contains(e))
                    .collect(Collectors.toList());
            this.outOfWorkListSize = setSize(result.size());
        }catch (NullPointerException e){
            System.out.println("error " + nameMethod + " ");
        }
        return result;
    }

    private Boolean validateDate(Employee employee){

        LocalDate employmentDate = employee.getDataOfEmployment();
        LocalDate firedDate = employee.getDataDismissal();
        //employment before lastDate or equal
       if(employmentDate.isBefore(lastDate.plusDays(1)) ) {
           //fired after beginDate or equal

              if (firedDate == null || firedDate.isAfter(beginDate)) {
                  return true;
              }

       }

        return false;
    }
    private List<Employee> deleteNotValidate( List<Employee> listOfPerson){
        List<Employee> result = null;
        result = listOfPerson
                .stream()
                .filter(e->validateDate(e))
                .collect(Collectors.toList());

    return result;
}
    private String setSize(int i){
        return String.valueOf(i);
    }
    private Boolean validateDate(Store store){
        List <Employee> list =deleteNotValidate(store.getEmployeeList());
        if(list.isEmpty())
            return false;
        else
            return  true;
}
    private void createStoreListAll(List<Employee> list){

        if(list!=null){
            list.stream().forEach(e->setStoreListAll(e.getStore()));
            if(storeListAll!=null && !storeListAll.isEmpty())
                this.storeListAllSize = setSize(storeListAll.size());
        }

}

    private void createStoreListCurrent(List<Employee> list){

        if(list!=null){
            list.stream().forEach(e->setStoreListCurrent(e.getStore()));
            if(storeListCurrent!=null)
                this.storeListCurrentSize = setSize(storeListCurrent.size());
        }

    }
    private void createCurrentAverage(AverageCountOfEmployee day){
        List<Employee> listOfPerson = deleteNotValidate(day.getListEmployee());
        if(!listOfPerson.isEmpty()){
            AverageCountOfEmployee oneDay = new AverageCountOfEmployee(day.getDate());
            oneDay.setListEmployee(listOfPerson);
            this.averageCountOfEmployeeCurrent.add(oneDay);
        }
    }

    public List<Store> getStoreListCurrent() {
        return storeListCurrent;
    }

    public String getStoreListCurrentSize() {
        return storeListCurrentSize;
    }

    private Boolean comparatorValidateDay(LocalDate day){
        if(day.isAfter(beginDate.minusDays(1)) && day.isBefore(lastDate.plusDays(1)) )
            return true;
        else
            return false;
    }
    private List<AverageCountOfEmployee>  createValidateAverageCount(){
        averageCountOfEmployeeFromOutside
                .forEach(day-> createCurrentAverage(day));

        return averageCountOfEmployeeCurrent
                .stream()
                .filter(one->comparatorValidateDay(one.getDate()))
                .collect(Collectors.toList());

    }

    protected void createKtek(){
        //    Ктек = Кув × 100 (%) : S, где: Ктек – коэффициент текучести персонала;
        // Кув – количество уволенных сотрудников за рассматриваемый период;
        // S – среднесписочная численность персонала за рассматриваемый период.
        int KYB =0;
        if(outOfWorkList!=null)
            KYB = outOfWorkList.size();

        int S=0;
        try {
            S = (averageCountOfEmployeeCurrent
                    .stream()
                    .mapToInt(e -> e.getCountEmployee())
                    .reduce((s1, s2) -> (s1 + s2))
                    .orElse(0)) / averageCountOfEmployeeCurrent.size();
            this.Ktek = String.valueOf((KYB * 100) / S);
            this.averageCountOfEmployee =String.valueOf(S);
        }catch (ArithmeticException e) {
            System.out.println("for " + getPeriodString() + "   " + e );
        }

    }

   public void setPeriodString(String periodString){
        this.periodString= periodString;
   }

    public List<AverageCountOfEmployee> getAverageCountOfEmployeeCurrent() {
        return averageCountOfEmployeeCurrent;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }


    public List<Employee> getOutOfWorkList() {
        return outOfWorkList;
    }

    public String getEmployeeListSize() {
        return employeeListSize;
    }

    public String getOutOfWorkListSize() {
        return outOfWorkListSize;
    }

    public String getEmployeeListCurrentSize() {
        return employeeListCurrentSize;
    }

    public String getAverageCountOfEmployee() {
        return averageCountOfEmployee;
    }

    public List<Employee> getEmployeeListCurrent() {
        return employeeListCurrent;
    }

    public void setEmployeeListCurrent(List<Employee> employeeListCurrent) {
        this.employeeListCurrent = employeeListCurrent;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public String getKtek() {
        return Ktek;
    }

    public void setStoreListCurrent(Store  store) {
       if(!storeListCurrent.contains(store))
            this.storeListCurrent.add(store);
    }

    public List<Store> getStoreListAll() {
        return storeListAll;
    }

    public void setStoreListAll(Store store) {
        if(!storeListAll.contains(store))
            this.storeListAll.add(store);
    }

    public String getStoreListAllSize() {
        return storeListAllSize;
    }
}
