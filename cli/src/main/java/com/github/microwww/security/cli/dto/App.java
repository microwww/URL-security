
package com.github.microwww.security.cli.dto;

import java.io.Serializable;

/**
 * 与 server 中的 webapp 数据等价
 *
 * @author changshu.li
 */
public class App implements Serializable {

    private int id;
    private String appName;
    private String name;
    /**
     * true: white list / false: blank list
     */
    private boolean whiteList;
    private int loginGroup;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isWhiteList() {
        return whiteList;
    }

    public void setWhiteList(boolean whiteList) {
        this.whiteList = whiteList;
    }

    public int getLoginGroup() {
        return loginGroup;
    }

    public void setLoginGroup(int loginGroup) {
        this.loginGroup = loginGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
