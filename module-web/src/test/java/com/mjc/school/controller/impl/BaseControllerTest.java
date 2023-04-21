package com.mjc.school.controller.impl;

import com.mjc.school.Main;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Main.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseControllerTest {

    @LocalServerPort
    private long port;

    protected int preparedAuthorId;
    protected String preparedAuthorName;
    protected int preparedTagId;
    protected int preparedNewsId;

    protected RequestSpecification request;

    @BeforeAll
    public void setURI() {
        RestAssured.baseURI = "http://localhost:" + port;
        request = RestAssured.given();
        request.contentType("application/json");
    }

    public final void initAuthor() {
        JSONObject requestParams = new JSONObject();
        String name = "Olia tester";
        requestParams.put("name", name);
        Response response = request
                .body(requestParams.toString())
                .post("/authors").andReturn();
        JsonPath rs = new JsonPath(response.asString());
        preparedAuthorId = rs.get("id");
        preparedAuthorName = rs.get("name");
    }

    public final void cleanAuthor(int authorId) {
        request.delete("/authors/" + authorId);
    }

    public final JsonPath initNews(int authorId, int tagId) {
        JSONObject params = new JSONObject();
        String title = "news title";
        String content = "news content";
        params.put("title", title);
        params.put("content", content);
        params.put("authorId", authorId);
        List<Long> tags = new ArrayList<>();
        tags.add((long) tagId);
        params.put("tagIds", tags);
        Response response = request.body(params.toString())
                .post("/news").andReturn();
        JsonPath rs = new JsonPath(response.asString());
        preparedNewsId = rs.get("id");
        return rs;
    }

    public final void initTag() {
        JSONObject params = new JSONObject();
        String createdName = "created name";
        params.put("name", createdName);
        Response response = request.body(params.toString())
                .post("/tags").andReturn();
        JsonPath rs = new JsonPath(response.asString());
        preparedTagId = rs.get("id");
    }
}
