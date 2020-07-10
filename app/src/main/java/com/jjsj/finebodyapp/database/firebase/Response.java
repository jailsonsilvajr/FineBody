package com.jjsj.finebodyapp.database.firebase;

public class Response {

    private int status;
    private String message;
    private Object object;

    public Response(int status, String message, Object object){

        this.status = status;
        this.message = message;
        this.object = object;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getObject() {
        return object;
    }
}
