package com.mjc.school.controller.impl;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AuthorControllerTest extends BaseControllerTest {

    public static final String AUTHORS = "/authors";

    @BeforeEach
    public void prepareAuthor() {
        initAuthor();
    }

    @AfterEach
    public void deletePreparedAuthor() {
        cleanAuthor(preparedAuthorId);
    }

    @Test
    public void getAllAuthors() {
        request.get(AUTHORS)
                .then()
                .body(notNullValue())
                .statusCode(200);
    }

    @Test
    public void createAuthor() {
        JSONObject requestParams = new JSONObject();
        String name = "Olia tester";
        requestParams.put("name", name);
        request.body(requestParams.toString())
                .post(AUTHORS)
                .then()
                .body("name", equalTo(name))
                .body("createDate", notNullValue())
                .body("lastUpdateDate", notNullValue())
                .statusCode(201);
    }

    @Test
    public void getAuthorsById() {
        request.get(AUTHORS + "/" + preparedAuthorId).then()
                .body("name", equalTo(preparedAuthorName))
                .body("id", equalTo(preparedAuthorId))
                .body("createDate", notNullValue())
                .body("lastUpdateDate", notNullValue())
                .statusCode(200);
    }

    @Test
    public void deleteAuthorById() {
        request.delete(AUTHORS + "/" + preparedAuthorId).then()
                .statusCode(204);
        request.get(AUTHORS + "/" + preparedAuthorId).then().statusCode(404);
    }

    @Test
    public void putAuthor() {
        String newName = "new Olia";
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", newName);
        request.body(requestParams.toString())
                .put(AUTHORS + "/" + preparedAuthorId)
                .then()
                .statusCode(200)
                .body("name", equalTo(newName));
    }

    @Test
    public void patchAuthor() {
        String newName = "name after";
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", newName);
        request.body(requestParams.toString())
                .patch(AUTHORS + "/" + preparedAuthorId)
                .then()
                .statusCode(200)
                .body("name", equalTo(newName));
    }
}
