/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package oculus.aperture.common.rest;

import org.restlet.resource.ServerResource;

/**
 * Base class for Aperture REST resources.
 * Extends Restlet's ServerResource with common functionality.
 */
public class ApertureServerResource extends ServerResource {

    /**
     * Returns the base reference for this resource.
     */
    protected String getBaseRef() {
        return getRequest().getRootRef().toString();
    }

    /**
     * Returns the current request path.
     */
    protected String getRequestPath() {
        return getRequest().getResourceRef().getPath();
    }
}
