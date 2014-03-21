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

import com.google.gwt.core.client.JavaScriptObject;

/**
 * The gear node modeled on the client side.
 * 
 * @author eric.wittmann@redhat.com
 */
public class ClientGearNode extends JavaScriptObject {
    
    /**
     * Constructor.
     */
    protected ClientGearNode() {
    }

    /**
     * Create a gear node.
     */
    public final static native ClientGearNode create(String name) /*-{
        return { 'name' : name, 'type' : 'gear', size : 10 };
    }-*/;
    
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
    // Size
    // ********************
    public final native int getSize() /*-{
        return this.size;
    }-*/;
    public final native void setSize(int value) /*-{
        this.size = value;
    }-*/;
}