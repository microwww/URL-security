
package com.github.microwww.security.cli.dto;

import java.io.Serializable;

/**
 *
 * @author changshu.li
 */
public class Authority implements Serializable {

    private int id;
    private String name;
    private int webappId;
    private String uri;
    private int type;
    private int sort;
    private int parentId;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWebappId() {
        return webappId;
    }

    public void setWebappId(int webappId) {
        this.webappId = webappId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
