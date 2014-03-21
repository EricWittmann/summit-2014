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

import org.jboss.errai.common.client.api.annotations.Portable;

/**
 * Bean sent to the UI whenever the visualization needs to be updated.
 *
 * @author eric.wittmann@redhat.com
 */
@Portable
public class UpdateVizEventBean implements Serializable {
    
    private static final long serialVersionUID = 3622779841859155223L;

    private RootNode root;
    
    /**
     * Constructor.
     */
    public UpdateVizEventBean() {
    }

    /**
     * @return the root
     */
    public RootNode getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(RootNode root) {
        this.root = root;
    }

}
