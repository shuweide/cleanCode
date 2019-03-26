package idv.code.logging;

import org.junit.Test;
import org.slf4j.*;

public class SLF4JTest {
    @Test
    public void testLogging() {
        Logger logger = LoggerFactory.getLogger(SLF4JTest.class);
        logger.info("Test Log4J impl SLF4J info {} {}", "String1", "String2");
        logger.debug("Test Log4J impl SLF4J debug");
        logger.error("Test Log4J impl SLF4J error");
        logger.trace("Test Log4J impl SLF4J trace");
    }
}
