/*
 * Copyright 2014 JBoss Inc
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

package com.redhat.summit2014.viz.server;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Bootstrapper.
 *
 * @author eric.wittmann@redhat.com
 */
public class StartupServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    @Inject VisualizationService vs;
    
    /**
     * Constructor.
     */
    public StartupServlet() {
    }
    
    /**
     * @see javax.servlet.GenericServlet#init()
     */
    @Override
    public void init() throws ServletException {
        System.out.println("StartupServlet initialized.");
        System.out.println(vs.toString());
    }

}
