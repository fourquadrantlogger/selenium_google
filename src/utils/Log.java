package utils;

/**
 * Created by timeloveboy on 16-12-18.
 */
public class Log {
    public static void v(Object... args) {
        System.out.println();
        for (Object o : args) {
            System.out.print(o);
        }
    }
}
