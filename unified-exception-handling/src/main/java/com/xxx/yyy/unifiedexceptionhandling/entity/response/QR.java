package com.xxx.yyy.unifiedexceptionhandling.entity.response;

import lombok.ToString;

/**
 * 响应信息主体，同{@link QueryDataResponse}
 *
 * @author: maoyan
 * @date: 2020/6/10 14:41:59
 * @description:
 * @see QueryDataResponse
 */
@ToString
public class QR<T> extends QueryDataResponse<T> {

    public QR() {
        super();
    }

    public QR(QueryData<T> data) {
        super(data);
    }
}
