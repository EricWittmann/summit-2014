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

package com.redhat.summit2014.viz.client.local.data;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.shared.GWT;
import com.redhat.summit2014.viz.client.shared.beans.GearNode;
import com.redhat.summit2014.viz.client.shared.beans.ProfileNode;

/**
 * The root node modeled on the client side.
 * 
 * @author eric.wittmann@redhat.com
 */
public class ClientProfileNode extends JavaScriptObject {
    
    /**
     * Constructor.
     */
    protected ClientProfileNode() {
    }
    
    /**
     * Create a profile node.
     */
    public final static native ClientProfileNode create(String name) /*-{
        return { 'name' : name, 'type' : 'profile', children : [] };
    }-*/;

    /**
     * @param profileNode
     */
    public final void merge(ProfileNode profileNode) {
        GWT.log("  > Merging profile: " + profileNode.getName());
        List<GearNode> children = profileNode.getChildren();
        // Add or update gears
        for (GearNode gearNode : children) {
            ClientGearNode clientGear = getGear(gearNode.getName());
            if (clientGear == null) {
                addGear(gearNode);
            } else {
                updateGear(clientGear, gearNode);
            }
        }
        
        // Remove missing gears
        JsArray<ClientGearNode> clientChildren = getChildren();
        int numGears = clientChildren.length();
        int expectedGears = profileNode.getChildren().size();
        if (numGears > expectedGears) {
            GWT.log("  > Removing (at most) " + (numGears-expectedGears) + ".");
            for (int i=numGears-1; i >= 0; i--) {
                ClientGearNode child = clientChildren.get(i);
                if (!profileNode.hasGear(child.getName())) {
                    GWT.log("    > Deleting gear: " + child.getName());
                    JsArrayUtil.remove(clientChildren, i, 1);
                }
            }
        }
    }

    /**
     * @param name
     */
    private final ClientGearNode getGear(String name) {
        JsArray<ClientGearNode> children = getChildren();
        for (int i=0; i < children.length(); i++) {
            ClientGearNode child = children.get(i);
            if (child.getName().equals(name)) {
                return child;
            }
        }
        return null;
    }

    /**
     * @param gearNode
     */
    private final void addGear(GearNode gearNode) {
        GWT.log("    > Adding gear: " + gearNode.getName());
        ClientGearNode clientGear = ClientGearNode.create(gearNode.getName());
        GWT.log("    > Created gear: " + clientGear.getName() + "  type: " + clientGear.getType());
        clientGear.setSize(gearNode.getSize());
        getChildren().push(clientGear);
    }

    /**
     * @param clientGear
     * @param gearNode
     */
    private final void updateGear(ClientGearNode clientGear, GearNode gearNode) {
        GWT.log("    > Updating gear: " + gearNode.getName());
        clientGear.setSize(gearNode.getSize());
    }

    // ********************
    // Name
    // ********************
    public final native String getName() /*-{
		return this.name;
    }-*/;
    public final native void setName(String value) /*-{
		this.name = value;
    }-*/;
    
    
    // ********************
    // Type
    // ********************
    public final native String getType() /*-{
        return this.type;
    }-*/;
    public final native void setType(String value) /*-{
        this.type = value;
    }-*/;

    
    // ********************
    // Children
    // ********************
    public final native JsArray<ClientGearNode> getChildren() /*-{
        return this.children;
    }-*/;
    public final native void setChildren(JsArray<ClientGearNode> value) /*-{
        this.children = value;
    }-*/;
}