package model;

public class Store extends Base implements PeriodDemo {
    private City city;
    private Coach coach;

    public Store(String name) {
        super(name);
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

}
