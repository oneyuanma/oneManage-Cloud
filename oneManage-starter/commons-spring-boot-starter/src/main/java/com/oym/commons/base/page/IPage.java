//package com.oym.commons.base.page;
//
//
//import java.io.Serializable;
//import java.util.List;
//import java.util.function.Function;
//
//import static java.util.stream.Collectors.toList;
//
///**
// * @author oneyuanma
// * @date 2021/06/25
// */
//public interface IPage <T> extends Serializable {
//
//    /**
//     * 总记录
//     * @return list
//     */
//    List<T> getList();
//
//    /**
//     *  记录
//     */
//    Page<T> setList(List<T> list);
//
//    /**
//     * 返回所有的记录数
//     */
//    int getTotalItem();
//
//    /**
//     * 返回所有的页码数
//     */
//    int getTotalPage();
//
//
//    int getPageSize();
//
//    void setTotalItem(int totalItem);
//
//    void setTotalPage(int totalPage);
//
//    void setPageSize(int pageSize);
//
//    /**
//     * IPage 的泛型转换,转换至默认的defaultPage;
//     *
//     * @param mapper 转换函数
//     * @param <R>    转换后的泛型
//     * @return 转换泛型后的 IPagination
//     */
//    @SuppressWarnings("unchecked")
//    default <R> IPage<R> convert(Function<? super T, ? extends R> mapper) {
//        List<R> collect = this.getList().stream().map(mapper).collect(toList());
//        return ((Page<R>) this).setList(collect);
//    }
//
//}
