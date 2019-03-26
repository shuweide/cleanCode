package idv.code.time;

import java.time.LocalDate;
import java.time.temporal.Temporal;

public class QueryTest {
    public static void main(String[] args) {
        QuarterOfYearQuery q = new QuarterOfYearQuery();

        Quarter quarter = q.queryFrom(LocalDate.now());
        System.out.println(quarter);

        quarter = LocalDate.now().query(q);
        System.out.println(quarter);

        LocalDate now = LocalDate.now();
        Temporal firstDayOfQuarter = now.with(new FirstDayOfQuarter());
        System.out.println(firstDayOfQuarter);
    }
}
