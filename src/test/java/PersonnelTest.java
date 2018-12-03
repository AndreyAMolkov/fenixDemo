
import model.Employee;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import dao.Personnel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class PersonnelTest {
    String path="/ЛЮДИ.xlsx";
    Personnel personnel;
    ArrayList <ArrayList<String>> array;
    @BeforeEach
    void setUp() throws IOException {
     //   personnel=new Personnel(path);

    }


    @Test
    public void getAllEmployee()throws  IOException {
        personnel =new Personnel(path);
    List<Employee> employees = personnel.getAllEmployee();

        assertEquals(1697,employees.size());
    }

}
