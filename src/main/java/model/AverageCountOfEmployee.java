package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AverageCountOfEmployee {
    private LocalDate date;
    private List<Employee> listEmployee;
    private int countEmployee;


    public AverageCountOfEmployee(LocalDate date) {
        this.date = date;
        this.listEmployee = new ArrayList<>(10);
    }

    public int getCountEmployee() {
        return countEmployee;
    }

    public void setListEmployee(Employee employee) {
        if(!listEmployee.contains(employee)) {
            this.listEmployee.add(employee);
            this.countEmployee = listEmployee.size();
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Employee> getListEmployee() {
        return listEmployee;
    }

    public void setListEmployee(List<Employee> listEmployee) {
        if(!this.listEmployee.contains(listEmployee)) {
            this.listEmployee = listEmployee;
            this.countEmployee += listEmployee.size();
        }
    }

    public void setCountEmployee(int countEmployee) {
        this.countEmployee = countEmployee;
    }
    public AverageCountOfEmployee(LocalDate date, int countEmployee) {
        this.date = date;
        this.listEmployee = new ArrayList<>(10);
        this.countEmployee = countEmployee;
    }
}
