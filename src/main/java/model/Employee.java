package model;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class Employee {
    private int id;
    private String name;
    private String phone;
    private String status;
    private Coach coach;
    private String workSchedule;
    private String employment;
    private LocalDate dataOfEmployment;
    private LocalDate dataDismissal;
    private LocalDate Birthday;
    private int age;
    private City city;
    private int operatingTime;
    private Store store;
    private Period dayOfwork;

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String name, String phone, String status, Coach coach, LocalDate dataOfEmployment, LocalDate dataDismissal, City city, Store store, int id) {
        this.dayOfwork = null;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.status = status;
        this.coach = coach;
        this.dataOfEmployment = dataOfEmployment;
        this.dataDismissal = dataDismissal;
        this.city = city;
        this.store = store;
        this.dayOfwork = null;
    }

    public void setDayOfwork(Period dayOfwork) {
        this.dayOfwork = dayOfwork;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public String getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(String workSchedule) {
        this.workSchedule = workSchedule;
    }

    public String getEmployment() {
        return employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public LocalDate getDataOfEmployment() {
        if(dataOfEmployment==null)
            return null;

        return dataOfEmployment;
    }


    public void setDataOfEmployment(LocalDate dataOfEmployment) {
        this.dataOfEmployment = dataOfEmployment;
    }

    public LocalDate getDataDismissal() {
        if(dataDismissal==null)
            return null;

        return dataDismissal;
    }

    public void setDataDismissal(LocalDate dataDismissal) {
        this.dataDismissal = dataDismissal;
    }

    public LocalDate getBirthday() {
        return Birthday;
    }

    public void setBirthday(LocalDate birthday) {
        Birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getOperatingTime() {
        return operatingTime;
    }

    public void setOperatingTime(int operatingTime) {
        this.operatingTime = operatingTime;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
