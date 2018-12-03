package model;


import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Quarter extends DecreaseAndCreate {
    private List<Month> monthList;
    private int countMonth;

    public List<Month> getMonthList() {
        return monthList;
    }

    public Quarter( List<AverageCountOfEmployee> averageCountOfEmployeeFromOutside, List<Employee> employeeList,
                    LocalDate beginDate, LocalDate lastDate) {
        super(averageCountOfEmployeeFromOutside, employeeList,beginDate, lastDate);
         this.monthList = new ArrayList<Month>(3);
        setMonthList();
    }
//
//    public String getPeriodString() {
//        return periodString;
//    }

    public int getCountMonth() {
        return countMonth;
    }

    public void setCountMonth() {
        Period period = Period.between(getLastDate(),getBeginDate());
        this.countMonth = period.getMonths();
    }

    public void setMonthList() {
        for (LocalDate date = getBeginDate(); date.isBefore(getLastDate()); date = date.plusMonths(1)){
            LocalDate endDate = date.plusMonths(1).minusDays(1);
            if(endDate.isAfter(LocalDate.now()))
                endDate = LocalDate.now();
            monthList.add(new Month(getAverageCountOfEmployeeCurrent(),getEmployeeList(), date,endDate));
                if(endDate.isEqual(LocalDate.now()))
                    return;
        }

    }

        public void setMonthList(List<Month> monthList) {
        this.monthList = monthList;
    }
    @Override
    public void setPeriodString() {
        super.setPeriodString( super.getBeginDate().getMonth().toString().toLowerCase() + "." +
                super.getBeginDate().getYear() + " - " + super.getLastDate().getMonth().toString().toLowerCase() + "."
                + super.getLastDate().getYear() );
    }
}
