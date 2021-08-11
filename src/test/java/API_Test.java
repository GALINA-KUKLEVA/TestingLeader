import io.restassured.RestAssured;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;

//    Задание 2
// Зайди на https://reqres.in и напиши автотест на тестирование эндпоинта
//        [GET] SINGLE USER (https://reqres.in/api/single_user).
//        Должна осуществляться проверка, что сервер возвращает статус 200 и
//        first_name = “Janet”
//        (опционально)


public class API_Test {

    @Test
    public void apiTest1() {
        RestAssured.given()
            .baseUri("https://reqres.in")
            .basePath("/api/single_user")
            .when().get()
            .then().statusCode(200);
    }

    @Test
    public void apiTest2() {
        RestAssured.given()
            .baseUri("https://reqres.in")
            .basePath("/api/single_user")
            .when().get()
            .then().statusCode(200)
            .body("first_name",equalTo("Janet"));
    }

    @Test
    public void apiTest3() {

        RestAssured.given()
            .baseUri("https://reqres.in")
            .basePath("/api/users/2")
            .when().get()
            .then().statusCode(200)
            .body("data.first_name", equalTo("Janet"));
    }
}
