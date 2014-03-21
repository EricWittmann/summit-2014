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
import com.redhat.summit2014.viz.client.shared.beans.ProfileNode;
import com.redhat.summit2014.viz.client.shared.beans.RootNode;

/**
 * The root node modeled on the client side.
 * 
 * @author eric.wittmann@redhat.com
 */
public class ClientRootNode extends JavaScriptObject {
    
    /**
     * Constructor.
     */
    protected ClientRootNode() {
    }
    
    /**
     * Create a root node.
     */
    public final static native ClientRootNode create() /*-{
        return { 'name' : 'root', 'type' : 'root', children : [] };
    }-*/;
    
    /**
     * @param root
     */
    public final void merge(RootNode root) {
        GWT.log("Merging root changes.");
        List<ProfileNode> children = root.getChildren();
        // Add or update profiles
        for (ProfileNode profileNode : children) {
            ClientProfileNode clientProfile = getProfile(profileNode.getName());
            if (clientProfile == null) {
                addProfile(profileNode);
            } else {
                updateProfile(clientProfile, profileNode);
            }
        }
        
        // Remove missing profiles
        JsArray<ClientProfileNode> clientChildren = getChildren();
        int numProfiles = clientChildren.length();
        int expectedProfiles = root.getChildren().size();
        if (numProfiles > expectedProfiles) {
            GWT.log("  > Removing (at most) " + (numProfiles-expectedProfiles) + ".");
            for (int i=numProfiles-1; i >= 0; i--) {
                ClientProfileNode child = clientChildren.get(i);
                if (!root.hasProfile(child.getName())) {
                    GWT.log("  > Deleting profile: " + child.getName());
                    JsArrayUtil.remove(clientChildren, i, 1);
                }
            }
        }
    }

    /**
     * @param name
     */
    private final ClientProfileNode getProfile(String name) {
        JsArray<ClientProfileNode> children = getChildren();
        for (int i=0; i < children.length(); i++) {
            ClientProfileNode child = children.get(i);
            if (child.getName().equals(name)) {
                return child;
            }
        }
        return null;
    }

    /**
     * @param profileNode
     */
    private final void addProfile(ProfileNode profileNode) {
        GWT.log("  > New profile: " + profileNode.getName());
        ClientProfileNode clientProfile = ClientProfileNode.create(profileNode.getName());
        GWT.log("  > Created profile: " + clientProfile.getName() + "  type: " + clientProfile.getType());
        getChildren().push(clientProfile);
        clientProfile.merge(profileNode);
    }

    /**
     * @param clientProfile
     * @param profileNode
     */
    private final void updateProfile(ClientProfileNode clientProfile, ProfileNode profileNode) {
        GWT.log("  > Updating profile: " + profileNode.getName());
        clientProfile.merge(profileNode);
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
    public final native JsArray<ClientProfileNode> getChildren() /*-{
        return this.children;
    }-*/;
    public final native void setChildren(JsArray<ClientProfileNode> value) /*-{
        this.children = value;
    }-*/;
}