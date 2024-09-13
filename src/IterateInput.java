import java.util.HashMap;
import java.util.function.Function;

public class IterateInput {
    static public String stringInput(String prompt, Function<String, HashMap<String, String>> validate){
        String value;
        HashMap<String, String> response;
        while (true) {
            System.out.print(prompt + ": ");
            value = Console.readString();
            response = validate.apply(value);
            if(Boolean.parseBoolean(response.get("code")))
                break;
            System.out.println(response.get("message"));
        }
        return value;
    }

    static public byte byteInput(String prompt, byte min, byte max,
                                 TriFunction<Byte, Byte, Byte, HashMap<String, String>> validate) {
        Byte option;
        HashMap<String, String> response;
        while (true) {
            System.out.print(prompt + ": ");
            option = Console.readByte();
            response = validate.apply(option, min, max);
            if(Boolean.parseBoolean(response.get("code")))
                break;
            System.out.println(response.get("message"));
        }

        return option;
    }
}
