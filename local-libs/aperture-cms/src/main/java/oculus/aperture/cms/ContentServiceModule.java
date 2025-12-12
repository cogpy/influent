/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package oculus.aperture.cms;

import com.google.inject.AbstractModule;
import oculus.aperture.spi.store.ContentService;

/**
 * Guice module to bind ContentService.
 */
public class ContentServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ContentService.class).to(InMemoryContentService.class);
    }
}
