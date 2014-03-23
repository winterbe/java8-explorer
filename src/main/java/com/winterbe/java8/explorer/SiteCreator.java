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

    public void createSite(ExplorerResult result) throws IOException {
        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("template.html");

        Document document = Jsoup.parse(inputStream, "UTF-8", "http://download.java.net/jdk8/docs/api/");
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

        File file = new File("_site/index.html");
        BufferedWriter htmlWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        htmlWriter.write(document.toString());
        htmlWriter.flush();
        htmlWriter.close();
    }

    private String createDetailView(TypeInfo typeInfo) {
        String html = "<div id='detail{{id}}' class='detail-view'>{{content}}</div>";

        String content =
                "<div class='panel panel-success'>\n" +
                        "    <div class=\"panel-heading\">\n" +
                        "        <h3 class=\"panel-title\">{{name}}</h3>\n" +
                        "    </div>\n" +
                        "    <div class=\"panel-body\">{{description}}</div>\n" +
                        "</div>";
        content = StringUtils.replaceOnce(content, "{{name}}", typeInfo.getPackageName() + "." + typeInfo.getName());
        content = StringUtils.replaceOnce(content, "{{description}}", typeInfo.getDescription());

        for (MethodInfo methodInfo : typeInfo.getMethods()) {
            String panel =
                    "<div class='panel panel-default'>\n" +
                            "    <div class=\"panel-heading\">\n" +
                            "        <h3 class=\"panel-title\">{{name}}</h3>\n" +
                            "    </div>\n" +
                            "    <div class=\"panel-body\">{{description}}</div>\n" +
                            "</div>";

            panel = StringUtils.replaceOnce(panel, "{{name}}", methodInfo.getDeclaration());
            panel = StringUtils.replaceOnce(panel, "{{description}}", methodInfo.getDescription());
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
