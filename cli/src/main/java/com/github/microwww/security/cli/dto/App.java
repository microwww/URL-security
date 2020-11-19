
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
