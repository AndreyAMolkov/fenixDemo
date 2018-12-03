package model;

import java.time.LocalDate;
import java.util.List;

public interface PeriodDemo {

    public void setStatistic() ;
    public void setCurrentEmployeeList(Employee currentEmployee);
    public void setOutOfWorkList(Employee outOfWork);
    public String getName();
    public List <Employee> getEmployeeList() ;
    public void setEmployeeList(List <Employee> employeeList) ;
    public void setEmployeeList( Employee employee );
    public StatisticForHr getStatistic();
    public List<Employee> getCurrentEmployeeList() ;
    public List<Employee> getOutOfWorkList() ;
//    public int getOutOfWorkEmployee() ;
    public void setName(String name);
    public LocalDate getDateOfEmployment() ;
    public void setDateOfEmployment(LocalDate dateOfEmployment);
//    public int getAverageAgeCurrentEmployee() ;
//    public void setAverageAgeCurrentEmployee(int averageAgeCurrentEmployee) ;
//    public int getAverageAgeLossEmployee();
//    public void setAverageAgeLossEmployee(int averageAgeLossEmployee) ;
//    public int getPercentLastEmployee();
//    public void setPercentLastEmployee(int percentLastEmployee);
//    public int getCurrentEmployee() ;
//    public int getLossEmployeePerLastYear();
//    public void setLossEmployeePerLastYear(int lossEmployeePerLastYear);
}
