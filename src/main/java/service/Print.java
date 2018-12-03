package service;

import dao.*;
import model.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Print {
    private Personnel personnel;
    private String path;
    XSSFWorkbook workbook;

    public Print() throws IOException {
        this.path ="/ЛЮДИ.xlsx";
        this.personnel=new Personnel(path);
        try {
            this.workbook = openDocument(path);
        }catch (IOException e){
            System.out.println("Error open document - " + path);
           throw new  IOException();
        }

    }
    private XSSFWorkbook openDocument(String pathDocument) throws IOException {
       String[][] buf = new String[0][0];
       ArrayList<String> arrayWidth = new ArrayList <String>(0);
       ArrayList <ArrayList <String>> arrayLength = new ArrayList <ArrayList <String>>(0);
       String line = "";
       //read excel file

       XSSFWorkbook workbook = new XSSFWorkbook();
  //     XSSFSheet sheet = workbook.getSheet(sheetName);/////////////////////////////////// can be NullPointerException
      return workbook;
   }

   private void printEntityYear(String nameEntity, List<? extends Base>entityList){
       String sheetName = nameEntity;
       XSSFSheet excelSheet = workbook.createSheet(sheetName);
       Map<String, Object[]> excelData = new HashMap<>();
/////print year
       Integer positionOnSheet = 0;
       int maxSize=personnel.getStatistic().getYearList().size();

       for(int i=0;i<maxSize;i++) {
           excelData = createEntityYears(excelData, i, positionOnSheet, entityList);

           //  excelData = createEntityYears(excelData, i, positionOnSheet, entityList);
           addIntoCell(excelData, excelSheet, positionOnSheet.toString());
           positionOnSheet +=excelData.size()+1;
       }

   }
    private void printEntityHalfYear(String nameEntity, List<? extends Base>entityList){
        String sheetName = nameEntity;
        XSSFSheet excelSheet = workbook.createSheet(sheetName);
        Map<String, Object[]> excelData = new HashMap<>();
/////print year
        Integer positionOnSheet = 0;
        int maxSizeYear=2;//personnel.getStatistic().getYearList().size();
        int maxSizeHalfYear = 2;
        for(int posYear=0; posYear<maxSizeYear ;posYear++) {
            for(int posHalf=0; posHalf<maxSizeHalfYear; posHalf++) {

                excelData = createEntityHalf(excelData, posYear, posHalf, positionOnSheet, entityList);
                addIntoCell(excelData, excelSheet, positionOnSheet.toString());
                positionOnSheet += excelData.size() + 1;

            }
        }

    }
    private void printEntityQuarter(String nameEntity, List<? extends Base>entityList){
        String sheetName = nameEntity;
        XSSFSheet excelSheet = workbook.createSheet(sheetName);
        Map<String, Object[]> excelData = new HashMap<>();
/////print year
        Integer positionOnSheet = 0;
        int maxSizeYear=2;//personnel.getStatistic().getYearList().size();
        int maxSizeHalfYear = 2;
        int maxSizeQuarter = 2;
        for(int posYear=0; posYear<maxSizeYear ;posYear++) {
            for(int posHalf = 0 ; posHalf < maxSizeHalfYear; posHalf++) {
                for(int posQuart = 0; posQuart < maxSizeQuarter; posQuart++) {

                    excelData = createEntityQuarter(excelData, posYear, posHalf,posQuart, positionOnSheet, entityList);
                    addIntoCell(excelData, excelSheet, positionOnSheet.toString());
                    positionOnSheet += excelData.size() + 1;
                }
            }
        }

    }
    private void printEntityMonth(String nameEntity, List<? extends Base>entityList){
        String sheetName = nameEntity;
        XSSFSheet excelSheet = workbook.createSheet(sheetName);
        Map<String, Object[]> excelData = new HashMap<>();
/////print year
        Integer positionOnSheet = 0;
        int maxSizeYear=2;//personnel.getStatistic().getYearList().size();
        int maxSizeHalfYear = 2;
        int maxSizeQuarter = 2;
        int maxSizeMonth = 3;
        for(int posYear=0; posYear<maxSizeYear ;posYear++) {
            for(int posHalf = 0 ; posHalf < maxSizeHalfYear; posHalf++) {
                for(int posQuart = 0; posQuart < maxSizeQuarter; posQuart++) {
                    for(int posMonth = 0; posMonth < maxSizeMonth; posMonth++) {
                        try {
                            excelData = createEntityMonth(excelData, posYear, posHalf, posQuart, posMonth, positionOnSheet,
                                    entityList);
                            addIntoCell(excelData, excelSheet, positionOnSheet.toString());
                            positionOnSheet += excelData.size() + 1;
                        }catch (IndexOutOfBoundsException e){
                            System.out.print( "Error for posYear - "+ posYear +" posHalf - " + posHalf +" posQuart - " +
                                    posQuart + " posMonth - "+posMonth + "  " + e);
                        }

                    }
                }
            }
        }

    }



    public void printEntity() {
        FileOutputStream out = null;

        List<Coach>coachList=personnel.getCouchList();
        printEntityYear("Coach year",coachList);
        printEntityHalfYear("Coach HalfYear",coachList);
        printEntityQuarter("Coach Quarter",coachList);
        printEntityMonth("Coach Month",coachList);

        List<Store>storeList=personnel.getStoreList();
        printEntityYear("Store year",storeList);
        printEntityHalfYear("Store HalfYear",storeList);
        printEntityQuarter("Store Quarter",storeList);
        printEntityMonth("Store Month",storeList);

        List<City>cityList=personnel.getCityList();
        printEntityYear("City year",cityList);
        printEntityHalfYear("City HalfYear",cityList);
        printEntityQuarter("City Quarter",cityList);
        printEntityMonth("City Month",cityList);

        try {
            // Write the workbook in file system
            File file = new File("C:/demo/employee.xlsx");
            file.getParentFile().mkdirs();
            out = new FileOutputStream(file);
            workbook.write(out);
            workbook.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    private Object[]createTitle(DecreaseAndCreate currentPeriod){
        return  new Object[] {
                "ИМЯ" + "/" +currentPeriod.getPeriodString(),
                "был в салонах",
                "на конец периода салонов",
                "сотр. текущие",
                "сотр. уволенные",
                "Сотр. всего было",
                "среднеспис.",
                "Коф. текуч." };
    }
    private Object[]createLineRecord( DecreaseAndCreate currentPeriod, PeriodDemo entity){

        String name = entity.getName() + "/" +currentPeriod.getPeriodString();
        String storeListAllSize = currentPeriod.getStoreListAllSize();
        String storeListCurrentSize = currentPeriod.getStoreListCurrentSize();
        String employeeListCurrentSize = currentPeriod.getEmployeeListCurrentSize();
        String outOfWorkListSize = currentPeriod.getOutOfWorkListSize();
        String employeeListSize = currentPeriod.getEmployeeListSize();
        String averageCountOfEmployeeSize = currentPeriod.getAverageCountOfEmployee();
        String ktek = currentPeriod.getKtek();

        return new Object[]{
                name
                ,storeListAllSize
                ,storeListCurrentSize
                ,employeeListCurrentSize
                ,outOfWorkListSize
                ,employeeListSize
                ,averageCountOfEmployeeSize
                ,ktek

        };

    }
    public Map<String, Object[]> createEntityYears(Map<String, Object[]> excelData, int positionOfPeriod,
                                                  Integer positionOnSheet, List<? extends Base> entityList){

        Year yearCurrent = getYear(positionOfPeriod, personnel);
        String standartPeriod = yearCurrent.getPeriodString();
        excelData.put(positionOnSheet.toString(),createTitle(yearCurrent));

        ++positionOnSheet;
       excelData.put(positionOnSheet.toString(),createLineRecord(yearCurrent,personnel));


       Object []o;
       Integer count = positionOnSheet;
        Year yearCurrentEntity = null;
       for(Base entity:entityList){

           try {
               yearCurrentEntity = getYear(positionOfPeriod, entity);
          }catch (IndexOutOfBoundsException e) {

                System.out.println("not period for " + standartPeriod + " -  "+ entity.getName());
               continue;
           }

           o = createLineRecord(yearCurrentEntity,entity);
           ++count;
           excelData.put(count.toString(),o);
       }

        return excelData;
   }

    public Map<String, Object[]> createEntityHalf(Map<String, Object[]> excelData, int positionOfPeriodYear,
                                                  int positionOfPeriodHalfYear, Integer positionOnSheet,
                                                  List<? extends Base> entityList){

        HalfYear halfYearCurrent = getHalfYear(positionOfPeriodYear,positionOfPeriodHalfYear, personnel);
        String standartPeriod = halfYearCurrent.getPeriodString();
        excelData.put(positionOnSheet.toString(),createTitle(halfYearCurrent));

        ++positionOnSheet;
        excelData.put(positionOnSheet.toString(),createLineRecord(halfYearCurrent,personnel));


        Object []o;
        Integer count = positionOnSheet;
        HalfYear halfYearCurrentEntity = null;
        for(Base entity:entityList){

            try {
                halfYearCurrentEntity = getHalfYear(positionOfPeriodYear,positionOfPeriodHalfYear, entity);
            }catch (IndexOutOfBoundsException e) {

                System.out.println("not period for " + standartPeriod + " -  "+ entity.getName());
                continue;
            }

            o = createLineRecord(halfYearCurrentEntity,entity);
            ++count;
            excelData.put(count.toString(),o);
        }

        return excelData;
    }

    public Map<String, Object[]> createEntityQuarter(Map<String, Object[]> excelData, int positionOfPeriodYear,
                                                  int positionOfPeriodHalfYear,int positionOfPeriodQuarter, Integer positionOnSheet,
                                                  List<? extends Base> entityList){

        Quarter quarterCurrent = getQuarter(positionOfPeriodYear,positionOfPeriodHalfYear, positionOfPeriodQuarter,personnel);
        String standartPeriod = quarterCurrent.getPeriodString();
        excelData.put(positionOnSheet.toString(),createTitle(quarterCurrent));

        ++positionOnSheet;
        excelData.put(positionOnSheet.toString(),createLineRecord(quarterCurrent,personnel));


        Object []o;
        Integer count = positionOnSheet;
        Quarter quarterCurrentEntity = null;
        for(Base entity:entityList){

            try {
                quarterCurrentEntity = getQuarter(positionOfPeriodYear,positionOfPeriodHalfYear,positionOfPeriodQuarter, entity);
            }catch (IndexOutOfBoundsException e) {

                System.out.println("not period for " + standartPeriod + " -  "+ entity.getName());
                continue;
            }

            o = createLineRecord(quarterCurrentEntity,entity);
            ++count;
            excelData.put(count.toString(),o);
        }

        return excelData;
    }

    public Map<String, Object[]> createEntityMonth(Map<String, Object[]> excelData, int positionOfPeriodYear,
                                                  int positionOfPeriodHalfYear, int positionOfPeriodQuarter,
                                                   int positionOfPeriodMonth, Integer positionOnSheet,
                                                  List<? extends Base> entityList){

        Month monthCurrent = getMonth(positionOfPeriodYear,positionOfPeriodHalfYear,positionOfPeriodQuarter,
                positionOfPeriodMonth, personnel);
        String buf1 = monthCurrent.getPeriodString();
        if(buf1.contains("2018"))
            System.out.print(monthCurrent.getPeriodString());

        String standartPeriod = monthCurrent.getPeriodString();
        excelData.put(positionOnSheet.toString(),createTitle(monthCurrent));

        ++positionOnSheet;
        excelData.put(positionOnSheet.toString(),createLineRecord(monthCurrent,personnel));


        Object []o;
        Integer count = positionOnSheet;
        Month monthCurrentEntity = null;
        for(Base entity:entityList){

            try {
                monthCurrentEntity = getMonth(positionOfPeriodYear,positionOfPeriodHalfYear,positionOfPeriodQuarter,
                        positionOfPeriodMonth, entity);
            }catch (IndexOutOfBoundsException e) {

                System.out.println("not period for " + standartPeriod + " -  "+ entity.getName());
                continue;
            }

            o = createLineRecord(monthCurrentEntity,entity);
            ++count;
            excelData.put(count.toString(),o);
        }

        return excelData;
    }




    private void addIntoCell(Map<String, Object[]> data, XSSFSheet sheet, String excelID) {
        Set<String> keyset = data.keySet();
        Cell cell = null;
        Row row = null;
        Object[] objArr = null;
        int rownum = 0;

        for (String key : keyset) {
            rownum = Integer.parseInt(key);
            row = sheet.createRow(rownum);
            objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                cell = row.createCell(cellnum++);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Integer) {
                    cell.setCellValue((Integer) obj);
                }
                // applying style only for heading
//                if (Integer.parseInt(excelID) < 1) {
//                    cell.setCellStyle(style);
                }
            }
        }
    public Year getYear(int i, PeriodDemo entity){
        Year year ;
        List<Year> list = entity.getStatistic().getYearList();

        year = list.get(i);
        return year;
    }
    public HalfYear getHalfYear(int positionYear, int positionHalfYear, PeriodDemo entity){
        HalfYear half;
        Year year;

        year = getYear(positionYear,entity);
        half = year.getHalfYearList().get(positionHalfYear);
        return half;
    }
    public Quarter getQuarter(int positionYear, int positionHalfYear, int positionQuarter, PeriodDemo entity){

        HalfYear half;
        Quarter quarter;
        half =getHalfYear(positionYear,positionHalfYear,entity);
        quarter = half.getQuarterList().get(positionQuarter);
        return quarter;
    }

    public  Month getMonth( int positionYear, int positionHalfYear, int positionQuarter, int positionMonth,
                            PeriodDemo entity ) throws IndexOutOfBoundsException {
        Quarter quarter;
        Month month;
        quarter = getQuarter(positionYear,positionHalfYear,positionQuarter,entity);
        month = quarter.getMonthList().get(positionMonth);
        return month;
    }


}

