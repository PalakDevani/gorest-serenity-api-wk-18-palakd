package com.gorest.userinfo;

import com.gorest.constants.EndPoints;
import com.gorest.model.UserPojo;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;


import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class UserSteps {
    @Step("Creating user with name: {0}, email: {1}, gender:{2}, status:{3}")

    public ValidatableResponse createUser(String name, String email, String gender, String status) {
        UserPojo userPojo = new UserPojo();

        //id 23851,
        userPojo.setName(name);
        userPojo.setEmail(email);
        userPojo.setGender(gender);
        userPojo.setStatus(status);
        return SerenityRest.given().log().all()
                //.contentType(ContentType.JSON)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 600f4364266ef9256401822c412cbfa2a4fe3c13c5c708bf2206cbb120f2a4c9")
                .body(userPojo)
                .when()
                .post()
                .then();
    }

    @Step("Getting user information with email:{0}")

    public HashMap<String, Object> getUserInfoByEmail(String email) {
        String p1 = "findAll{it.email == '";
        String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .header("Authorization", "Bearer 600f4364266ef9256401822c412cbfa2a4fe3c13c5c708bf2206cbb120f2a4c9")
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .when()
                .get(EndPoints.GET_ALL_USERS)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + email + p2); //             .path("/public/v2/users/23851");
    }

    @Step("Getting all users records")
    public ValidatableResponse getUserIDs() {
        return SerenityRest.given().log().all()
                .header("Authorization", "Bearer 600f4364266ef9256401822c412cbfa2a4fe3c13c5c708bf2206cbb120f2a4c9")
                .header("Connection", "keep-alive")
                .when()
                .get(EndPoints.GET_USERS)
                .then();
    }


    @Step("Updating user information with UserId: {0}, name: {1}, email: {2}, gender: {3}, status: {4}")

    public ValidatableResponse updateUserById(int userId, String name, String email, String gender, String status) {
        UserPojo userPojo = new UserPojo();
        userPojo.setName(name);
        userPojo.setEmail(email);
        userPojo.setGender(gender);
        userPojo.setStatus(status);
        return SerenityRest.given().log().all()
                .header("Authorization", "Bearer 600f4364266ef9256401822c412cbfa2a4fe3c13c5c708bf2206cbb120f2a4c9")
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .pathParam("userId", userId)
                .body(userPojo)
                .when()
                //.put()("/users/23851")
                .put(EndPoints.UPDATE_USER_BY_ID)
                .then();
    }

    @Step("Deleting student information with userId: {0}")

    public ValidatableResponse deleteUser(int id) {
        return SerenityRest.given().log().all()
                .header("Authorization", "Bearer 600f4364266ef9256401822c412cbfa2a4fe3c13c5c708bf2206cbb120f2a4c9")
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .pathParam("userId", id)
                .when()
                .delete(EndPoints.DELETE_USER_BY_ID)
                .then();
    }

    @Step("Getting user information with userId: {0}")

    public ValidatableResponse getUserById(int userId) {
        return SerenityRest.given().log().all()
                .header("Authorization", "Bearer 600f4364266ef9256401822c412cbfa2a4fe3c13c5c708bf2206cbb120f2a4c9")
                .pathParam("userId", userId)
                .when()
                .get(EndPoints.GET_SINGLE_USER_BY_ID)
                .then();

    }


}
