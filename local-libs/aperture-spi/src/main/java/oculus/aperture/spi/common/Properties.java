/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package oculus.aperture.spi.common;

/**
 * Interface for property access - key/value pairs of configuration.
 */
public interface Properties {

    /**
     * Gets a string property value.
     */
    String getString(String key, String defaultValue);

    /**
     * Gets a boolean property value.
     */
    Boolean getBoolean(String key, Boolean defaultValue);

    /**
     * Gets an integer property value.
     */
    Integer getInteger(String key, Integer defaultValue);

    /**
     * Gets a long property value.
     */
    Long getLong(String key, Long defaultValue);

    /**
     * Gets a double property value.
     */
    Double getDouble(String key, Double defaultValue);

    /**
     * Gets an iterable of strings.
     */
    Iterable<String> getStrings(String key);

    /**
     * Gets an iterable of property sets.
     */
    Iterable<Properties> getPropertiesSets(String key);

    /**
     * Gets a nested Properties object.
     */
    Properties getPropertiesSet(String key);

    /**
     * Returns true if the property exists.
     */
    boolean hasProperty(String key);
}
