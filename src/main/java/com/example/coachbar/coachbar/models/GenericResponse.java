package com.example.coachbar.coachbar.models;

public class GenericResponse<T> {


    private Long responseCode;
    private String responseMessage;
    private T response;

    public GenericResponse() {
    }

    public GenericResponse(Long responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public GenericResponse(Long responseCode, String responseMessage, T data) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.response = data;
    }

    public Long getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(Long responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return this.responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public T getResponse() {
        return this.response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public String toString() {
        String var10000 = String.valueOf(this.responseCode);
        return "GenericResponse [responseCode=" + var10000 + ", responseMessage=" + this.responseMessage + ", response=" + String.valueOf(this.response) + "]";
    }

}
