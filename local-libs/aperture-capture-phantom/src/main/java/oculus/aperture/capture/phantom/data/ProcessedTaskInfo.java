/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package oculus.aperture.capture.phantom.data;

import oculus.aperture.spi.store.ContentService.DocumentDescriptor;

/**
 * Information about a processed capture task.
 */
public class ProcessedTaskInfo implements DocumentDescriptor {

    /**
     * Represents no result / empty task info.
     */
    public static final ProcessedTaskInfo NONE = new ProcessedTaskInfo(null, null, null);

    private String id;
    private String revision;
    private String store;

    public ProcessedTaskInfo(String id, String revision, String store) {
        this.id = id;
        this.revision = revision;
        this.store = store;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getRevision() {
        return revision;
    }

    @Override
    public String getStore() {
        return store;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public void setStore(String store) {
        this.store = store;
    }
}
