package hexlet.code.controllers;

import hexlet.code.domain.Url;
import io.javalin.http.Handler;

public class PageController {

    private static final Handler ADD_PAGE = ctx -> {
        String name = ctx.formParam("url");

        Url url = new Url(name);
        url.save();

        ctx.sessionAttribute("flash", "Ссылка успешно добавлена");
        ctx.render("index.html");
    };

    public static Handler getAddPage() {
        return ADD_PAGE;
    }
}
