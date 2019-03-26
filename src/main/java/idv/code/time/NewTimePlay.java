package idv.code.time;

import static java.lang.System.*;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class NewTimePlay {

    public void play() {

        // Instant類別用來定義一個瞬間的時間點
        final Instant instant = Instant.parse("2017-01-01T00:00:00Z");

        // Duration類別用來定義一個時間區段,Duration最小的單位可到奈秒(Nanosecond)
        out.println("===============Duration=============");
        final Duration duration = Duration.ofMinutes(20);
        final Instant plusDurInstant = instant.plus(duration);

        out.println("instant:" + instant);
        out.println("plusDurInstant:" + plusDurInstant);

        // Period類別用來定義一個時間週期,Period最小的單位則是天(day)
        out.println("===============Period=============");
        final Period period = Period.ofDays(20);
        final Instant plusPerInstant = instant.plus(period);
        out.println("instant:" + instant);
        out.println("plusPerInstant:" + plusPerInstant);

        // ZoneId類別與時區有關
        out.println("===============ZoneId=============");
        final ZoneId zoneIdDefault = ZoneId.systemDefault();
        final ZoneId zoneIdPlus8 = ZoneId.of("UTC+8");
        final LocalDateTime nowLocalDateTime = LocalDateTime.ofInstant(Instant.now(), zoneIdPlus8);

        out.println("nowLocalDateTime:" + nowLocalDateTime);

        // ZoneOffset物件繼承ZoneId，但它只專注在時間的位移上，而沒有地區與標準時間的概念。
        out.println("===============ZoneOffset=============");
        final ZoneOffset zoneOffset1hr1 = (ZoneOffset) ZoneId.of("+1");
        final ZoneOffset zoneOffset1hr2 = ZoneOffset.of("+1");
        final ZoneOffset zoneOffset1hr3 = ZoneOffset.ofHours(1);
        final Instant nowLocalDateTimeToInstant = nowLocalDateTime.toInstant(zoneOffset1hr3);

        out.println("nowLocalDateTimeToInstant:" + nowLocalDateTimeToInstant);

        // Clock物件可用來找出目前指定時區的時間點
        out.println("===============Clock=============");

        final LocalDateTime currentPoint = LocalDateTime.now(); // 預設代入Clock.systemDefaultZone()
        out.println("currentPoint:" + currentPoint);

        final LocalDateTime currentPointUTC = LocalDateTime.now(Clock.systemUTC());
        final LocalDateTime currentPointDefault = LocalDateTime.now(Clock.systemDefaultZone()); // 同LocalDateTime.now();
        final LocalDateTime currentPointPlus8 = LocalDateTime.now(Clock.system(ZoneId.of("+8")));

        out.println("currentPointUTC:" + currentPointUTC);
        out.println("currentPointDefault:" + currentPointDefault);
        out.println("currentPointPlus8:" + currentPointPlus8);

        // of是類別方法，可以直接傳入年、月、日、小時、分鐘、秒等時間數值來產生LocalDate、LocalTime和LocalDateTime物件。
        out.println("=================Of================");
        final LocalDateTime qingming = LocalDateTime.of(2015, 4, 5, 12, 30, 30);
        final LocalDate qingmingDate = LocalDate.of(2015, 4, 5); // 同qingming.toLocalDate()
        final LocalTime qingmingTime = LocalTime.of(12, 30, 30); // 同qingming.toLocalTime()

        out.println("qingming:" + qingming);
        out.println("qingmingDate:" + qingmingDate);
        out.println("qingmingTime:" + qingmingTime);

        // parse是類別方法，可以直接傳入字串來產生LocalDate、LocalTime和LocalDateTime物件。傳入的字串格式可由DateTimeFormatter來決定，
        // 預設是使用DateTimeFormatter.ISO_LOCAL_DATE_TIME
        out.println("=================Parser================");
        final LocalDateTime localDateTime = LocalDateTime.parse("2017-01-01T12:00:00");
        final LocalDateTime localDateTimeForPattern = LocalDateTime.parse("2015/04/05 12:30:30",
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

        out.println("localDateTime:" + localDateTime);
        out.println("localDateTimeForPattern:" + localDateTimeForPattern);
        out.println("localDateFormat:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));

        // LocalDateTime
        out.println("=================LocalDateTime=============");
        final LocalDateTime currentDateTime = LocalDateTime.now();
        final int year = currentDateTime.getYear();
        final int month = currentDateTime.getMonthValue();
        final Month m = currentDateTime.getMonth();
        final int day = currentDateTime.getDayOfMonth();
        final DayOfWeek w = currentDateTime.getDayOfWeek();
        final int hour = currentDateTime.getHour();
        final int minute = currentDateTime.getMinute();
        final int second = currentDateTime.getSecond();

        out.println("currentDateTime:" + currentDateTime);
        out.println("year:" + year);
        out.println("month:" + month);
        out.println("m:" + m);
        out.println("day:" + day);
        out.println("w:" + w);
        out.println("hour:" + hour);
        out.println("minute:" + minute);
        out.println("second:" + second);

        // java.time套件底下的物件都是Immutable Object，一旦被建立後，就只能讀取，不能再被改變。
        out.println("=============Modify LocalDate=====================");
        final LocalDate currentDate = LocalDate.now();
        final LocalDate thisMonth = currentDate.withDayOfMonth(1); // 將日期指定為該月1號。注意這裡currentDate並沒有被改變！
        final LocalDateTime nextWeekDateTime = LocalDateTime.now().plusWeeks(1);
        final LocalDateTime next7DaysDateTime = LocalDateTime.now().plusDays(7);

        out.println("currentDate:" + currentDate);
        out.println("thisMonthFirstDay:" + thisMonth);
        out.println("nextWeekDateTime:" + nextWeekDateTime);
        out.println("next7DaysDateTime:" + next7DaysDateTime);

        // TemporalAdjusters提供了許多類別方法來從某一時間點下跳到其他的時間點，不需要設計師自己多寫程式來實作。
        // TemporalAdjusters提供了next和nextOrSame與previous和previousOrSame方法，可以快速找出某一時間點往前或是往後距離最近的星期日子。
        final LocalDateTime lastDayOfMonthDateTime = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth());
        final LocalDateTime previousWednesDateTime = LocalDateTime.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.WEDNESDAY));
        final LocalDateTime nextWednesdayDateTime = LocalDateTime.now()
                .with(TemporalAdjusters.nextOrSame(DayOfWeek.WEDNESDAY));

        out.println("lastDayOfMonthDateTime:" + lastDayOfMonthDateTime);
        out.println("previousWednesDateTime:" + previousWednesDateTime);
        out.println("nextWednesdayDateTime:" + nextWednesdayDateTime);
        
        //只有LocalTime與LocalDateTime物件有truncatedTo方法可以使用
        out.println("===============Truncation=================");
        final LocalDateTime hourDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS); //捨棄比小時小的單位
        
        out.println("hourDateTime:" +hourDateTime);
        
        out.println("==============ZonedDateTime=================");
        final ZonedDateTime zoneCurrentPoint = ZonedDateTime.now(); //同ZonedDateTime.now(Clock.systemDefaultZone())。直接使用ZonedDateTime類別來取得日期與時間，由於Clock物件已有時區資訊，故不用再代入時區
        final LocalDateTime currentDateTime1 = LocalDateTime.now();
        final ZonedDateTime zonedCurrentDateTime = currentDateTime1.atZone(ZoneId.of("+8"));
        final Instant zonedCurrentInstant = ZonedDateTime.now().toInstant();
        
        out.println("zoneCurrentPoint:" + zoneCurrentPoint);
        out.println("currentDateTime1:" + currentDateTime1);
        out.println("zonedCurrentDateTime:" + zonedCurrentDateTime);
        out.println("zonedCurrentInstant:" + zonedCurrentInstant);
        
        //java.time套件除了能支援國際通用的ISO 8601日期與時間的表示方式外，還支援一些non-ISO的曆法，相關API都在java.time.chrono套件內。
        out.println("========java.time.chrono======");
        final JapaneseChronology japaneseChronology = JapaneseChronology.INSTANCE;
        final JapaneseDate japaneseDate = japaneseChronology.dateNow();
        
        out.println("japaneseDate:" + japaneseDate);
        
        out.println("=========YearMonth與MonthDay=========");
        final MonthDay birthday = MonthDay.of(8, 10);
        final YearMonth creditCard = YearMonth.of(2015, 4);
        
        final LocalDate birthdayLocalDate = birthday.atYear(1993);
        final LocalDate creditCardLocalDate = creditCard.atDay(5);
        
        out.println("birthday:" + birthday);
        out.println("creditCard:" + creditCard);
        out.println("birthdayLocalDate:" + birthdayLocalDate);
        out.println("creditCardLocalDate:" + creditCardLocalDate);
        
        
    }

}
