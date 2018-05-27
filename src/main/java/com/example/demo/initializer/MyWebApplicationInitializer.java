package com.example.demo.initializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;

public class MyWebApplicationInitializer implements WebApplicationInitializer {
    private Logger loger = LogManager.getLogger(this.getClass());

    @Override
    public void onStartup(ServletContext arg0) throws ServletException {
        loger.info("MyWebApplicationInitializer is running!");
    }
}
