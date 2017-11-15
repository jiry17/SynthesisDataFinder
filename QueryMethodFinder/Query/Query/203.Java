// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
package com.yahoo.config.subscription;

import java.io.File;

import com.yahoo.config.subscription.impl.JRTConfigRequester;

/**
 * A Config URI is a class that can be used to encapsulate a config source and a config id into one
 * object to simplify parameter passing.
 *
 * @author lulf
 * @since 5.1
 */
public class ConfigURI {
    private String configId;
    private ConfigSource source;

    private ConfigURI(String configId, ConfigSource source) {
        this.configId = configId;
        this.source = source;
    }

    public String getConfigId() {
        return configId;
    }

    public ConfigSource getSource() {
        return source;
    }

    public static ConfigURI createFromId(String configId) {
        return new ConfigURI(getConfigId(configId), getConfigSource(configId));
    }

    private static ConfigSource getConfigSource(String configId) {
        if (configId.startsWith("file:")) {
            return new FileSource(new File(configId.substring(5)));
        } else if (configId.startsWith("dir:")) {
            return new DirSource(new File(configId.substring(4)));
        } else {
            return JRTConfigRequester.defaultSourceSet;
        }
    }

    private static String getConfigId(String configId) {
        if (configId.startsWith("file:") || configId.startsWith("dir:")) {
            return "";
        } else {
            return configId;
        }
    }

    public static ConfigURI createFromIdAndSource(String configId, ConfigSource source) {
        return new ConfigURI(configId, source);
    }
}
