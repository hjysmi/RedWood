package com.onepiece.redwood.order;

import com.google.gson.annotations.Expose;
import com.onepiece.redwood.order.orderdetail.OrderDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/1.
 */
public class OrderPage {
    @Expose
    private Integer pageNo;
    @Expose
    private Integer pageSize;
    @Expose
    private Object orderBy;
    @Expose
    private Object orderDir;
    @Expose
    private Object orderType;
    @Expose
    private Boolean countTotal;
    @Expose
    private Object sort;
    @Expose
    private Boolean searchDebug;
    @Expose
    private List<OrderDetailBean> result = new ArrayList<OrderDetailBean>();
    @Expose
    private Integer totalItems;
    @Expose
    private Object debugMsg;
    @Expose
    private Integer totalPages;
    @Expose
    private Boolean firstPage;
    @Expose
    private Boolean lastPage;
    @Expose
    private Integer prePage;
    @Expose
    private Integer nextPage;
    @Expose
    private Integer offset;
    @Expose
    private Boolean orderBySetted;

    @Override
    public String toString() {
        return "OrderPage{" +
                "countTotal=" + countTotal +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", orderBy=" + orderBy +
                ", orderDir=" + orderDir +
                ", orderType=" + orderType +
                ", sort=" + sort +
                ", searchDebug=" + searchDebug +
                ", result=" + result +
                ", totalItems=" + totalItems +
                ", debugMsg=" + debugMsg +
                ", totalPages=" + totalPages +
                ", firstPage=" + firstPage +
                ", lastPage=" + lastPage +
                ", prePage=" + prePage +
                ", nextPage=" + nextPage +
                ", offset=" + offset +
                ", orderBySetted=" + orderBySetted +
                '}';
    }

    public Boolean getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(Boolean countTotal) {
        this.countTotal = countTotal;
    }

    public Object getDebugMsg() {
        return debugMsg;
    }

    public void setDebugMsg(Object debugMsg) {
        this.debugMsg = debugMsg;
    }

    public Boolean getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(Boolean firstPage) {
        this.firstPage = firstPage;
    }

    public Boolean getLastPage() {
        return lastPage;
    }

    public void setLastPage(Boolean lastPage) {
        this.lastPage = lastPage;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Object getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Object orderBy) {
        this.orderBy = orderBy;
    }

    public Boolean getOrderBySetted() {
        return orderBySetted;
    }

    public void setOrderBySetted(Boolean orderBySetted) {
        this.orderBySetted = orderBySetted;
    }

    public Object getOrderDir() {
        return orderDir;
    }

    public void setOrderDir(Object orderDir) {
        this.orderDir = orderDir;
    }

    public Object getOrderType() {
        return orderType;
    }

    public void setOrderType(Object orderType) {
        this.orderType = orderType;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPrePage() {
        return prePage;
    }

    public void setPrePage(Integer prePage) {
        this.prePage = prePage;
    }

    public List<OrderDetailBean> getResult() {
        return result;
    }

    public void setResult(List<OrderDetailBean> result) {
        this.result = result;
    }

    public Boolean getSearchDebug() {
        return searchDebug;
    }

    public void setSearchDebug(Boolean searchDebug) {
        this.searchDebug = searchDebug;
    }

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
