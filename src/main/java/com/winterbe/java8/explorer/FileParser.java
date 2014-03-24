package com.winterbe.java8.explorer;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

/**
 * @author Benjamin Winterberg
 */
public class FileParser {

    public Optional<TypeInfo> parse(File file, String path, int id) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("file does not exist: " + file.getAbsolutePath());
        }

        Document document = Jsoup.parse(file, "UTF-8", "http://download.java.net/jdk8/docs/api/");

        try {
            return getTypeInfo(document, path, id);
        }
        catch (Exception e) {
            System.err.println("failed to parse file " + file.getAbsolutePath() + ": " + e.getMessage());
            return Optional.empty();
        }
    }

    private Optional<TypeInfo> getTypeInfo(Document document, String path, int id) {
        String title = document.title();
        String typeName = StringUtils.substringBefore(title, " ");

        Element body = document.body();

        if (!body.html().contains("1.8")) {
            return Optional.empty();
        }

        String fullType = body
                .select(".header h2")
                .first()
                .text();

        String packageName = body
                .select(".header > .subTitle")
                .last()
                .text();

//        Element block = body
//                .select(".contentContainer .description .block")
//                .first();
//
//        String description = block != null ? block.html() : "";

        boolean newType = false;
        Elements elements1 = body.select(".contentContainer .description dd");
        for (Element dd : elements1) {
            if (dd.text().equals("1.8")) {
                newType = true;
                break;
            }
        }

        TypeInfo typeInfo = new TypeInfo();
        typeInfo.setId(id);
        typeInfo.setName(typeName);
        typeInfo.setFullType(fullType);
        typeInfo.setPackageName(packageName);
        typeInfo.setPath(path);
//        typeInfo.setDescription(description);
        typeInfo.setNewType(newType);

        Elements methods = body.select(".contentContainer .details > ul > li > ul > li > ul");

        for (Element ul : methods) {
            String methodName = ul.select("h4").text();
            Elements elements = ul.select("dl > dd");
            for (Element dd : elements) {
                if (newType || dd.text().equals("1.8")) {
                    MethodInfo methodInfo = new MethodInfo();
                    methodInfo.setName(methodName);
                    methodInfo.setDeclaration(ul.select("pre").first().html());
//                    methodInfo.setDescription(ul.select(".block").first().html());
                    typeInfo.getMethods().add(methodInfo);
                    break;
                }
            }
        }

        if (typeInfo.getMethods().isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(typeInfo);
    }

}