package com.winguo.testbugly.net;

import rx.functions.Func1;

/**
 * Created by admin on 2017/8/15.
 */

public class HttpResultFunc<T> implements Func1<HttpResult<T>,T> {
    @Override
    public T call(HttpResult<T> tHttpResult) {
        if (tHttpResult.getResultCode() != 0) {
            throw new ApiException(tHttpResult.getResultCode());
        }
        return tHttpResult.getData();
    }
}
