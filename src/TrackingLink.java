import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.lf5.util.ResourceUtils;

public class TrackingLink {
    static String returnBody="";
    

    public static List<String> readFile() throws FileNotFoundException, IOException {
        List<String> list = new ArrayList<>();
        String TEXT_FILE = "CapstoneAJ/LinksClicked/links.txt";

        File textFile = new File(TEXT_FILE);
        try (BufferedReader br = new BufferedReader(new FileReader(textFile));) {
            list = br.lines().collect(Collectors.toList());
        }
        return list;
    }

    public static void Init(String company) throws FileNotFoundException, IOException {
        List<String> Links = new ArrayList<>();
       int length =(company.length());
        Links = readFile();

        for (int i = 1; i < Links.size(); i=i+2) {
            String person =Links.get(i);
            String personsub= person.substring(0,length);
            if (company.equals(personsub)){
                returnBody+= "Employee Number " + person.substring(length) + " has clicked the link. <br>";
            }
        }
    }

    public static String readFile(String company) throws FileNotFoundException, IOException {
        Init(company);
        return returnBody;

    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        System.out.println(readFile("Pizza"));
    }
}
