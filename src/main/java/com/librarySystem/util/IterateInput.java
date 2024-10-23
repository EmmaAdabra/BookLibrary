package main.java.com.librarySystem.util;

import java.util.function.Function;


public class IterateInput {
    static Console console = new Console();
    static public String stringInput(String prompt, Function<String, Response> validate) {
        String value;
        Response response;
        while (true) {
            value = console.readString(prompt);
            response = validate.apply(value);
            if (response.code == 1)
                break;
            System.out.println(response.message);
        }
        return value;
    }

    static public int intInput(String prompt, int min, int max,
                               TriFunction<Integer, Integer, Integer, Response> validate) {
        int option;
        Response response;
        while (true) {
            option = console.readInt(prompt);
            response = validate.apply(option, min, max);
            if (response.code == 1)
                break;
            System.out.println(response.message);
        }

        return option;
    }
}
