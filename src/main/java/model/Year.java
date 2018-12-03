package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Year extends DecreaseAndCreate {
    private List<HalfYear> halfYearList;
    private int countHalfYear;
   // private String periodString;

    public Year( List<AverageCountOfEmployee> averageCountOfEmployeeFromOutside, List<Employee> employeeList,
                 LocalDate beginDate, LocalDate lastDate) {
        super(averageCountOfEmployeeFromOutside, employeeList, beginDate, lastDate);
        this.halfYearList = new ArrayList<>(2);

        setHalfYearList();
    }

    @Override
    public void setPeriodString() {
       super.setPeriodString( super.getBeginDate().getYear() + " year");
    }


    public List<HalfYear> getHalfYearList() {
        return halfYearList;
    }

    public int getCountHalfYear() {
        return countHalfYear;
    }

    public void setHalfYear() {
        this.countHalfYear =2;//default value
    }


    public void setHalfYearList() {
        for (LocalDate date = getBeginDate(); date.isBefore(getLastDate()); date = date.plusMonths(6)){
            LocalDate endDate = date.plusMonths(6).minusDays(1);
            halfYearList.add(new HalfYear(getAverageCountOfEmployeeCurrent(),getEmployeeList(), date,endDate));
        }

    }

}
