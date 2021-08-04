package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestRequest extends RequestBase{
    public RequestRequest(){}
    public RequestRequest(String url, String method, List<Header> header, Body body, String description){
        super(method,header,body,description);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

}
