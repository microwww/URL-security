package com.github.microwww.security.cli;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class AjaxMessage<T> {

    private List<String> errors;
    private List<String> warns;

    private String message;
    private T data;
    private transient Object object;

    public boolean isSuccess() {
        return errors == null || errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getWarns() {
        return warns;
    }

    public void setWarns(List<String> warns) {
        this.warns = warns;
    }

    public AjaxMessage addWarn(String warn) {
        if (this.warns == null) {
            this.warns = new ArrayList<>();
        }
        this.warns.add(warn);
        return this;
    }

    public AjaxMessage addError(String error) {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(error);
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AjaxMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public AjaxMessage setData(T data) {
        this.data = data;
        return this;
    }

    @JsonIgnore
    public Object getObject() {
        return object;
    }

    public AjaxMessage setObject(Object object) {
        this.object = object;
        return this;
    }

}
