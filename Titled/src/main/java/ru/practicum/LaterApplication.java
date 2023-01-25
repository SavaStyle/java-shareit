package ru.practicum;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class LaterApplication {

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setSilent(true);
        tomcat.getConnector().setPort(8080);

        Context tomcatContext = tomcat.addContext("", null);

        AnnotationConfigWebApplicationContext applicationContext =
                new AnnotationConfigWebApplicationContext();
        applicationContext.scan("ru.practicum");
        applicationContext.setServletContext(tomcatContext.getServletContext());
        applicationContext.refresh();

        // добавляем диспетчер запросов
        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
        Wrapper dispatcherWrapper =
                Tomcat.addServlet(tomcatContext, "dispatcher", dispatcherServlet);
        dispatcherWrapper.addMapping("/");
        dispatcherWrapper.setLoadOnStartup(1);

        tomcat.start();
    }
}