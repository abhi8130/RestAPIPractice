package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class RequestRootResponse extends RequestRootBase{
    public RequestRootResponse(){}

    public RequestRootResponse(String name, RequestResponse request){
        super(name);
        this.request = request;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RequestResponse getRequest() {
        return request;
    }

    public void setRequest(RequestResponse request) {
        this.request = request;
    }

    private String name;
    RequestResponse request;

}
