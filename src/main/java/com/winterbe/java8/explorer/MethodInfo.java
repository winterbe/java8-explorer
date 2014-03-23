package com.winterbe.java8.explorer;

/**
 * @author Benjamin Winterberg
 */
public class MethodInfo {
    private String name;
    private String declaration;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}