package com.shenhao.resp;

import java.util.List;

public class ResponseBuilder {
    private String sequence;
    private ResponseCode code;
    private String detail;
    private Long page;
    private Long size;
    private Long totalCount;
    private Long totalPage;

    public ResponseBuilder() {
    }

    public static ResponseBuilder create() {
        ResponseBuilder responseBuilder = new ResponseBuilder();
        return responseBuilder.sequence(String.valueOf(System.currentTimeMillis()));
    }

    public static <T> BaseResponse<T> success() {
        return ResponseBuilder.create().detail(ResponseCode.SUCCEED.getMsg()).build();
    }

    public static <T> BaseResponse<T> success(T data) {
        return ResponseBuilder.create().detail(ResponseCode.SUCCEED.getMsg()).build(data);
    }

    public static <T> BaseResponse<T> failed() {
        return ResponseBuilder
                .create().detail(HttpResponseCode.SYSTEM_ERROR.getMsg()).code(ResponseCode.FAIL)
                .build();
    }

    public static <T> BaseResponse<T> failed(String msg) {
        return ResponseBuilder
                .create().detail(msg).code(ResponseCode.FAIL)
                .build();
    }

    public static <T> BaseResponse<T> judge(boolean status) {
        if (status) {
            return success();
        } else {
            return failed();
        }
    }

    public ResponseBuilder sequence(String sequence) {
        this.sequence = sequence;
        return this;
    }

    public ResponseBuilder code(ResponseCode code) {
        this.code = code;
        return this;
    }

    public ResponseBuilder detail(String detail) {
        this.detail = detail;
        return this;
    }

    public ResponseBuilder page(long page) {
        this.page = page;
        return this;
    }

    public ResponseBuilder size(long size) {
        this.size = size;
        return this;
    }

    public ResponseBuilder totalCount(long totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public ResponseBuilder totalPage(long totalPage) {
        this.totalPage = totalPage;
        return this;
    }

    public <T> BaseResponse<T> build() {
        return this.build(null);
    }

    public <T> BaseResponse<T> build(T data) {
        try {
            BaseResponse<T> response = new BaseResponse<>();
            response.setStatus(ResponseCode.SUCCEED);
            response.setData(data);
            response.setSequence(this.sequence);
            response.setDetail(this.detail);
            if (this.code != null) {
                response.setStatus(this.code);
            }

            return response;
        } catch (Exception var3) {
            throw new RuntimeException("响应构建失败", var3);
        }
    }

    public <T> PageDataResponse<T> buildPageData() {
        return this.buildPageData(null);
    }

    public <T> PageDataResponse<T> buildPageData(List<T> data) {
        if (this.page != null && this.size != null && this.totalCount != null) {
            try {
                PageDataResponse<T> response = new PageDataResponse<>();
                response.setStatus(ResponseCode.SUCCEED);
                response.setData(data);
                response.setSequence(this.sequence);
                response.setDetail(this.detail);
                response.setPage(this.page);
                response.setSize(this.size);
                response.setTotalCount(this.totalCount);
                response.setTotalPage(this.totalPage == null ? (this.size <= 0L ? 0L : (this.totalCount - 1L) / this.size + 1L) : this.totalPage);
                if (this.code != null) {
                    response.setStatus(this.code);
                }

                return response;
            } catch (Exception e) {
                throw new RuntimeException("分页请求响应构建失败", e);
            }
        } else {
            throw new IllegalArgumentException("构建分页请求响应参数缺失");
        }
    }
}