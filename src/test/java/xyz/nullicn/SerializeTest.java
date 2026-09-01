package xyz.nullicn;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

@DisplayName("序列化测试")
public class SerializeTest {


    @Test
    @DisplayName("测试序列化")
    public void SerializeDemo()
    {
        Employee e = new Employee();
        e.name = "Reyan Ali";
        e.address = "Phokka Kuan, Ambehta Peer";
        e.SSN = 11122333;
        e.number = 101;
        try
        {
            FileOutputStream fileOut =
                    new FileOutputStream("D:/CODE/ProjectStudy/JavaBasicWithJupiterTest/employee.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(e);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in D:/CODE/ProjectStudy/JavaBasicWithJupiterTest/employee.ser");
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }
}

class Employee implements java.io.Serializable
{
    public String name;
    public String address;
    public transient int SSN;
    public int number;
    public void mailCheck()
    {
        System.out.println("Mailing a check to " + name
                + " " + address);
    }
}
