package com.winterbe.java8.explorer;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Benjamin Winterberg
 */
public class MemberInfo {
    private String name;
    private String declaration;
    private MemberType type;

    public boolean isStatic() {
        return StringUtils.contains(declaration, "static&nbsp;") ||
                StringUtils.contains(declaration, "static ");
    }

    public boolean isDefault() {
        return StringUtils.contains(declaration, "default&nbsp;") ||
                StringUtils.contains(declaration, "default ");
    }

    public MemberType getType() {
        return type;
    }

    public void setType(MemberType type) {
        this.type = type;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}