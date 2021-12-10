package hexlet.code;

import hexlet.code.domain.Url;
import hexlet.code.domain.query.QUrl;
import io.ebean.DB;
import io.ebean.Transaction;
import io.javalin.Javalin;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    private static String baseUrl;
    private static Javalin javalin;
    private static Transaction transaction;
    private static final int OK = 200;
    private static final int FOUND = 302;
    private static final int SERVER_ERROR = 500;
    private static MockWebServer mockWebServer;

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

        assertThat(responseUrl.getStatus()).isEqualTo(FOUND);

        Url testUrl = new QUrl()
                .name.equalTo(test)
                .findOne();

        assertThat(testUrl).isNotNull();
        assertThat(testUrl.getName()).isEqualTo(test);
    }

    @Test
    public void testShowUrls() {
        HttpResponse<String> response = Unirest
                .get(baseUrl + "/urls")
                .asString();

        assertThat(response.getStatus()).isEqualTo(OK);

        String content = response.getBody();
        assertThat(content).contains("https://vk.com");
        assertThat(content).contains("http://junit.com");

    }

    @Test
    public void testShowUrl() {
        HttpResponse<String> response = Unirest
                .get(baseUrl + "/urls/100")
                .asString();

        assertThat(response.getStatus()).isEqualTo(SERVER_ERROR);

        HttpResponse<String> response2 = Unirest
                .get(baseUrl + "/urls/2")
                .asString();

        assertThat(response2.getStatus()).isEqualTo(OK);

        String content = response2.getBody();
        assertThat(content).contains("http://junit.com");
    }

    @Test
    public void testCheckUrl() throws IOException {

        mockWebServer = new MockWebServer();
        mockWebServer.enqueue(new MockResponse()
                .setBody("{\"id\": 1, \"name\":\"duke\"}"));

        mockWebServer.start();

        HttpUrl httpUrl = mockWebServer.url("/");

        HttpResponse<String> response = Unirest
                .get(String.valueOf(httpUrl))
                .asString();
        String content = response.getBody();

        assertThat(content).contains("duke");

    }

}
