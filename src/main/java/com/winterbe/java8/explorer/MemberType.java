package com.winterbe.java8.explorer;

/**
 * @author Benjamin Winterberg
 */
public enum MemberType {
    METHOD ("success"),
    CONSTRUCTOR ("info"),
    FIELD ("default"),
    UNKNOWN ("default");

    private String color;

    MemberType(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}