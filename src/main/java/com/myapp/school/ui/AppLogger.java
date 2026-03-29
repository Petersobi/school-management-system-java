package com.myapp.school.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AppLogger {
    public static final Logger logger = Logger.getLogger(AppLogger.class.getName());

    private AppLogger(){}

    public static void logError(String message, Exception e){
        logger.log(Level.SEVERE , message,e);
    }
}
