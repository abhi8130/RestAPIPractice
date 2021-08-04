package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FolderRequest extends FolderBase{
    public FolderRequest(){}
    public FolderRequest(String name, List<RequestRootRequest> item){
        super(name);
        this.item = item;
    }

    public List<RequestRootRequest> getItem() {
        return item;
    }

    public void setItem(List<RequestRootRequest> item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private List<RequestRootRequest> item;
    private String name;
}
