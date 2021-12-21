package ew.sr.x1c.quilt.meow.plugin.NoExplode.api.console.hook;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

// 輸出流控制 直接抓取主控台訊息
public class LoggerHook {

    public static void hookLogger() {
        Logger logger = (Logger) LogManager.getRootLogger();
        LogAppender appender = new LogAppender();
        logger.addAppender(appender);
    }
}
