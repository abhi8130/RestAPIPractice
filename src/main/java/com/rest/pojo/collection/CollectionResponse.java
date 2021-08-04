package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionResponse extends  CollectionBase {

    public CollectionResponse(){}
    public CollectionResponse(Info info, List<FolderResponse> item){
        super(info);
        this.item = item;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<FolderResponse> getItem() {
        return item;
    }

    public void setItem(List<FolderResponse> item) {
        this.item = item;
    }

    Info info;
    List<FolderResponse> item;
}
