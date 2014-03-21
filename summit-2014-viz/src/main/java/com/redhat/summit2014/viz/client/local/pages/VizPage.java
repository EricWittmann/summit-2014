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

package com.redhat.summit2014.viz.client.local.pages;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.ClientMessageBus;
import org.jboss.errai.bus.client.api.messaging.Message;
import org.jboss.errai.bus.client.api.messaging.MessageCallback;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.PageShown;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.redhat.summit2014.viz.client.local.data.ClientProfileNode;
import com.redhat.summit2014.viz.client.local.data.ClientRootNode;
import com.redhat.summit2014.viz.client.shared.beans.UpdateVizEventBean;

/**
 * The default/only page.
 *
 * @author eric.wittmann@redhat.com
 */
@Page(path="home", role=DefaultPage.class)
@Dependent
public class VizPage extends FlowPanel {

    @Inject
    protected ClientMessageBus bus;
    
    ClientRootNode root = ClientRootNode.create();
    
    /**
     * Constructor.
     */
    public VizPage() {
    }
    
    @PostConstruct
    private void pc() {
        ClientProfileNode compute1 = ClientProfileNode.create("compute1");
        root.getChildren().push(compute1);
        updateVisualization(root);
        add(new InlineLabel("SUCCESSFULLY CREATED PAGE!"));
    }

    /**
     * Called whenver the page is shown.
     */
    @PageShown
    public void onPageShown() {
        GWT.log("Subscribing to VizUpdate topic.");
        bus.subscribe("VizUpdate", new MessageCallback() {
            @Override
            public void callback(Message message) {
                UpdateVizEventBean bean = message.get(UpdateVizEventBean.class, "bean"); //$NON-NLS-1$
                try {
                    onUpdateViz(bean);
                } catch (Exception e) {
                    Window.alert(e.toString());
                }
            }
        });
    }

    /**
     * @param bean
     */
    protected void onUpdateViz(UpdateVizEventBean bean) {
        GWT.log("Updating visualization.");
        root.merge(bean.getRoot());
        updateVisualization(root);
    }
    
    /**
     * @param data the visualization data
     */
    public static final native void updateVisualization(JavaScriptObject data) /*-{
        try {
            $wnd.update(data);
        } catch (e) {
            $wnd.alert(e);
        }
    }-*/;

}
