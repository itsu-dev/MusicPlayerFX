package itsu.java.musicplayerfx.Utils;

public class FormatUtil {

    public static String secToString(double rawSec) {
        double min = rawSec / 60;
        double sec = rawSec % 60;

        String secString = "" + (int) sec;
        if (sec < 10) secString = "0" + secString;

        return (int) min + ":" + secString;
    }

}
