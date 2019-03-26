package idv.code.concurrent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatWrapper {
    private static final ThreadLocal<SimpleDateFormat> SDF =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static String format(Date date) {
        return SDF.get().format(date);
    }

    public static Date parse(String str) throws ParseException {
        return SDF.get().parse(str);
    }
}
