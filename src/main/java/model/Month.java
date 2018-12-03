package model;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Month extends DecreaseAndCreate{

    public Month(List<AverageCountOfEmployee> averageCountOfEmployeeFromOutside, List<Employee> employeeList,
                 LocalDate beginDate, LocalDate lastDate) {
        super(averageCountOfEmployeeFromOutside, employeeList, beginDate, lastDate);
        if(Period.between(beginDate,lastDate).getMonths()==0);
          }

    @Override
    protected void setPeriodString() {
       super.setPeriodString(super.getBeginDate().getMonth().toString() + "." +super.getBeginDate().getYear());
    }


}
