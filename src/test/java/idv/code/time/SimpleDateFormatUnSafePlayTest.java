package idv.code.time;

import org.junit.Test;

public class SimpleDateFormatUnSafePlayTest {
    
    @Test(expected=Exception.class)
    public void testSimpleDateFormat() throws Exception{
        SimpleDateFormatUnSafePlay play = new SimpleDateFormatUnSafePlay();
        play.simpleDateFormatPlay();
    }
    
    @Test
    public void testDateTimeFormatter() throws Exception{
        SimpleDateFormatUnSafePlay play = new SimpleDateFormatUnSafePlay();
        play.dateTimeformatterPlay();
    }
}
