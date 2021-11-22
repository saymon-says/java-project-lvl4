package hexlet.code.controllers;

import hexlet.code.domain.Url;
import hexlet.code.domain.query.QUrl;
import io.ebean.PagedList;
import io.javalin.http.Handler;

import java.util.List;

public final class PageController {

    private static final int PAGINATION = 10;

    private final Handler addPage = ctx -> {
        String name = ctx.formParam("url");

        Url url = new Url(name);
        url.save();

        ctx.sessionAttribute("flash", "Ссылка успешно добавлена");
        ctx.render("index.html");
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

}
