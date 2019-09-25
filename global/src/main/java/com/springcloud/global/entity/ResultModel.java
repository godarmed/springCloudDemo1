//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
package com.springcloud.global.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(
        value = "统一返回模型",
        tags = {"统一返回模型"}
)
public class ResultModel<T> {
    @ApiModelProperty("响应信息")
    private String message;
    @ApiModelProperty("响应状态码 0-正常 其他-异常 500X业务异常 403 没有权限 401 token异常")
    private int subCode = 0;
    @ApiModelProperty("响应实体类")
    private T data;
    @ApiModelProperty("分页信息")
    private MsgPageInfo pageInfo;

    public ResultModel() {
        this.message = "";
    }

    public ResultModel(T data) {
        this.data = data;
        this.message = "";
    }

    public ResultModel(String message) {
        this.message = message;
    }

    public ResultModel(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ResultModel(int subCode, String message) {
        this.subCode = subCode;
        this.message = message;
    }

    public ResultModel(T data, int subCode, String message) {
        this.data = data;
        this.subCode = subCode;
        this.message = message;
    }

    public void setData(T data, MsgPageInfo pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }

    public String getMessage() {
        return this.message;
    }

    public int getSubCode() {
        return this.subCode;
    }

    public T getData() {
        return this.data;
    }

    public MsgPageInfo getPageInfo() {
        return this.pageInfo;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSubCode(int subCode) {
        this.subCode = subCode;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setPageInfo(MsgPageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ResultModel)) {
            return false;
        } else {
            ResultModel<?> other = (ResultModel)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                if (this.getSubCode() != other.getSubCode()) {
                    return false;
                } else {
                    Object this$data = this.getData();
                    Object other$data = other.getData();
                    if (this$data == null) {
                        if (other$data != null) {
                            return false;
                        }
                    } else if (!this$data.equals(other$data)) {
                        return false;
                    }

                    Object this$pageInfo = this.getPageInfo();
                    Object other$pageInfo = other.getPageInfo();
                    if (this$pageInfo == null) {
                        if (other$pageInfo != null) {
                            return false;
                        }
                    } else if (!this$pageInfo.equals(other$pageInfo)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ResultModel;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        result = result * 59 + this.getSubCode();
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        Object $pageInfo = this.getPageInfo();
        result = result * 59 + ($pageInfo == null ? 43 : $pageInfo.hashCode());
        return result;
    }

    public String toString() {
        return "ResultModel(message=" + this.getMessage() + ", subCode=" + this.getSubCode() + ", data=" + this.getData() + ", pageInfo=" + this.getPageInfo() + ")";
    }
}
