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
     * Gets an object property value.
     */
    Object getObject(String key);

    /**
     * Gets an iterable of objects.
     */
    Iterable<Object> getObjects(String key);

    /**
     * Gets a string property value.
     */
    String getString(String key, String defaultValue);

    /**
     * Gets an iterable of strings.
     */
    Iterable<String> getStrings(String key);

    /**
     * Gets a boolean property value.
     */
    Boolean getBoolean(String key, Boolean defaultValue);

    /**
     * Gets an iterable of booleans.
     */
    Iterable<Boolean> getBooleans(String key);

    /**
     * Gets an integer property value.
     */
    Integer getInteger(String key, Integer defaultValue);

    /**
     * Gets an iterable of integers.
     */
    Iterable<Integer> getIntegers(String key);

    /**
     * Gets a long property value.
     */
    Long getLong(String key, Long defaultValue);

    /**
     * Gets an iterable of longs.
     */
    Iterable<Long> getLongs(String key);

    /**
     * Gets a float property value.
     */
    Float getFloat(String key, Float defaultValue);

    /**
     * Gets an iterable of floats.
     */
    Iterable<Float> getFloats(String key);

    /**
     * Gets a double property value.
     */
    Double getDouble(String key, Double defaultValue);

    /**
     * Gets an iterable of doubles.
     */
    Iterable<Double> getDoubles(String key);

    /**
     * Gets an iterable of property sets.
     */
    Iterable<Properties> getPropertiesSets(String key);

    /**
     * Gets a nested Properties object.
     */
    Properties getPropertiesSet(String key);

    /**
     * Gets a nested Properties object with a default value.
     */
    Properties getPropertiesSet(String key, Properties defaultValue);

    /**
     * Returns true if the property exists.
     */
    boolean hasProperty(String key);
}
