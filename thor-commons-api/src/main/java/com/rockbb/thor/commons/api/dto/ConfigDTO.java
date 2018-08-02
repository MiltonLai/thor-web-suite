package com.rockbb.thor.commons.api.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ConfigDTO implements Serializable
{
    public static final int TYPE_STRING		= 0;
    public static final int TYPE_INT		= 1;
    public static final int TYPE_DATE		= 2;
    public static final int TYPE_LIST		= 3;
    public static final int TYPE_MAP		= 4;
    public static final int TYPE_LIST_OBJ   = 5;
    public static final int TYPE_MAP_OBJ    = 6;

    private String category;
    private String title;
    private String name;
    private String value;
    private int type;
    private String defaultValue;
    private String notes;

    private Integer integer;
    private Date date;
    private List<String> list;
    private Map<String, String> map;
    private List<Object> listObj;
    private Map<String, Object> mapObj;

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

    public Integer getInteger() { return integer; }
    public void setInteger(Integer integer) { this.integer = integer; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public List<String> getList() { return list; }
    public void setList(List<String> list) { this.list = list; }
    public Map<String, String> getMap() { return map; }
    public void setMap(Map<String, String> map) { this.map = map; }
    public List<Object> getListObj() { return listObj; }
    public void setListObj(List<Object> listObj) { this.listObj = listObj; }
    public Map<String, Object> getMapObj() { return mapObj; }
    public void setMapObj(Map<String, Object> mapObj) { this.mapObj = mapObj; }
}