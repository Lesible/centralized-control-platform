package com.shenhao.resp;

public class BaseResponse<T> {

    private String sequence;
    private T data;
    private ResponseCode status;
    private String detail;

    public BaseResponse() {
    }

    public boolean isSuccess() {
        return status.isSucceed();
    }

    public boolean isFailed() {
        return !status.isSucceed();
    }

    public String getSequence() {
        return this.sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseCode getStatus() {
        return this.status;
    }

    public void setStatus(ResponseCode status) {
        this.status = status;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[sequence: " + this.sequence + ", code: " + this.status.getCode() + ", detail: " + this.detail + ", data: " + this.data + "]";
    }
}