/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package oculus.aperture.common.rest;

import org.restlet.resource.ServerResource;

/**
 * Definition for a REST resource, containing the resource class.
 */
public class ResourceDefinition {

    private Class<? extends ServerResource> resourceClass;

    public ResourceDefinition(Class<? extends ServerResource> resourceClass) {
        this.resourceClass = resourceClass;
    }

    public Class<? extends ServerResource> getResourceClass() {
        return resourceClass;
    }

    public void setResourceClass(Class<? extends ServerResource> resourceClass) {
        this.resourceClass = resourceClass;
    }
}
