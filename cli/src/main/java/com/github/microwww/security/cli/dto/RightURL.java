
package com.github.microwww.security.cli.dto;

import java.io.Serializable;

/**
 *
 * @author changshu.li
 */
public class RightURL implements Serializable {

    private int id;
    private String name;
    private int webappId;
    private String webappPath;
    private int type;
    private int sort;
    private String data;
    private boolean catalog;
    private int parentId;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWebappId() {
        return webappId;
    }

    public void setWebappId(Integer webappId) {
        this.webappId = webappId;
    }

    public String getWebappPath() {
        return webappPath;
    }

    public void setWebappPath(String webappPath) {
        this.webappPath = webappPath;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isCatalog() {
        return catalog;
    }

    public void setCatalog(boolean catalog) {
        this.catalog = catalog;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
