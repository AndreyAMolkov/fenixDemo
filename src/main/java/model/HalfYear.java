package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HalfYear  extends DecreaseAndCreate {
    private List<Quarter> quarterList;
    private int countQuarter;


    public HalfYear( List<AverageCountOfEmployee> averageCountOfEmployeeFromOutside, List<Employee> employeeList,
                     LocalDate beginDate, LocalDate lastDate) {
        super(averageCountOfEmployeeFromOutside, employeeList,beginDate, lastDate);
        this.quarterList = new ArrayList<>(2);
            setQuarterList();
    }

    @Override
    public void setPeriodString() {
          super.setPeriodString( super.getBeginDate().getMonth().toString().toLowerCase() +"." + super.getBeginDate().getYear()
                  +"-" + super.getLastDate().getMonth().toString().toLowerCase() + "." + super.getLastDate().getYear());

    }


    public List<Quarter> getQuarterList() {
        return quarterList;
    }

    public int getCountQuarter() {
        return countQuarter;
    }

    public void setCountQuarter() {
        this.countQuarter =2;//default value
    }

    public void setQuarterList() {
        for (LocalDate date = getBeginDate(); date.isBefore(getLastDate()); date = date.plusMonths(3)){
            LocalDate endDate = date.plusMonths(3).minusDays(1);
            quarterList.add(new Quarter(getAverageCountOfEmployeeCurrent(),getEmployeeList(),date,endDate));
        }

    }
}
