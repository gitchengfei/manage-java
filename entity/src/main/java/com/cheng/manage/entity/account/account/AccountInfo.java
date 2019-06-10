package com.cheng.manage.entity.account.account;

import java.io.Serializable;

/**
 * @Description : 账号信息封装
 * @Author : cheng fei
 * @Date : 2019/3/22 02:33
 */
public class AccountInfo implements Serializable {

    /**
     * token
     */
    private String token;

    /**
     * 账号基本信息
     */
    private AccountBO account;

    /**
     * 账号头像url
     */
    private String headPortraitUrl;

    /**
     * 请求来源
     */
    private String requestSource;

    public String getHeadPortraitUrl() {
        return headPortraitUrl;
    }

    public void setHeadPortraitUrl(String headPortraitUrl) {
        this.headPortraitUrl = headPortraitUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AccountBO getAccount() {
        return account;
    }

    public void setAccount(AccountBO account) {
        this.account = account;
    }

    public String getRequestSource() {
        return requestSource;
    }

    public void setRequestSource(String requestSource) {
        this.requestSource = requestSource;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "token='" + token + '\'' +
                ", account=" + account +
                ", headPortraitUrl='" + headPortraitUrl + '\'' +
                ", requestSource='" + requestSource + '\'' +
                '}';
    }
}
