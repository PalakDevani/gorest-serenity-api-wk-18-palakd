package com.gorest.userinfo;

import com.gorest.constants.EndPoints;
import com.gorest.model.UserPojo;
import com.gorest.testbase.TestBase;
import com.gorest.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static com.gorest.utils.TestUtils.getRandomValue;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class UserCURDTestWithSteps extends TestBase {

    static String name = "Mala";
    static String email = "mala" + getRandomValue() + "@gmail.com";
    static String gender = "Female";
    static String status = "Active";
    static int userId;


    @Steps
    UserSteps userSteps;

    @Title("This will create new user")
    @Test
    public void test01() {

        ValidatableResponse response = userSteps.createUser(name, email, gender, status);
        response.log().all().statusCode(201);

        //"id": 23892,
        //"id": 23997
        //id 23851,

    }

    @Title("Verify if the student was added to the applications")
    @Test
    public void test002() {
        HashMap<String, Object> userMap = userSteps.getUserInfoByEmail(email);
        Assert.assertThat(userMap, hasValue(email));
        //noinspection SuspiciousMethodCalls
        userId = (int) userMap.get(userId);
        System.out.println("User Id is : " + userId);
    }

//    @Title("Verify if the user was added to the list successfully")
//    @Test
//    public void test002() {
//
//        HashMap<String, Object> userMap = userSteps.getUserById(id);
//        Assert.assertThat(userMap, hasValue(id));
//        id = (int) userMap.get("id");
//  }

    @Title("Updating user information and verifying its updated ")
    @Test
    public void test03() {
        name = name + "_updated";

        userSteps.updateUserById(userId, name, email, gender, status)
                .log().all().statusCode(200);

        HashMap<String, Object> userMap = userSteps.getUserInfoByEmail(email);
        Assert.assertThat(userMap, hasValue(name));
    }


    @Title("Delete the user and verify if the user is deleted!")
    @Test
    public void test004() {

        userSteps.deleteUser(userId).statusCode(204);
        userSteps.getUserById(userId).statusCode(404);
    }
}
