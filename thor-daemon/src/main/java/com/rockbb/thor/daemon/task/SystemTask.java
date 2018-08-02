package com.rockbb.thor.daemon.task;

import com.rockbb.thor.commons.api.service.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component("systemTask")
public class SystemTask {
    private static Logger logger = LoggerFactory.getLogger(SystemTask.class);

    @Resource(name="sessionManager")
    private SessionManager sessionManager;

    /**
     * 清理过期Session
     */
    @Scheduled(cron = "50 0/2 * * * ?") // at 50' every 2 minutes
    public void doSessionCleanup()
    {
        logger.debug("Session Cleanup: start");
        sessionManager.cleanup();
        logger.debug("Session Cleanup: end");
    }
}
