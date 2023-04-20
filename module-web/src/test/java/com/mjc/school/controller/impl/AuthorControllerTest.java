package com.mjc.school.controller.impl;

import com.mjc.school.Main;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Main.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorControllerTest {
    @LocalServerPort
    long port;
    private RequestSpecification request;

    @BeforeEach
    public void setURI() {
        RestAssured.baseURI = "http://localhost:" + port + "/authors";
        request = RestAssured.given();
    }

    @Test
    public void getAllAuthors() {
        request.get().then().statusCode(200);
    }

    @Test
    public void createAuthor() {
        JSONObject requestParams = new JSONObject();
        String name = "Olia tester";
        requestParams.put("name", name);
        request.contentType("application/json")
                .body(requestParams.toString(1)).post().then()
                .body("name", equalTo(name))
                .body("createDate", notNullValue())
                .body("lastUpdateDate", notNullValue())
                .statusCode(201);
    }

    @Test
    public void getAuthorsById() {
        JsonPath response = createAuthorAndReturnResponse();
        int authorId = response.get("id");
        request.get("/" + authorId).then()
                .body("name", equalTo(response.get("name")))
                .body("id", equalTo(authorId))
                .body("createDate", notNullValue())
                .body("lastUpdateDate", notNullValue())
                .statusCode(200);
    }

    @Test
    public void deleteAuthorById() {
        JsonPath response = createAuthorAndReturnResponse();
        int authorId = response.get("id");
        request.delete("/" + authorId).then()
                .statusCode(204);
        request.get("/" + authorId).then().statusCode(404);
    }

    @Test
    public void putAuthor() {
        JsonPath response = createAuthorAndReturnResponse();
        int authorId = response.get("id");
        String newName = "new Olia";
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", newName);
        request.contentType("application/json")
                .body(requestParams.toString(1))
                .put("/" + authorId)
                .then()
                .statusCode(200)
                .body("name",equalTo(newName));
    }

    @Test
    public void patchAuthor() {
        JsonPath response = createAuthorAndReturnResponse();
        int authorId = response.get("id");
        String newName = "name after";
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", newName);
        request.contentType("application/json")
                .body(requestParams.toString(1))
                .patch("/" + authorId)
                .then()
                .statusCode(200)
                .body("name", equalTo(newName));
    }

    private JsonPath createAuthorAndReturnResponse(){
        JSONObject requestParams = new JSONObject();
        String name = "Olia tester";
        requestParams.put("name", name);
        Response response = request
                .contentType("application/json")
                .body(requestParams.toString(1))
                .post().andReturn();
        return new JsonPath(response.asString());
    }

}
