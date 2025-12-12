/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package oculus.aperture.cms;

import oculus.aperture.spi.store.ConflictException;
import oculus.aperture.spi.store.ContentService;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of ContentService for local development.
 */
public class InMemoryContentService implements ContentService {

    private Map<String, StoredDocument> documents = new ConcurrentHashMap<String, StoredDocument>();

    @Override
    public Document createDocument() {
        return new SimpleDocument();
    }

    @Override
    public DocumentDescriptor storeDocument(Document doc, String store, String id, String revision) throws ConflictException {
        String docId = id != null ? id : UUID.randomUUID().toString();
        String docRev = UUID.randomUUID().toString().substring(0, 8);
        String key = store + ":" + docId;

        if (revision != null && documents.containsKey(key)) {
            StoredDocument existing = documents.get(key);
            if (!revision.equals(existing.revision)) {
                throw new ConflictException("Revision mismatch");
            }
        }

        StoredDocument stored = new StoredDocument();
        stored.contentType = doc.getContentType();
        stored.data = doc.getDocument();
        stored.id = docId;
        stored.revision = docRev;
        stored.store = store;
        documents.put(key, stored);

        return new SimpleDocumentDescriptor(docId, docRev, store);
    }

    @Override
    public Document getDocument(DocumentDescriptor descriptor) {
        return getDocument(descriptor.getStore(), descriptor.getId());
    }

    @Override
    public Document getDocument(String store, String id) {
        String key = store + ":" + id;
        StoredDocument stored = documents.get(key);
        if (stored == null) return null;

        SimpleDocument doc = new SimpleDocument();
        doc.setContentType(stored.contentType);
        doc.setDocument(stored.data);
        return doc;
    }

    @Override
    public boolean deleteDocument(DocumentDescriptor descriptor) {
        String key = descriptor.getStore() + ":" + descriptor.getId();
        return documents.remove(key) != null;
    }

    private static class StoredDocument {
        String id;
        String revision;
        String store;
        String contentType;
        byte[] data;
    }

    private static class SimpleDocument implements Document {
        private String contentType;
        private byte[] data;

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        @Override
        public byte[] getDocument() {
            return data;
        }

        @Override
        public void setDocument(byte[] data) {
            this.data = data;
        }
    }

    private static class SimpleDocumentDescriptor implements DocumentDescriptor {
        private String id;
        private String revision;
        private String store;

        public SimpleDocumentDescriptor(String id, String revision, String store) {
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
    }
}
