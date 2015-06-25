package ge.kuku.movie.parse.core;

import ge.kuku.movie.parse.data.CompositeParser;
import ge.kuku.movie.parse.data.ImoviesParser;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        CompositeParser cParser = CompositeParser.instance();
        cParser.add(new ImoviesParser());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
