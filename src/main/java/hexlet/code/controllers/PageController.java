package hexlet.code.controllers;

import hexlet.code.domain.Url;
import hexlet.code.domain.UrlCheck;
import hexlet.code.domain.query.QUrl;
import hexlet.code.domain.query.QUrlCheck;
import io.ebean.PagedList;
import io.javalin.http.Handler;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public final class PageController {

    private static final int PAGINATION = 10;

    private final Handler addPage = ctx -> {
        String incomeUrl = ctx.formParam("url");
        try {
            String transmittedUrl = createTransmittedUrl(incomeUrl);
            if (isNotSimilarUrl(transmittedUrl)) {
                Url url = new Url(transmittedUrl);
                url.save();
                ctx.sessionAttribute("flash", "Ссылка успешно добавлена");
            } else {
                ctx.sessionAttribute("flash", "Ссылка уже существует");
            }
            ctx.render("index.html");
        } catch (MalformedURLException e) {
            ctx.sessionAttribute("flash", "Ссылка битая");
            ctx.render("index.html");
        }
    };

    public Handler getAddPage() {
        return addPage;
    }


    private final Handler listArticles = ctx -> {
        int page = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
        int rowsPerPage = PAGINATION;
        int offset = (page - 1) * rowsPerPage;

        PagedList<Url> urlPagedList = new QUrl()
                .setFirstRow(offset)
                .setMaxRows(rowsPerPage)
                .orderBy()
                .id.asc()
                .findPagedList();

        List<Url> urls = urlPagedList.getList();

        ctx.attribute("urls", urls);
        ctx.attribute("page", page);
        ctx.render("urls/index.html");
    };

    public Handler getListArticles() {
        return listArticles;
    }

    private final Handler showUrl = ctx -> {
        long id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);

        Url url = new QUrl()
                .id.equalTo(id)
                .findOne();

        List<UrlCheck> urlCheckList = new QUrlCheck()
                .url.equalTo(url)
                .orderBy()
                .id.desc()
                .findList();

        ctx.attribute("checks", urlCheckList);
        ctx.attribute("url", url);
        ctx.render("urls/show.html");
    };

    public Handler getShowUrl() {
        return showUrl;
    }

    private final Handler checks = ctx -> {

        long id = ctx.pathParamAsClass("id", Long.class).getOrDefault(null);

        Url url = new QUrl()
                .id.equalTo(id)
                .findOne();
        try {
            HttpResponse<String> response = Unirest
                    .get(url.getName())
                    .asString();

            String content = response.getBody();

            String contentTitle = "";
            String contentH1 = "";
            String contentDescription = "";
            int contentStatus = response.getStatus();

            Document doc = Jsoup.parse(content);
            if (doc.title() != null) {
                contentTitle = doc.title();
            }
            if (doc.select("meta[name=description]").first() != null) {
                contentDescription = doc.select("meta[name=description]").first().attr("content");
            }
            if (doc.select("h1").first() != null) {
                contentH1 = doc.select("h1").first().text();
            }

            UrlCheck urlCheck = new UrlCheck(contentStatus, contentTitle, contentH1, contentDescription, url);
            urlCheck.save();

            ctx.sessionAttribute("flash", "Страница успешно проверена");
        } catch (Exception e) {
            ctx.sessionAttribute("flash", e.getMessage());
        }

        List<UrlCheck> urlCheckList = new QUrlCheck()
                .url.equalTo(url)
                .orderBy()
                .id.desc()
                .findList();

        ctx.attribute("checks", urlCheckList);
        ctx.attribute("url", url);
        ctx.redirect("/urls/" + id);

//        ctx.render("urls/show.html");
    };

    public Handler getChecks() {
        return checks;
    }

    private static String createTransmittedUrl(String url) throws MalformedURLException {

        URL tempUrl = new URL(url);
        String transmitUrl = String.format("%s://%s", tempUrl.getProtocol(), tempUrl.getHost());
        if (tempUrl.getPort() != -1) {
            transmitUrl += ":" + tempUrl.getPort();
        }
        return transmitUrl;
    }

    private static boolean isNotSimilarUrl(String url) {
        Url checkedUrl = new QUrl()
                .name.equalTo(url)
                .findOne();
        return checkedUrl == null;
    }
}
