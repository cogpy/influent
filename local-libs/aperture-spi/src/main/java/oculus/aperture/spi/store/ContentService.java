/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package oculus.aperture.spi.store;

/**
 * Service interface for storing and retrieving document content.
 */
public interface ContentService {

    /**
     * Descriptor for a stored document.
     */
    interface DocumentDescriptor {
        String getId();
        String getRevision();
        String getStore();
    }

    /**
     * Interface for a document to be stored.
     */
    interface Document {
        String getContentType();
        void setContentType(String contentType);

        byte[] getDocument();
        void setDocument(byte[] data);
    }

    /**
     * Creates a new empty document.
     */
    Document createDocument();

    /**
     * Stores a document and returns its descriptor.
     */
    DocumentDescriptor storeDocument(Document doc, String store, String id, String revision) throws ConflictException;

    /**
     * Retrieves a document by its descriptor.
     */
    Document getDocument(DocumentDescriptor descriptor);

    /**
     * Retrieves a document by store and id.
     */
    Document getDocument(String store, String id);

    /**
     * Deletes a document.
     */
    boolean deleteDocument(DocumentDescriptor descriptor);
}
