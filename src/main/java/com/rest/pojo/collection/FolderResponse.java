package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FolderResponse extends FolderBase {
    public FolderResponse(){}
    public FolderResponse(String name, List<RequestRootResponse> item){
        super(name);
        this.item = item;
    }

    public List<RequestRootResponse> getItem() {
        return item;
    }

    public void setItem(List<RequestRootResponse> item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private List<RequestRootResponse> item;
    private String name;
}
