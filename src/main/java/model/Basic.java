package model;

interface  Basic {

    public Year getYear(int i, DecreaseAndCreate entity);
    public HalfYear getHalfYear(int i, DecreaseAndCreate entity);
    public Quarter getQuarter(int i, DecreaseAndCreate entity);
    public  Month getMonth(int i, DecreaseAndCreate entity);
}
