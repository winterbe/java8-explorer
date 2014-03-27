package com.winterbe.java8.explorer;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Benjamin Winterberg
 */
public class TypeInfo {
    private String name;
    private String fullType;
    private String packageName;
    private String declaration;
    private String path;
    private boolean newType;
    private FileType fileType;
    private List<MemberInfo> members = new ArrayList<>();

    public boolean isFunctionalInterface() {
        return StringUtils.contains(declaration, "@FunctionalInterface");
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        String id = packageName + name;
        return StringUtils.remove(id, '.');
    }

    public List<MemberInfo> getMembers() {
        return members;
    }

    public void setMembers(List<MemberInfo> members) {
        this.members = members;
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

}