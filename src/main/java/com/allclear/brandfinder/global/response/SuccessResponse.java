package com.allclear.brandfinder.global.response;


import lombok.Getter;

@Getter
public class SuccessResponse<T>{

    public boolean success;
    public String message;
    public T data;

    public SuccessResponse() {

        this.success = true;
    }

    public SuccessResponse(String message, T data) {
        this.success = true;
        this.message = message;
        this.data = data;
    }

    public static <T> SuccessResponse<T> success(T data, String message) {
        return new SuccessResponse<T>(message, data);
    }

    public static <T> SuccessResponse<T> withData(T data) {
        return success(data, "Success");
    }

    public static <T> SuccessResponse<T> withNoData(String message) {
        return success(null, message);
    }

}
