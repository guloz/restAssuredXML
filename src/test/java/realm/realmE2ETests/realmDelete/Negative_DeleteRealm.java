package realm.realmE2ETests.realmDelete;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import realm.common.RealmCommon;

import java.util.UUID;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class Negative_DeleteRealm extends RealmCommon{

    @Test // Test 13
    public void givenRealmIdNotAnInteger_DeleteRealmId_Returns400InvalidRealmIdErrorReturned(){
        String realmId= String.valueOf(UUID.randomUUID());

        given()
                .header("Accept", "application/xml;charset=utf-8")
                .contentType(ContentType.XML)
                .expect()
                .statusCode(400)
                .body("error.code",equalTo("InvalidRealmId"))
                .body("error.message",equalTo("Invalid realm id [" + realmId + "]."))
                .contentType(ContentType.XML)
                .log().everything()
                .when()
                .delete(baseURI + basePath + realmId)
                .prettyPeek().andReturn()
                .xmlPath();
    }
}
