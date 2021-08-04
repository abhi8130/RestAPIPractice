package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionRootResponse extends CollectionRootBase {
    public CollectionRootResponse(){}

    public CollectionRootResponse(CollectionResponse collection){
        this.collection = collection;
    }

    public CollectionResponse getCollection() {
        return collection;
    }

    public void setCollection(CollectionResponse collection) {
        this.collection = collection;
    }

    CollectionResponse collection;

}
