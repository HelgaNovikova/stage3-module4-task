package com.mjc.school.controller.impl;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class TagControllerTest extends BaseControllerTest {

    public static final String TAGS = "/tags";
    private int createdTagId;
    private String createdTagName;

    @BeforeEach
    public void prepareTag() {
        JSONObject params = new JSONObject();
        params.put("name", "tagName");
        Response response = request.body(params.toString()).post(TAGS).andReturn();
        JsonPath rs = new JsonPath(response.asString());
        createdTagId = rs.get("id");
        createdTagName = rs.get("name");
    }

    @AfterEach
    public void deletePreparedTag() {
        request.delete(TAGS + "/" + createdTagId);
    }

    @Test
    public void getAllTags() {
        request.get(TAGS)
                .then()
                .assertThat()
                .body(notNullValue())
                .statusCode(200);
    }

    @Test
    public void getTagById() {
        request.get(TAGS + "/" + createdTagId)
                .then()
                .body("id", equalTo(createdTagId))
                .body("name", equalTo(createdTagName))
                .statusCode(200);
    }

    @Test
    public void createTag() {
        JSONObject params = new JSONObject();
        String createdName = "created name";
        params.put("name", createdName);
        request.body(params.toString())
                .post(TAGS)
                .then()
                .body("id", notNullValue())
                .body("name", equalTo(createdName))
                .statusCode(201);
    }

    @Test
    public void updateTag() {
        JSONObject params = new JSONObject();
        String updatedName = "updated name";
        params.put("name", updatedName);
        request.body(params.toString())
                .put(TAGS + "/" + createdTagId)
                .then()
                .body("name", equalTo(updatedName))
                .statusCode(200);
    }

    @Test
    public void deleteTagById() {
        request.delete(TAGS + "/" + createdTagId)
                .then()
                .statusCode(204);
        request.get("/" + createdTagId).then().statusCode(404);
    }

    @Test
    public void patchTag() {
        JSONObject param = new JSONObject();
        String patchedName = "patched name";
        param.put("name", patchedName);
        request.body(param.toString())
                .patch(TAGS + "/" + createdTagId)
                .then()
                .body("id", equalTo(createdTagId))
                .body("name", equalTo(patchedName))
                .statusCode(200);
    }


}
