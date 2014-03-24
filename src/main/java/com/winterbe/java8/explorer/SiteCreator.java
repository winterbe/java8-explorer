package com.winterbe.java8.explorer;

import org.apache.commons.lang3.StringUtils;
import org.intellij.lang.annotations.Language;
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
        @Language("HTML")
        String html = "<div id='detail{{id}}' class='detail-view'>{{content}}</div>";

        @Language("HTML")
        String content =
                "<div class='panel panel-primary'>\n" +
                        "    <div class='panel-heading'>\n" +
                        "        <h3 class='panel-title'>{{name}}</h3>\n" +
                        "    </div>\n" +
                        "    <div class='panel-body'>\n" +
                        "        <code>{{declaration}}</code>\n" +
                        "        <div class='pull-right'>\n" +
                        "            <a href='{{url}}' class='btn btn-xs btn-default'>JAVADOC</a>\n" +
                        "        </div>\n" +
                        "    </div>\n" +
                        "</div>";

        content = StringUtils.replaceOnce(content, "{{name}}", typeInfo.getFullType());
        content = StringUtils.replaceOnce(content, "{{declaration}}", typeInfo.getPackageName() + "." + typeInfo.getName());
        content = StringUtils.replaceOnce(content, "{{url}}", URI + typeInfo.getPath());

        for (MethodInfo methodInfo : typeInfo.getMethods()) {
            @Language("HTML")
            String panel =
                    "<div class='panel panel-success'>\n" +
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
