package hexlet.code;

import hexlet.code.domain.Url;
import hexlet.code.domain.query.QUrl;
import io.ebean.DB;
import io.ebean.Transaction;
import io.javalin.Javalin;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
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
    private static MockWebServer mockWebServer;

    @BeforeAll
    public static void beforeAll() throws IOException {
        javalin = App.getApp();
        javalin.start(0);
        int port = javalin.port();
        baseUrl = "http://localhost:" + port;

//        mockWebServer = new MockWebServer();
//        mockWebServer.url("/").toString();
//        mockWebServer.start();
    }

    @AfterAll
    public static void afterAll() throws IOException {
        javalin.stop();
//        mockWebServer.shutdown();
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

    @Test
    public void testCheckUrl() {

//        MockResponse mockResponse = new MockResponse()
//                .addHeader("Content-Type", "application/json; charset=utf-8")
//                .setBody("{\"id\": 1, \"name\":\"duke\"}");
//        mockWebServer.enqueue(mockResponse);
//
////        mockWebServer.enqueue(new MockResponse().setBody("hello, world!")
////                .setStatus("200")
////                .setBody("<h1>hello</h1>"));
//
//
//        HttpResponse<String> response = Unirest
//                .get("/")
//                .asString();
//        System.out.println(response.getBody());

    }

}
