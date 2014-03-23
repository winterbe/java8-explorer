package com.winterbe.java8.explorer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Benjamin Winterberg
 */
public class TypeInfo {
    private int id;
    private String name;
    private String fullType;
    private String packageName;
    private String description;
    private boolean newType;
    private List<MethodInfo> methods = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MethodInfo> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodInfo> methods) {
        this.methods = methods;
    }

    public boolean isNewType() {
        return newType;
    }

    public void setNewType(boolean newType) {
        this.newType = newType;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullType() {
        return fullType;
    }

    public void setFullType(String fullType) {
        this.fullType = fullType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}