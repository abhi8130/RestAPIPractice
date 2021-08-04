package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestRootRequest extends RequestRootBase {
    public RequestRootRequest(){}

    public RequestRootRequest(String name, RequestRequest request){
        super(name);
        this.request = request;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RequestRequest getRequest() {
        return request;
    }

    public void setRequest(RequestRequest request) {
        this.request = request;
    }

    private String name;
    RequestRequest request;

}
