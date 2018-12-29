package demo.model;

public abstract class Data {
    //markers
    protected String markerId;
    protected String markerPhone;
    protected String markerFIO;
    protected String markerStatus;
    protected String markerCouch;
    protected String markerStore;
    protected String markerDateEmployment;
    protected String markerDataDismissal;
    //field
    protected String id;
    protected String phone;
    protected String fio;
    protected String status;
    protected String couch;
    protected String dateEmployment;
    protected String dataDismissal;
    protected String store;
    //
    protected int positionStore;
    protected int positionId;
    protected int positionPhone;
    protected int positionFio;
    protected int positionStatus;
    protected int positionCouch;
    protected int positionDateEmployment;
    protected int positionDataDismissale;

   public Data(){
        this.markerId="Номeр";
        this.markerPhone="Телефон";
        this.markerFIO="Наименование";
        this.markerStatus="Статус";
        this.markerCouch="Основной тренер";
        this.markerDateEmployment="Дата приема";
        this.markerDataDismissal="Дата увольнения";
        this.markerStore = "Магазин";

        this.store = "";
        this.id="";
        this.phone="";
        this.fio="";
        this.status="";
        this.couch="";
        this.dateEmployment="";
        this.dataDismissal = "";

        this.positionStore=0;
        this.positionPhone=0;
        this.positionFio=0;
        this.positionStatus=0;
        this.positionCouch=0;
        this.positionDataDismissale=0;
        this.positionDateEmployment=0;
    }
}
