import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(Parameterized.class)
public class CalculatorTest {

    private final String operation;
    private final String num1;
    private final String num2;

    public CalculatorTest(String operation, String num1, String num2) {
        this.operation = operation;
        this.num1 = num1;
        this.num2 = num2;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws IOException {
        return readTestDataFromCSV("src/main/resources/testData");
    }

    private static List<Object[]> readTestDataFromCSV(String filePath) throws IOException {
        List<Object[]> data = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            reader.readLine(); // for header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                data.add(new Object[] { parts[0].replaceAll("\"",""), parts[1].replaceAll("\"",""), parts[2].replaceAll("\"","")});
            }
        }
        return data;
    }

    @Test
    public void testCalculatorOperations() throws IOException, InterruptedException {
        BigDecimal res = new BigDecimal(0);
        String output = CalculatorUtls.runDockerCommand(operation, num1, num2);
        String message;
        switch (operation) {
            case "add":
                res = new BigDecimal(num1).add(new BigDecimal(num2)) ;
                message = "expected result: "+ res;
                break;
            case "multiply":
                res = new BigDecimal(num1).multiply(new BigDecimal(num2)) ;
                message = "expected result: "+ res;
                break;
            case "divide":
                res = new BigDecimal(num1).divide(new BigDecimal(num2)) ;
                message = "expected result: "+ res;                break;
            case "subtract":
                res = new BigDecimal(num1).subtract(new BigDecimal(num2)) ;
                message = "expected result: "+ res;                break;
            default:
                message = "not a operation";
                break;
        }
        System.out.println("Test - For: " + num1 + " " + operation+ " "+ num2+ " actual "+ output+" "+message);
        Assert.assertEquals(""+res, CalculatorUtls.parseResult(output));
    }
}
