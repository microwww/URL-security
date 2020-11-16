
package com.github.microwww.security.cli.dto;

import java.io.Serializable;

/**
 *
 * @author changshu.li
 */
public class Department implements Serializable {

    /**
     * 为了兼容， ID 为字符串
     */
    private String id;
    private String name;
    private String pid;//上级部门

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
