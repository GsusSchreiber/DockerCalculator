import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorUtls {


    static String runDockerCommand(String operation, String num1, String num2) throws IOException, InterruptedException {
        List<String> command = Arrays.asList("docker", "run", "--rm",
                "public.ecr.aws/l4q9w4c5/loanpro-calculator-cli", operation, num1, num2);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            output.append(line);
        }

        process.waitFor();
        return output.toString().trim();
    }


    static String parseResult(String output) {
        Pattern pattern = Pattern.compile("Result: (.+)");
        Matcher matcher = pattern.matcher(output);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "Error: Unexpected output format";
        }
    }
}
