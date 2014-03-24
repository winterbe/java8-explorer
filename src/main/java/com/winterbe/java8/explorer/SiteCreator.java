package com.winterbe.java8.explorer;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * @author Benjamin Winterberg
 */
public class SiteCreator {

    private static final String URI = "http://docs.oracle.com/javase/8/docs/api/";

    public void createSite(ExplorerResult result) throws IOException {
        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("template.html");

        Document document = Jsoup.parse(inputStream, "UTF-8", URI);
        Element contentList = document.body().getElementById("content-list");
        Element details = document.body().getElementById("details");


        List<TypeInfo> typeInfos = result.getTypeInfos();
        for (TypeInfo typeInfo : typeInfos) {
            StringBuilder listEntry = createListEntry(typeInfo);
            contentList.append(listEntry.toString());

            String detailView = createDetailView(typeInfo);
            details.append(detailView);
        }

        document.body().select("table").addClass("table").addClass("table-bordered");

        rewriteRelativeUrls(document);

        File file = new File("_site/index.html");
        BufferedWriter htmlWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        htmlWriter.write(document.toString());
        htmlWriter.flush();
        htmlWriter.close();
    }

    private void rewriteRelativeUrls(Document document) {
        document.body().select("a").forEach((a) -> {
            String href = a.attr("href");
            if (href.equals("#")) {
                return;
            }
            a.attr("target", "_blank");
            if (!href.startsWith("http")) {
                href = StringUtils.remove(href, "../");
                a.attr("href", URI + href);
            }
        });
    }

    private String createDetailView(TypeInfo typeInfo) {
//        String description = typeInfo.getDescription();
//        if (!typeInfo.isNewType()) {
//            int size = typeInfo.getMethods().size();
//            description = "<p class='text-muted'>This type already exists in earlier versions of Java. {{size}} new member{{text}} been added in JDK 1.8.<br><br>" +
//                    "See official javadoc for detailed descriptions of this type.</p>";
//            description = StringUtils.replaceOnce(description, "{{size}}", String.valueOf(size));
//            description = StringUtils.replaceOnce(description, "{{text}}", size == 1 ? " has" : "s have");
//        }

        String html = "<div id='detail{{id}}' class='detail-view'>{{content}}</div>";

        String content =
                "<div class='panel panel-success'>\n" +
                        "    <div class='panel-heading'>\n" +
                        "        <h3 class='panel-title'>{{name}}</h3>\n" +
                        "    </div>\n" +
                        "    <div class='panel-body'><code>{{declaration}}</code></div>\n" +
                        "</div>";
        content = StringUtils.replaceOnce(content, "{{name}}", typeInfo.getFullType());
        content = StringUtils.replaceOnce(content, "{{declaration}}", typeInfo.getPackageName() + "." + typeInfo.getName());

        for (MethodInfo methodInfo : typeInfo.getMethods()) {
            String panel =
                    "<div class='panel panel-info'>\n" +
                            "    <div class='panel-heading'>\n" +
                            "        <h3 class='panel-title'>{{name}}</h3>\n" +
                            "    </div>\n" +
                            "    <div class='panel-body'><code>{{declaration}}</code></div>\n" +
                            "</div>";

            panel = StringUtils.replaceOnce(panel, "{{name}}", methodInfo.getName());
            panel = StringUtils.replaceOnce(panel, "{{declaration}}", methodInfo.getDeclaration());
            content += panel;
        }

        html = StringUtils.replaceOnce(html, "{{id}}", String.valueOf(typeInfo.getId()));
        html = StringUtils.replaceOnce(html, "{{content}}", content);
        return html;
    }

    private StringBuilder createListEntry(TypeInfo typeInfo) {
        StringBuilder html = new StringBuilder()
                .append("<a href='#' class='list-group-item' data-target-id='")
                .append(typeInfo.getId())
                .append("'>")
                .append("<span class='badge'>")
                .append(typeInfo.getMethods().size())
                .append("</span>")
                .append(typeInfo.getPackageName())
                .append(".")
                .append(typeInfo.getName());

        if (typeInfo.isNewType()) {
            html.append(" <span class='label label-success'>NEW</span>");
        }

        html.append("</a>");
        return html;
    }

}
