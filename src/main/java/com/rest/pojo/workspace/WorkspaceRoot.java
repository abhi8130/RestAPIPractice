package com.rest.pojo.workspace;

public class WorkspaceRoot {

    //default constructor : Jackson needs it for deserialization
    public WorkspaceRoot(){ }

    public WorkspaceRoot(Workspace workspace){
        this.workspace = workspace;
    }
    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    Workspace workspace;
}
