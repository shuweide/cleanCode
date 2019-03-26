package idv.code.time;

import org.junit.Assert;
import org.junit.Test;

import java.time.*;
import java.time.chrono.JapaneseDate;
import java.time.chrono.MinguoDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Locale;

import static java.time.temporal.TemporalAdjusters.*;

public class Java8InActionCh12Test {

    @Test
    public void period() {
        Period tenDays = Period.ofDays(10);
        System.out.println(tenDays.get(ChronoUnit.DAYS));
    }

    @Test
    public void duration() {
        Duration tenDays = Duration.ofDays(10);
        System.out.println(tenDays.get(ChronoUnit.SECONDS));
    }

    @Test
    public void testTemporalAdjuster() {
        LocalDate date1 = LocalDate.of(2017, 12, 17);
        //找出下一個Sunday
        LocalDate date2 = date1.with(nextOrSame(DayOfWeek.SUNDAY));
        LocalDate date3 = date1.with(next(DayOfWeek.SUNDAY));
        //找出本月最後一天
        LocalDate date4 = date1.with(lastDayOfMonth());
        LocalDate date5 = date1.with(firstInMonth(DayOfWeek.SUNDAY));

        System.out.println(date1);
        System.out.println(date2);
        System.out.println(date3);
        System.out.println(date4);
        System.out.println(date5);
    }

    class NextWorkingDay implements TemporalAdjuster {
        @Override
        public Temporal adjustInto(Temporal temporal) {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY) dayToAdd = 3;
            else if (dow == DayOfWeek.SATURDAY) dayToAdd = 2;
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        }
    }

    @Test
    public void testCustomTemporalAdjuster() {
        LocalDate friday = LocalDate.of(2017, 12, 15);
        LocalDate nextWorkingDay = friday.with(new NextWorkingDay());
        System.out.println(nextWorkingDay);
        Assert.assertEquals(DayOfWeek.MONDAY, nextWorkingDay.getDayOfWeek());

        LocalDate saturday = LocalDate.of(2017, 12, 16);
        nextWorkingDay = saturday.with(new NextWorkingDay());
        System.out.println(nextWorkingDay);
        Assert.assertEquals(DayOfWeek.MONDAY, nextWorkingDay.getDayOfWeek());

        LocalDate sunday = LocalDate.of(2017, 12, 17);
        nextWorkingDay = sunday.with(new NextWorkingDay());
        System.out.println(nextWorkingDay);
        Assert.assertEquals(DayOfWeek.MONDAY, nextWorkingDay.getDayOfWeek());

        nextWorkingDay = nextWorkingDay.with(new NextWorkingDay());
        Assert.assertEquals(DayOfWeek.TUESDAY, nextWorkingDay.getDayOfWeek());
        nextWorkingDay = nextWorkingDay.with(new NextWorkingDay());
        Assert.assertEquals(DayOfWeek.WEDNESDAY, nextWorkingDay.getDayOfWeek());
        nextWorkingDay = nextWorkingDay.with(new NextWorkingDay());
        Assert.assertEquals(DayOfWeek.THURSDAY, nextWorkingDay.getDayOfWeek());
        nextWorkingDay = nextWorkingDay.with(new NextWorkingDay());
        Assert.assertEquals(DayOfWeek.FRIDAY, nextWorkingDay.getDayOfWeek());
    }

    // all the DateTimeFormatter instances are thread-safe. Therefore, you can create singleton formatters,
    // like the ones defined by the DateTimeFormatter constants, and share them among multiple threads.
    @Test
    public void testDateFormatter() {
        LocalDate date = LocalDate.of(2017, 12, 18);
        String s1 = date.format(DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(s1);
        String s2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(s2);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String s3 = date.format(formatter);
        System.out.println(s3);
    }

    @Test
    public void testZone() {
        ZoneId romeZone = ZoneId.of("Europe/Rome");

        LocalDate date = LocalDate.of(2017, 12, 18);
        ZonedDateTime zdt1 = date.atStartOfDay(romeZone);
        System.out.println(zdt1);

        LocalDateTime dateTime = LocalDateTime.of(2017, 12, 18, 01, 45, 00);
        ZonedDateTime zdt2 = dateTime.atZone(romeZone);
        System.out.println(zdt2);

        Instant instant = Instant.now();
        ZonedDateTime zdt3 = instant.atZone(romeZone);
        System.out.println(zdt3);

    }

    @Test
    public void testAlternativeCalendar() {
        LocalDate date = LocalDate.of(2017, 12, 18);
        JapaneseDate japaneseDate = JapaneseDate.from(date);
        System.out.println(japaneseDate);
        //https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
        System.out.println(japaneseDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd EEEE", Locale.JAPANESE)));

        MinguoDate minguoDate = MinguoDate.from(date);
        System.out.println(minguoDate);
        System.out.println(minguoDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd EEEE", Locale.TAIWAN)));
    }

}
