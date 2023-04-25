package com.shenhao.resp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PageDataResponse<T> {
    private String sequence;

    /**
     * 数据
     */
    private Collection<T> data;

    private ResponseCode status;

    private String detail;
    /**
     * 当前页码
     */
    private Long page;

    /**
     * 每页大小
     */
    private Long size;

    /**
     * 总数量
     */
    private Long totalCount;

    /**
     * 总页数
     */
    private Long totalPage;

    public PageDataResponse() {
    }

    public static <T> List<T> getPageSizeDataForRelations(List<T> dataList, int pageSize, int pageNo) {
        int startNum = (pageNo - 1) * pageSize + 1;
        if (startNum > dataList.size()) {
            return null;
        }
        List<T> res = new ArrayList<>();
        int rum = dataList.size() - startNum;
        if (rum < 0) {
            return null;
        }
        if (rum == 0) {
            int index = dataList.size() - 1;
            res.add(dataList.get(index));
            return res;
        }
        if (rum / pageSize >= 1) {
            for (int i = startNum; i < startNum + pageSize; i++) {
                res.add(dataList.get(i - 1));
            }
            return res;
        } else if (rum / pageSize == 0) {
            for (int j = startNum; j <= dataList.size(); j++) {
                res.add(dataList.get(j - 1));
            }
            return res;
        } else {
            return null;
        }
    }

    public String getSequence() {
        return this.sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Collection<T> data) {
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

    public Long getPage() {
        return this.page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getSize() {
        return this.size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[sequence: " + this.sequence + ", code: " + this.status.getCode() + ", page: " + this.page + ", size: " + this.size + ", totalCount: " + this.totalCount + ", detail: " + this.detail + ", data: " + this.data + "]";
    }
}
