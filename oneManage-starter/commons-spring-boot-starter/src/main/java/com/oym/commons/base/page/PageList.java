//package com.oym.commons.base.page;
//
//import com.github.pagehelper.ISelect;
//import com.github.pagehelper.PageHelper;
//
//import java.io.Closeable;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Function;
//
//import static java.util.stream.Collectors.toList;
//
//public class PageList<E> extends ArrayList<E> implements IList, Closeable {
//
//    private static final long serialVersionUID = 1180188951472423544L;
//
//    /**
//     * 页码，从1开始
//     */
//    private int nowPageIndex = 0;
//    /**
//     * 页面大小
//     */
//    private int pageSize = 30;
//    /**
//     * 起始行
//     */
//    private int startRow;
//    /**
//     * 末行
//     */
//    private int endRow;
//    /**
//     * 总数
//     */
//    private long total;
//    /**
//     * 总页数
//     */
//    private int pages;
//    /**
//     * 包含count查询
//     */
//    private boolean count = true;
//    /**
//     * 分页合理化
//     */
//    private Boolean reasonable;
//    /**
//     * 当设置为true的时候，如果pagesize设置为0（或RowBounds的limit=0），就不执行分页，返回全部结果
//     */
//    private Boolean pageSizeZero;
//    /**
//     * 进行count查询的列名
//     */
//    private String countColumn;
//    /**
//     * 排序
//     */
//    private String orderBy;
//    /**
//     * 只增加排序
//     */
//    private boolean orderByOnly;
//
//    /**
//     * count通过redis获取
//     */
//    private boolean countByRedis = false;
//
//    /**
//     * cachKey default 5's
//     */
//    private int expireTime = 5;
//
//    public PageList() {
//        super();
//    }
//
//    public PageList(int pageNum, int pageSize) {
//        this(pageNum, pageSize, true, null);
//    }
//
//    public PageList(int pageNum, int pageSize, boolean count) {
//        this(pageNum, pageSize, count, null);
//    }
//
//    private PageList(int nowPageIndex, int pageSize, boolean count, Boolean reasonable) {
//        super(0);
//        if (nowPageIndex == 1 && pageSize == Integer.MAX_VALUE) {
//            pageSizeZero = true;
//            pageSize = 0;
//        }
//        this.nowPageIndex = nowPageIndex;
//        this.pageSize = pageSize;
//        this.count = count;
//        calculateStartAndEndRow();
//        setReasonable(reasonable);
//    }
//
//    /**
//     * int[] rowBounds
//     * 0 : offset
//     * 1 : limit
//     */
//    public PageList(int[] rowBounds, boolean count) {
//        super(0);
//        if (rowBounds[0] == 0 && rowBounds[1] == Integer.MAX_VALUE) {
//            pageSizeZero = true;
//            this.pageSize = 0;
//        } else {
//            this.pageSize = rowBounds[1];
//            this.nowPageIndex = rowBounds[1] != 0 ? (int) (Math.ceil(((double) rowBounds[0] + rowBounds[1]) / rowBounds[1])) : 0;
//        }
//        this.startRow = rowBounds[0];
//        this.count = count;
//        this.endRow = this.startRow + rowBounds[1];
//    }
//
//    public List<E> getResult() {
//        return this;
//    }
//
//    public int getPages() {
//        return pages;
//    }
//
//    public PageList<E> setPages(int pages) {
//        this.pages = pages;
//        return this;
//    }
//
//    public int getEndRow() {
//        return endRow;
//    }
//
//    public PageList<E> setEndRow(int endRow) {
//        this.endRow = endRow;
//        return this;
//    }
//
//    public int getNowPageIndex() {
//        return nowPageIndex;
//    }
//
//    public PageList<E> setNowPageIndex(int nowPageIndex) {
//        //分页合理化，针对不合理的页码自动处理
//        this.nowPageIndex = ((reasonable != null && reasonable) && nowPageIndex <= 0) ? 1 : nowPageIndex;
//        return this;
//    }
//
//    public int getPageSize() {
//        return pageSize;
//    }
//
//    public PageList<E> setPageSize(int pageSize) {
//        this.pageSize = pageSize;
//        return this;
//    }
//
//    public int getStartRow() {
//        return startRow;
//    }
//
//    public PageList<E> setStartRow(int startRow) {
//        this.startRow = startRow;
//        return this;
//    }
//
//    public long getTotal() {
//        return total;
//    }
//
//    public void setTotal(long total) {
//        this.total = total;
//        if (total == -1) {
//            pages = 1;
//            return;
//        }
//        if (pageSize > 0) {
//            pages = (int) (total / pageSize + ((total % pageSize == 0) ? 0 : 1));
//        } else {
//            pages = 0;
//        }
//        //分页合理化，针对不合理的页码自动处理
//        if ((reasonable != null && reasonable) && nowPageIndex > pages) {
//            if(pages!=0){
//                nowPageIndex = pages;
//            }
//            calculateStartAndEndRow();
//        }
//    }
//
//    public Boolean getReasonable() {
//        return reasonable;
//    }
//
//    public PageList<E> setReasonable(Boolean reasonable) {
//        if (reasonable == null) {
//            return this;
//        }
//        this.reasonable = reasonable;
//        //分页合理化，针对不合理的页码自动处理
//        if (this.reasonable && this.nowPageIndex <= 0) {
//            this.nowPageIndex = 1;
//            calculateStartAndEndRow();
//        }
//        return this;
//    }
//
//    public Boolean getPageSizeZero() {
//        return pageSizeZero;
//    }
//
//    public PageList<E> setPageSizeZero(Boolean pageSizeZero) {
//        if (pageSizeZero != null) {
//            this.pageSizeZero = pageSizeZero;
//        }
//        return this;
//    }
//    public String getOrderBy() {
//        return orderBy;
//    }
//
//    public <E> PageList<E> setOrderBy(String orderBy) {
//        this.orderBy = orderBy;
//        return (PageList<E>) this;
//    }
//
//    public boolean isOrderByOnly() {
//        return orderByOnly;
//    }
//
//    public void setOrderByOnly(boolean orderByOnly) {
//        this.orderByOnly = orderByOnly;
//    }
//
//    /**
//     * 计算起止行号
//     */
//    private void calculateStartAndEndRow() {
//        this.startRow = this.nowPageIndex > 0 ? (this.nowPageIndex - 1) * this.pageSize : 0;
//        this.endRow = this.startRow + this.pageSize * (this.nowPageIndex > 0 ? 1 : 0);
//    }
//
//    public boolean isCount() {
//        return this.count;
//    }
//
//    public PageList<E> setCount(boolean count) {
//        this.count = count;
//        return this;
//    }
//
//    /**
//     * 设置页码
//     *
//     * @param nowPageIndex
//     * @return
//     */
//    public PageList<E> pageNum(int nowPageIndex) {
//        //分页合理化，针对不合理的页码自动处理
//        this.nowPageIndex = ((reasonable != null && reasonable) && nowPageIndex <= 0) ? 1 : nowPageIndex;
//        return this;
//    }
//
//    /**
//     * 设置页面大小
//     *
//     * @param pageSize
//     * @return
//     */
//    public PageList<E> pageSize(int pageSize) {
//        this.pageSize = pageSize;
//        calculateStartAndEndRow();
//        return this;
//    }
//
//    /**
//     * 是否执行count查询
//     *
//     * @param count
//     * @return
//     */
//    public PageList<E> count(Boolean count) {
//        this.count = count;
//        return this;
//    }
//
//    /**
//     * 设置合理化
//     *
//     * @param reasonable
//     * @return
//     */
//    public PageList<E> reasonable(Boolean reasonable) {
//        setReasonable(reasonable);
//        return this;
//    }
//
//    /**
//     * 当设置为true的时候，如果pagesize设置为0（或RowBounds的limit=0），就不执行分页，返回全部结果
//     *
//     * @param pageSizeZero
//     * @return
//     */
//    public PageList<E> pageSizeZero(Boolean pageSizeZero) {
//        setPageSizeZero(pageSizeZero);
//        return this;
//    }
//
//    /**
//     * 指定 count 查询列
//     *
//     * @param columnName
//     * @return
//     */
//    public PageList<E> countColumn(String columnName) {
//        this.countColumn = columnName;
//        return this;
//    }
//
//    public Page<E> toPage() {
//        Page<E> page = new Page<E>(this);
//        return page;
//    }
//
//    public long doCount(ISelect select) {
//        this.pageSizeZero = true;
//        this.pageSize = 0;
//        select.doSelect();
//        return this.total;
//    }
//
//    public boolean isCountByRedis() {
//        return countByRedis;
//    }
//
//    public PageList<E> setCountByRedis(boolean countByRedis) {
//        this.countByRedis = countByRedis;
//        return this;
//    }
//
//    public int getExpireTime() {
//        return expireTime;
//    }
//
//    public PageList<E> setExpireTime(int expireTime) {
//        this.expireTime = expireTime;
//        return this;
//    }
//
//    public String getCountColumn() {
//        return countColumn;
//    }
//
//    public void setCountColumn(String countColumn) {
//        this.countColumn = countColumn;
//    }
//
//    @Override
//    public String toString() {
//        return "Page{" +
//                "count=" + count +
//                ", nowPageIndex=" + nowPageIndex +
//                ", pageSize=" + pageSize +
//                ", startRow=" + startRow +
//                ", endRow=" + endRow +
//                ", total=" + total +
//                ", pages=" + pages +
//                ", reasonable=" + reasonable +
//                ", pageSizeZero=" + pageSizeZero +
//                '}' + super.toString();
//    }
//
//    @Override
//    public void close() {
//        PageHelper.clearPage();
//    }
//
//    public <R> Page<R> convert(Function<? super E, ? extends R> mapper) {
//
//        Page<R> page = Page.of((int)this.getTotal(),
//                this.getPages(), this.getPageSize());
//
//        List<R> collect = this.stream().map(mapper).collect(toList());
//
//        page.setList(collect);
//        return page;
//    }
//}
