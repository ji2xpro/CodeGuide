package com.xxx.yyy.spring.retry;

import com.xxx.yyy.spring.utils.LogUtil;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * @Auther: maoyan
 * @Date: 2019/6/23 16:56
 * @Description:
 */
public class Retry implements IRetryAnalyzer {
    private static LogUtil logger = LogUtil.getLogger(Retry.class);
    private int retryCount = 1;
    /**
     * 失败测试重跑2次
     */
    private static int maxRetryCount;

    static {
        maxRetryCount = 2;
    }

    /**
     * Returns true if the test method has to be retried, false otherwise.
     *
     * @param result The result of the test method that just ran.
     * @return true if the test method has to be retried, false otherwise.
     */
    @Override
    public boolean retry(ITestResult result) {
        if (retryCount <= maxRetryCount) {
            String message = "Running retry for '" + result.getName() + "' on class " + this.getClass().getName() + " Retrying " + retryCount + " times";
            logger.info(message);
            retryCount++;
            return true;
        }
        return false;
    }

    // 用于重置retryCnt
    public void reset() {
        retryCount = 0;
    }
}
