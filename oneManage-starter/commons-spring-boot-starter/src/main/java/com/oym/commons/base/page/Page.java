//package com.oym.commons.base.page;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//
///**
// * @author oneyuanma
// * @date 2021/06/25
// */
//public class Page<T> implements IPage<T> {
//
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 所有记录数
//     */
//    private int totalItem;
//
//    /**
//     * 所有页面数量
//     */
//    private int totalPage;
//
//    /**
//     * 每页默认记录数
//     */
//    @JsonIgnore
//    private int pageSize;
//
//    private List<T> list = Collections.emptyList();
//
//    public Page() {
//    }
//
//    /**
//     * Page 构造
//     *
//     * @param totalItem 总记录数
//     * @param totalPage 总页数
//     * @param pageSize  当前页显示记录数
//     */
//    public Page(int totalItem, int totalPage, int pageSize) {
//        this.totalItem = totalItem;
//        this.totalPage = totalPage;
//        this.pageSize = pageSize;
//    }
//
//    /**
//     * page信息
//     *
//     * @param totalItem 总记录
//     * @param totalPage 总页数
//     * @return page
//     */
//    public static Page of(int totalItem, int totalPage) {
//        return of(totalItem, totalPage, 0);
//    }
//
//    /**
//     * page信息
//     *
//     * @param totalItem 总记录
//     * @param totalPage 总页数
//     * @param pageSize  每页记录数
//     * @return page
//     */
//    public static <T> Page<T> of(int totalItem, int totalPage, int pageSize) {
//        return new Page<>(totalItem, totalPage, pageSize);
//    }
//
//
//    public Page(List<T> list) {
//        this.list = list;
//        if (list instanceof IList) {
//            this.totalItem = (int) ((IList) list).getTotal();
//            this.totalPage = ((IList) list).getPages();
//            this.pageSize = ((IList) list).getPageSize();
//        } else if (list instanceof Collection) {
//            this.totalItem = list.size();
//            this.pageSize = list.size();
//
//            this.totalPage = this.pageSize > 0 ? 1 : 0;
//        }
//
//    }
//
//    @Override
//    public List<T> getList() {
//        return this.list;
//    }
//
//    @Override
//    public Page<T> setList(List<T> list) {
//        this.list = list;
//        return this;
//    }
//
//    @Override
//    public void setTotalItem(int totalItem) {
//        this.totalItem = totalItem;
//    }
//
//    @Override
//    public void setTotalPage(int totalPage) {
//        this.totalPage = totalPage;
//    }
//
//    @Override
//    public void setPageSize(int pageSize) {
//        this.pageSize = pageSize;
//    }
//
//    @Override
//    public int getTotalItem() {
//        return this.totalItem;
//    }
//
//    @Override
//    public int getTotalPage() {
//        return this.totalPage;
//    }
//
//    @Override
//    public int getPageSize() {
//        return pageSize;
//    }
//}
