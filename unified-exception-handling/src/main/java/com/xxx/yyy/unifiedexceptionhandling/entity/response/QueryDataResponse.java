package com.xxx.yyy.unifiedexceptionhandling.entity.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>查询数据集返回结果</p>
 *
 * @author: maoyan
 * @date: 2020/6/10 11:18:41
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryDataResponse<T> extends CommonResponse<QueryData<T>> {
    public QueryDataResponse() {
    }

    public QueryDataResponse(QueryData<T> data) {
        super(data);
    }
}
