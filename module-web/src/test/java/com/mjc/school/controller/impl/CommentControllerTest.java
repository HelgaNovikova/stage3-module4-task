package com.mjc.school.controller.impl;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CommentControllerTest extends BaseControllerTest {

    public static final String COMMENTS = "/comments";
    private int createdCommentId;
    private String createdCommentContent;
    private int createdCommentNewsId;

    @BeforeEach
    public void prepareComment() {
        initAuthor();
        initTag();
        initNews(preparedAuthorId, preparedTagId);
        JSONObject params = new JSONObject();
        params.put("content", "comment content");
        params.put("newsId", preparedNewsId);
        Response response = request.body(params.toString())
                .post(COMMENTS)
                .andReturn();
        JsonPath rs = new JsonPath(response.asString());
        createdCommentId = rs.get("id");
        createdCommentContent = rs.get("content");
        createdCommentNewsId = rs.get("newsId");
    }

    @Test
    public void createComment() {
        JSONObject params = new JSONObject();
        params.put("content", "comment content");
        params.put("newsId", preparedNewsId);
        request.body(params.toString())
                .post(COMMENTS)
                .then()
                .body("content", equalTo(createdCommentContent))
                .body("newsId", equalTo(createdCommentNewsId))
                .body("createDate", notNullValue())
                .body("lastUpdateDate", notNullValue())
                .statusCode(201);
    }

    @Test
    public void getAllComments() {
        request.get(COMMENTS)
                .then()
                .body(notNullValue())
                .statusCode(200);
    }

    @Test
    public void getCommentById() {
        request.get(COMMENTS + "/" + createdCommentId)
                .then()
                .body("id", equalTo(createdCommentId))
                .body("content", equalTo(createdCommentContent))
                .body("newsId", equalTo(createdCommentNewsId))
                .body("createDate", notNullValue())
                .body("lastUpdateDate", notNullValue())
                .statusCode(200);
    }

    @Test
    public void updateComment() {
        JSONObject params = new JSONObject();
        String newContent = "new content";
        params.put("content", newContent);
        params.put("newsId", createdCommentNewsId);
        request.body(params.toString())
                .put(COMMENTS + "/" + createdCommentId)
                .then()
                .body("content", equalTo(newContent))
                .body("newsId", equalTo(createdCommentNewsId))
                .body("id", equalTo(createdCommentId))
                .statusCode(200);
    }

    @Test
    public void patchComment() {
        JSONObject params = new JSONObject();
        String newContent = "new content";
        params.put("content", newContent);
        request.body(params.toString())
                .patch(COMMENTS + "/" + createdCommentId)
                .then()
                .body("content", equalTo(newContent))
                .body("newsId", equalTo(createdCommentNewsId))
                .body("id", equalTo(createdCommentId))
                .statusCode(200);
    }

    @Test
    public void deleteCommentById() {
        request.delete(COMMENTS + "/" + createdCommentId)
                .then()
                .statusCode(204);
        request.get(COMMENTS + "/" + createdCommentId).then().statusCode(404);
    }
}
