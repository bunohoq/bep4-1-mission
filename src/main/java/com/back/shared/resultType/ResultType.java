package com.back.shared.resultType;

public interface ResultType {
    String getResultCode();

    String getMsg();

    default <T> T getData() {
        return null;
    }
}
