package com.mjc.school.controller.impl;

import io.restassured.path.json.JsonPath;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class NewsControllerTest extends BaseControllerTest {

    public static final String NEWS = "/news";
    public int newsId;
    public String newsTitle;
    public String newsContent;
    public List<Long> tagIds;
    public int authorId;

    @BeforeEach
    public void prepareNews() {
        initAuthor();
        initTag();
        JsonPath rs = initNews(preparedAuthorId, preparedTagId);
        newsId = rs.get("id");
        newsTitle = rs.get("title");
        newsContent = rs.get("content");
        tagIds = rs.get("tagIds");
        authorId = rs.get("authorId");
    }

    @Test
    public void createNews() {
        initAuthor();
        initTag();
        JSONObject params = new JSONObject();
        String title = "news title";
        String content = "news content";
        params.put("title", title);
        params.put("content", content);
        params.put("authorId", preparedAuthorId);
        List<Integer> tags = List.of(preparedTagId);
        params.put("tagIds", tags);
        request.body(params.toString())
                .post(NEWS)
                .then()
                .body("id", notNullValue())
                .body("title", equalTo(title))
                .body("content", equalTo(content))
                .body("tagIds", equalTo(tags))
                .body("createDate", notNullValue())
                .body("lastUpdateDate", notNullValue())
                .body("authorId", equalTo(preparedAuthorId))
                .statusCode(201);
    }

    @Test
    public void getAllNews() {
        request.get(NEWS)
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void getNewsById() {
        request.get(NEWS + "/" + newsId)
                .then()
                .body("id", equalTo(newsId))
                .body("title", equalTo(newsTitle))
                .body("content", equalTo(newsContent))
                .body("tagIds", equalTo(tagIds))
                .body("createDate", notNullValue())
                .body("lastUpdateDate", notNullValue())
                .body("authorId", equalTo(authorId))
                .statusCode(200);
    }

    @Test
    public void updateNews() {
        JSONObject params = new JSONObject();
        String title = "updated title";
        String content = "updated content";
        params.put("title", title);
        params.put("content", content);
        params.put("authorId", preparedAuthorId);
        List<Integer> tags = List.of(preparedTagId);
        params.put("tagIds", tags);
        request.body(params.toString())
                .put(NEWS + "/" + newsId)
                .then()
                .body("id", equalTo(newsId))
                .body("title", equalTo(title))
                .body("content", equalTo(content))
                .body("tagIds", equalTo(tagIds))
                .body("createDate", notNullValue())
                .body("lastUpdateDate", notNullValue())
                .body("authorId", equalTo(preparedAuthorId))
                .statusCode(200);
    }

    @Test
    public void patchNews() {
        JSONObject params = new JSONObject();
        String title = "updated title";
        String content = "updated content";
        params.put("title", title);
        params.put("content", content);
        params.put("authorId", preparedAuthorId);
        List<Integer> tags = List.of(preparedTagId);
        params.put("tags", tags);
        request.body(params.toString())
                .patch(NEWS + "/" + newsId)
                .then()
                .body("id", equalTo(newsId))
                .body("title", equalTo(title))
                .body("content", equalTo(content))
                .body("tagIds", equalTo(tagIds))
                .body("createDate", notNullValue())
                .body("lastUpdateDate", notNullValue())
                .body("authorId", equalTo(preparedAuthorId))
                .statusCode(200);
    }
}
