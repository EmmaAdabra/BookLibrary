import java.util.HashMap;
import java.util.function.Function;

public class IterateInput {
    static public String stringInput(String prompt, Function<String, HashMap<String, String>> validate){
        String value;
        HashMap<String, String> response;
        while (true) {
            value = Console.readString(prompt);
            response = validate.apply(value);
            if(Boolean.parseBoolean(response.get("code")))
                break;
            System.out.println(response.get("message"));
        }
        return value;
    }

    static public int intInput(String prompt, int min, int max,
                               TriFunction<Integer, Integer, Integer, HashMap<String, String>> validate) {
        int option;
        HashMap<String, String> response;
        while (true) {
            option = Console.readInt(prompt);
            response = validate.apply(option, min, max);
            if(Boolean.parseBoolean(response.get("code")))
                break;
            System.out.println(response.get("message"));
        }

        return option;
    }
}
