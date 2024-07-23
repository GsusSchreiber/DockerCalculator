import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


public class CalculatorFunctionalTest {

    @Test
    public void testAddition() throws IOException, InterruptedException {
        String output = CalculatorUtls.runDockerCommand("add", "2", "2");
        String result = CalculatorUtls.parseResult(output);
        Assert.assertEquals("4", result);
    }

    @Test
    public void testSubtraction() throws IOException, InterruptedException {
        String output = CalculatorUtls.runDockerCommand("subtract", "5", "3");
        String result = CalculatorUtls.parseResult(output);
        Assert.assertEquals("2", result);
    }

    @Test
    public void testMultiplication() throws IOException, InterruptedException {
        String output = CalculatorUtls.runDockerCommand("multiply", "3", "4");
        String result = CalculatorUtls.parseResult(output);
        Assert.assertEquals("12", result);
    }

    @Test
    public void testDivision() throws IOException, InterruptedException {
        String output = CalculatorUtls.runDockerCommand("divide", "10", "2");
        String result = CalculatorUtls.parseResult(output);
        Assert.assertEquals("5", result);
    }

    @Test
    public void testDivisionByZero() throws IOException, InterruptedException {
        String output = CalculatorUtls.runDockerCommand("divide", "10", "0");
        Assert.assertEquals("Error: Cannot divide by zero", output);
    }

    @Test
    public void testScientificNotation() throws IOException, InterruptedException {
        String output = CalculatorUtls.runDockerCommand("multiply", "1000000000000000", "1000000000000000");
        String result = CalculatorUtls.parseResult(output);
        Assert.assertTrue(result.matches("1[0-9]*E\\+\\d+"));
    }

    @Test
    public void testDecimalPrecision() throws IOException, InterruptedException {
        String output = CalculatorUtls.runDockerCommand("add", "1.0000001", "1.0000001");
        String result = CalculatorUtls.parseResult(output);
        Assert.assertEquals("2.0000002", result);

        output = CalculatorUtls.runDockerCommand("add", "1.000000001", "1.000000001");
        result = CalculatorUtls.parseResult(output);
        Assert.assertEquals("2", result);
    }
}