/*
 * Local implementation for Influent project
 * Apache License 2.0
 */
package oculus.aperture.common;

import oculus.aperture.spi.common.Properties;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Properties implementation backed by a JSON object.
 */
public class JSONProperties implements Properties {

    private JSONObject json;

    public JSONProperties() {
        this.json = new JSONObject();
    }

    public JSONProperties(String jsonString) throws JSONException {
        this.json = new JSONObject(jsonString);
    }

    public JSONProperties(JSONObject json) {
        this.json = json;
    }

    @Override
    public String getString(String key, String defaultValue) {
        try {
            if (json.has(key)) {
                return json.getString(key);
            }
        } catch (JSONException e) {
            // ignore
        }
        return defaultValue;
    }

    @Override
    public Boolean getBoolean(String key, Boolean defaultValue) {
        try {
            if (json.has(key)) {
                return json.getBoolean(key);
            }
        } catch (JSONException e) {
            // ignore
        }
        return defaultValue;
    }

    @Override
    public Integer getInteger(String key, Integer defaultValue) {
        try {
            if (json.has(key)) {
                return json.getInt(key);
            }
        } catch (JSONException e) {
            // ignore
        }
        return defaultValue;
    }

    @Override
    public Long getLong(String key, Long defaultValue) {
        try {
            if (json.has(key)) {
                return json.getLong(key);
            }
        } catch (JSONException e) {
            // ignore
        }
        return defaultValue;
    }

    @Override
    public Double getDouble(String key, Double defaultValue) {
        try {
            if (json.has(key)) {
                return json.getDouble(key);
            }
        } catch (JSONException e) {
            // ignore
        }
        return defaultValue;
    }

    @Override
    public Iterable<String> getStrings(String key) {
        try {
            if (json.has(key)) {
                JSONArray arr = json.getJSONArray(key);
                List<String> result = new ArrayList<String>();
                for (int i = 0; i < arr.length(); i++) {
                    result.add(arr.getString(i));
                }
                return result;
            }
        } catch (JSONException e) {
            // ignore
        }
        return Collections.emptyList();
    }

    @Override
    public Iterable<Properties> getPropertiesSets(String key) {
        try {
            if (json.has(key)) {
                JSONArray arr = json.getJSONArray(key);
                List<Properties> result = new ArrayList<Properties>();
                for (int i = 0; i < arr.length(); i++) {
                    result.add(new JSONProperties(arr.getJSONObject(i)));
                }
                return result;
            }
        } catch (JSONException e) {
            // ignore
        }
        return Collections.emptyList();
    }

    @Override
    public Properties getPropertiesSet(String key) {
        try {
            if (json.has(key)) {
                return new JSONProperties(json.getJSONObject(key));
            }
        } catch (JSONException e) {
            // ignore
        }
        return null;
    }

    @Override
    public boolean hasProperty(String key) {
        return json.has(key);
    }

    public JSONObject getJSONObject() {
        return json;
    }
}
