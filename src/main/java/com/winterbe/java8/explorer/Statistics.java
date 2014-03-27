package com.winterbe.java8.explorer;

/**
 * @author Benjamin Winterberg
 */
public class Statistics {
    int maxFiles;
    int newFiles;
    int newClasses;
    int newInterfaces;
    int newEnums;
    int maxMembers;
    int newMembers;
    int newMethods;
    int newFields;
    int newConstructors;
    int newDefaulInterfacetMethods;
    int newStaticInterfaceMethods;
    int maxFunctionalInterfaces;
    int failures;

    @Override
    public String toString() {
        return "Statistics{" +
                "maxFiles=" + maxFiles +
                ", newFiles=" + newFiles +
                ", newClasses=" + newClasses +
                ", newInterfaces=" + newInterfaces +
                ", newEnums=" + newEnums +
                ", maxMembers=" + maxMembers +
                ", newMembers=" + newMembers +
                ", newMethods=" + newMethods +
                ", newFields=" + newFields +
                ", newConstructors=" + newConstructors +
                ", newDefaulInterfacetMethods=" + newDefaulInterfacetMethods +
                ", newStaticInterfaceMethods=" + newStaticInterfaceMethods +
                ", maxFunctionalInterfaces=" + maxFunctionalInterfaces +
                ", failures=" + failures +
                '}';
    }
}