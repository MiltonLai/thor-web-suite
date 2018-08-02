package com.rockbb.thor.commons.impl.po;

import java.io.Serializable;

public class ConfigPO implements Serializable {
    private String category;
    private String title;
    private String name;
    private String value;
    private int type;
    private String defaultValue;
    private String notes;

    public void initialize() {
        category                      = "";
        title                         = "";
        name                          = "";
        value                         = "";
        type                          = 0;
        defaultValue                  = "";
        notes                         = "";
    }

    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getValue() {return value;}
    public void setValue(String value) {this.value = value;}
    public int getType() {return type;}
    public void setType(int type) {this.type = type;}
    public String getDefaultValue() {return defaultValue;}
    public void setDefaultValue(String defaultValue) {this.defaultValue = defaultValue;}
    public String getNotes() {return notes;}
    public void setNotes(String notes) {this.notes = notes;}
}