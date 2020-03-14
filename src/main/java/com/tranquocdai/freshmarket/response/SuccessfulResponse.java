package com.tranquocdai.freshmarket.response;

public class SuccessfulResponse {
    private String status;
    private Object data;

    public SuccessfulResponse() {
    }

    public SuccessfulResponse(Object data) {
        this.status = "success";
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
