package com.earth.message.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class ApiResult<T> implements Serializable {
    public static final int ERROR = -1;
    public static final int OK = 0;
    public static final int WARNING = 1;
    public static final int INFO = 2;
    public static final int TOO_BUSY = 3;

    @JsonProperty("code")
    private Integer code = null;
    @JsonProperty("message")
    private String message = null;
    @JsonProperty("result")
    private T result = null;

    public static <M> ApiResult.ApiResultBuilder<M> ok(M result) {
        return (new ApiResult.ApiResultBuilder(0)).message("OK").result(result);
    }
    public static <M> ApiResultBuilder warning(M result, String warning) {
        return (new ApiResult.ApiResultBuilder(1)).result(result).message(warning);
    }

    public static <M> ApiResult.ApiResultBuilder<M> busy() {
        return new ApiResult.ApiResultBuilder(3);
    }

    public static <M> ApiResult.ApiResultBuilder<M> info(String message) {
        return (new ApiResult.ApiResultBuilder(2)).message(message);
    }

    public ApiResult code(Integer code) {
        this.code = code;
        return this;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ApiResult message(String message) {
        this.message = message;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ApiResult result(T result) {
        this.result = result;
        return this;
    }

    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ApiResult _apiResponse = (ApiResult)o;
            return Objects.equals(this.code, _apiResponse.code) && Objects.equals(this.message, _apiResponse.message) && Objects.equals(this.result, _apiResponse.result);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.code, this.message, this.result});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ApiResult {");
        sb.append("    code: ").append(this.toIndentedString(this.code));
        sb.append("    message: ").append(this.toIndentedString(this.message));
        sb.append("    result: ").append(this.toIndentedString(this.result));
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }

    public ApiResult() {
    }

    public static class ApiResultBuilder<M> {
        private int code;
        private String message = null;
        private M result;

        ApiResultBuilder(int code) {
            this.code = code;
        }

        ApiResultBuilder(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public ApiResult.ApiResultBuilder<M> code(int code) {
            this.code = code;
            return this;
        }

        public ApiResult.ApiResultBuilder<M> message(String message) {
            this.message = message;
            return this;
        }

        public ApiResult.ApiResultBuilder<M> result(M result) {
            this.result = result;
            return this;
        }

        public ApiResult<M> build() {
            ApiResult<M> apiResult = new ApiResult();
            apiResult.setCode(this.code);
            apiResult.setMessage(this.message);
            apiResult.setResult(this.result);
            return apiResult;
        }
    }
}
