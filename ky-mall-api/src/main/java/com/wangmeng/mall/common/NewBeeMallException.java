package com.wangmeng.mall.common;

public class NewBeeMallException extends RuntimeException {

    public NewBeeMallException() {
    }

    public NewBeeMallException(String message) {
        super(message);
    }

    /**
     * 丢出一个异常
     *
     * @param message
     */
    public static void fail(String message) {
        throw new com.wangmeng.mall.common.NewBeeMallException(message);
    }

}
