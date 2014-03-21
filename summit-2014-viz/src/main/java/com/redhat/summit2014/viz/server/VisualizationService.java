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

import java.security.SecureRandom;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.base.MessageBuilder;
import org.jboss.errai.bus.client.api.messaging.RequestDispatcher;

import com.redhat.summit2014.viz.client.shared.beans.GearNode;
import com.redhat.summit2014.viz.client.shared.beans.ProfileNode;
import com.redhat.summit2014.viz.client.shared.beans.RootNode;
import com.redhat.summit2014.viz.client.shared.beans.UpdateVizEventBean;

/**
 * Service that manages the visualization state and pushes updates
 * to the client.
 *
 * @author eric.wittmann@redhat.com
 */
@ApplicationScoped
public class VisualizationService implements Runnable {
    
    @Inject
    private RequestDispatcher dispatcher;
    
    private RootNode root = new RootNode();

    /**
     * Constructor.
     */
    public VisualizationService() {
    }
    
    @PostConstruct
    private void postConstruct() {
        System.out.println(">> Starting the visualization service.");
        ProfileNode compute1 = new ProfileNode();
        compute1.setName("compute1");
        GearNode gear1 = new GearNode();
        gear1.setName("gear-1");
        compute1.getChildren().add(gear1);
        root.getChildren().add(compute1);
        new Thread(this).start();
    }

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        int counter = 2;
        SecureRandom random = new SecureRandom();
        while (Boolean.TRUE) {
            UpdateVizEventBean payload = createVisualizationBean();
            System.out.println(">> Sending VizUpdate message.");
            MessageBuilder.createMessage()
                .toSubject("VizUpdate") //$NON-NLS-1$
                .signalling()
                .with("bean", payload) //$NON-NLS-1$
                .noErrorHandling()
                .sendNowWith(dispatcher);
            try {
                long delay = random.nextInt(15000) + 5000l;
                Thread.sleep(delay);
                
                // ///////////////////////////////////////////
                // Create a new random profile
                ProfileNode profile = new ProfileNode();
                profile.setName("compute" + counter++);
                int numGears = random.nextInt(5) + 1;
                for (int i=0; i<numGears; i++) {
                    GearNode gear = new GearNode();
                    gear.setName("gear-" + i);
                    profile.getChildren().add(gear);
                }
                root.getChildren().add(profile);
                // ///////////////////////////////////////////
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @return the viz event bean to send to the client
     */
    private UpdateVizEventBean createVisualizationBean() {
        UpdateVizEventBean bean = new UpdateVizEventBean();
        bean.setRoot(root);
        return bean;
    }

}
