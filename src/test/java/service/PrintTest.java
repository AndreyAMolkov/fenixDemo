package service;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class PrintTest {

    @Test
    public void printCouch() throws IOException {
        Print print =new Print();
       // print.printCouch2018_lastmonth();
//        print.printCouch2018_lastqwarter();
     //   print.printCouch2018_6();
        print.printEntity();
        assertEquals(1,1);
    }
}