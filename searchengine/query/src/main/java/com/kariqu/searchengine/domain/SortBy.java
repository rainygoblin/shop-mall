package com.kariqu.searchengine.domain;

/**
 * 排序类型
 *
 * @Author: Tiger
 * @Since: 11-6-26 下午4:40
 * @Version 1.0.0
 * @Copyright (c) 2011 by Kariqu.com
 */
public enum SortBy {

    /** 价格 */
    price,

    /** 销售量 */
    sell,

    /** 上架时间 */
    time,

    /** 评价 */
    valuation,

    /** 相关性 */
    score;

    public String toFiled() {
        return toString();
    }

}
