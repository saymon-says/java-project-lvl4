package hexlet.code;

import hexlet.code.domain.Url;
import hexlet.code.domain.query.QUrl;
import io.ebean.DB;
import io.ebean.Transaction;
import io.javalin.Javalin;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    private static String baseUrl;
    private static Javalin javalin;
    private static Transaction transaction;
    private static final int OK = 200;

    @BeforeAll
    public static void beforeAll() {
        javalin = App.getApp();
        javalin.start(0);
        int port = javalin.port();
        baseUrl = "http://localhost:" + port;
    }

    @AfterAll
    public static void afterAll() {
        javalin.stop();
    }

    @BeforeEach
    void beforeEach() {
        transaction = DB.beginTransaction();
    }

    @AfterEach
    void afterEach() {
        transaction.rollback();
    }

    @Test
    public void testRoot() {
        HttpResponse<String> response = Unirest.get(baseUrl).asString();
        assertThat(response.getStatus()).isEqualTo(OK);
    }

    @Test
    public void testAddUrl() {

        String test = "https://test.ru";

        HttpResponse<String> responseUrl = Unirest
                .post(baseUrl + "/urls")
                .field("url", test)
                .asEmpty();

        assertThat(responseUrl.getStatus()).isEqualTo(OK);

        Url testUrl = new QUrl()
                .name.equalTo(test)
                .findOne();

        assertThat(testUrl).isNotNull();
        assertThat(testUrl.getName()).isEqualTo(test);

        HttpResponse<String> response = Unirest
                .get(baseUrl + "/urls")
                .asString();
        String content = response.getBody();

        assertThat(content).contains(test);
    }

}
