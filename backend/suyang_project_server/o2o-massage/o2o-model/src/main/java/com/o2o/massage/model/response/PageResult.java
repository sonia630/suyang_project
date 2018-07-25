package com.o2o.massage.model.response;

import lombok.Data;

import java.util.List;

/**
 * @Author: zhongli
 * @Date: 2018/3/14 0:47
 * @Description:
 */
@Data
public class PageResult<T> {

    //当前页
    private int pageNum;
    //每页的数量
    private int pageSize;
    //当前页的数量
    private int size;
    //排序
    private String orderBy;

    //总记录数
    private long total;
    //总页数
    private int pages;
    //结果集
    private List<T> list;

    //前一页
    private int prePage;
    //下一页
    private int nextPage;

    //是否有前一页
    private boolean hasPreviousPage = false;
    //是否有下一页
    private boolean hasNextPage = false;
}
