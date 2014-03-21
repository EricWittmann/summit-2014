/*
 * Copyright 2013 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.summit2014.devsvr;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.errai.bus.server.servlet.DefaultBlockingServlet;
import org.jboss.weld.environment.servlet.BeanManagerResourceBindingListener;
import org.jboss.weld.environment.servlet.Listener;
import org.overlord.commons.dev.server.DevServerEnvironment;
import org.overlord.commons.dev.server.ErraiDevServer;
import org.overlord.commons.dev.server.MultiDefaultServlet;
import org.overlord.commons.dev.server.discovery.ErraiWebAppModuleFromMavenDiscoveryStrategy;
import org.overlord.commons.dev.server.discovery.WebAppModuleFromIDEDiscoveryStrategy;
import org.overlord.commons.gwt.server.filters.GWTCacheControlFilter;
import org.overlord.commons.gwt.server.filters.ResourceCacheControlFilter;

import com.redhat.summit2014.viz.VizUI;
import com.redhat.summit2014.viz.server.StartupServlet;

/**
 * A dev server for DTGov.
 * @author eric.wittmann@redhat.com
 */
public class Summit2014DevServer extends ErraiDevServer {

    /**
     * Main entry point.
     * @param args
     */
    public static void main(String [] args) throws Exception {
        Summit2014DevServer devServer = new Summit2014DevServer(args);
        devServer.go();
    }

    /**
     * Constructor.
     * @param args
     */
    public Summit2014DevServer(String [] args) {
        super(args);
    }

    /**
     * @see org.overlord.commons.dev.server.DevServer#serverPort()
     */
    @Override
    protected int serverPort() {
        return 8080;
    }

    /**
     * @see org.overlord.commons.dev.server.DevServer#preConfig()
     */
    @Override
    protected void preConfig() {
        // Don't do any resource caching!
        System.setProperty("overlord.resource-caching.disabled", "true");
    }

    /**
     * @see org.overlord.commons.dev.server.ErraiDevServer#getErraiModuleId()
     */
    @Override
    protected String getErraiModuleId() {
        return "viz";
    }

    /**
     * @see org.overlord.commons.dev.server.DevServer#createDevEnvironment()
     */
    @Override
    protected DevServerEnvironment createDevEnvironment() {
        return new Summit2014DevServerEnvironment(args);
    }

    /**
     * @see org.overlord.commons.dev.server.DevServer#addModules(org.overlord.commons.dev.server.DevServerEnvironment)
     */
    @Override
    protected void addModules(DevServerEnvironment environment) {
        environment.addModule("viz",
                new WebAppModuleFromIDEDiscoveryStrategy(VizUI.class),
                new ErraiWebAppModuleFromMavenDiscoveryStrategy(VizUI.class));
    }

    /**
     * @see org.overlord.commons.dev.server.DevServer#addModulesToJetty(org.overlord.commons.dev.server.DevServerEnvironment, org.eclipse.jetty.server.handler.ContextHandlerCollection)
     */
    @Override
    protected void addModulesToJetty(DevServerEnvironment environment, ContextHandlerCollection handlers) throws Exception {
        super.addModulesToJetty(environment, handlers);
        
        ServletContextHandler vizUI = new ServletContextHandler(ServletContextHandler.SESSIONS);
        vizUI.setContextPath("/viz");
        vizUI.setWelcomeFiles(new String[] { "index.html" });
        vizUI.setResourceBase(environment.getModuleDir("viz").getCanonicalPath());
        vizUI.setInitParameter("errai.properties", "/WEB-INF/errai.properties");
        vizUI.setInitParameter("login.config", "/WEB-INF/login.config");
        vizUI.setInitParameter("users.properties", "/WEB-INF/users.properties");
        vizUI.addEventListener(new Listener());
        vizUI.addEventListener(new BeanManagerResourceBindingListener());
        vizUI.addFilter(GWTCacheControlFilter.class, "/app/*", EnumSet.of(DispatcherType.REQUEST));
        vizUI.addFilter(ResourceCacheControlFilter.class, "/lib/*", EnumSet.of(DispatcherType.REQUEST));
        vizUI.addFilter(ResourceCacheControlFilter.class, "/css/*", EnumSet.of(DispatcherType.REQUEST));
        vizUI.addFilter(ResourceCacheControlFilter.class, "/images/*", EnumSet.of(DispatcherType.REQUEST));
        vizUI.addFilter(ResourceCacheControlFilter.class, "/js/*", EnumSet.of(DispatcherType.REQUEST));
        // Servlets
        ServletHolder erraiServlet = new ServletHolder(DefaultBlockingServlet.class);
        erraiServlet.setInitOrder(1);
        vizUI.addServlet(erraiServlet, "*.erraiBus");

        ServletHolder startupServlet = new ServletHolder(StartupServlet.class);
        startupServlet.setInitOrder(1);
        vizUI.addServlet(startupServlet, "/startup");

        // File resources
        ServletHolder resources = new ServletHolder(new MultiDefaultServlet());
        resources.setInitParameter("resourceBase", "/");
        resources.setInitParameter("resourceBases", environment.getModuleDir("viz").getCanonicalPath());
        resources.setInitParameter("dirAllowed", "true");
        resources.setInitParameter("pathInfoOnly", "false");
        String[] fileTypes = new String[] { "html", "js", "css", "png", "gif" };
        for (String fileType : fileTypes) {
            vizUI.addServlet(resources, "*." + fileType);
        }

        handlers.addHandler(vizUI);
    }

	/**
     * @see org.overlord.commons.dev.server.DevServer#postStart(org.overlord.commons.dev.server.DevServerEnvironment)
     */
    @Override
    protected void postStart(DevServerEnvironment environment) throws Exception {
        System.out.println("----------  DONE  ---------------");
        System.out.println("Now try:  \n  http://localhost:"+serverPort()+"/viz/index.html");
        System.out.println("---------------------------------");
    }

}
