package com.dafy.octopus.rental.common;

/**
 * Created by hedy02 on 2017/4/27.
 */
public class ExportEntry {
    private String key;
    private String value;

    public ExportEntry() {
    }

    public ExportEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
