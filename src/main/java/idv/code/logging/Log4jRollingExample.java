package idv.code.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//https://www.baeldung.com/java-logging-rolling-file-appenders
public class Log4jRollingExample {
    private static Logger logger = LoggerFactory.getLogger(Log4jRollingExample.class);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 2000; i++) {
            logger.info("This is the " + i + " time I say 'Hello World'.");
            Thread.sleep(100);
        }
    }


}
