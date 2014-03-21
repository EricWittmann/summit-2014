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

package com.redhat.summit2014.viz.client.shared.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jboss.errai.common.client.api.annotations.Portable;

/**
 * A single gear profile (e.g. compute1, compute2, compute3, compute4).
 *
 * @author eric.wittmann@redhat.com
 */
@Portable
public class ProfileNode implements Serializable {

    private static final long serialVersionUID = 1560994442329761845L;
    
    private String name;
    private String type = "profile";
    private List<GearNode> children = new ArrayList<GearNode>();
    
    /**
     * Constructor.
     */
    public ProfileNode() {
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the children
     */
    public List<GearNode> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List<GearNode> children) {
        this.children = children;
    }

    /**
     * @param gearName
     */
    public boolean hasGear(String gearName) {
        for (GearNode gearNode : getChildren()) {
            if (gearNode.getName().equals(gearName)) {
                return true;
            }
        }
        return false;
    }

}
