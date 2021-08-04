package com.rest.pojo.workspace;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hamcrest.beans.HasProperty;

import java.util.HashMap;

// jackson annotations at class level
// @JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = "id,i",allowSetters = true)
public class Workspace {

    //default constructor : Jackson needs it for deserialization
    public Workspace(){}

    public Workspace(String name,String type, String description){
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public HashMap<String, String> getMyHashMap() {
        return myHashMap;
    }

    public void setMyHashMap(HashMap<String, String> myHashMap) {
        this.myHashMap = myHashMap;
    }

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String i;
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    //@JsonIgnore
    private String id;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private HashMap<String,String> myHashMap;
    private String name;
    private String type;
    private String description;

}
