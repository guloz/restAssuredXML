package realm.realmE2ETests.commonNegative;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import realm.common.RealmCommon;

import static com.jayway.restassured.RestAssured.*;

public class Negative_Common extends RealmCommon {

    @Test // Test 14
    public void givenPathNotHaveRealmId_whenGetRealm_400MethodNotAllowedErrorReturned(){
        given()
                .header("Accept", "application/xml;charset=utf-8")
                .contentType(ContentType.XML)
                .expect()
                .statusCode(405)
                .log().everything()
                .when()
                .get(baseURI + basePath)
                .prettyPeek().andReturn()
                .xmlPath();
    }

    @Test // Test 15
    public void givenMediaTypeInHeaderNotXML_406Returned(){

        int realmId = 100;
        given()
                .header("Accept", "application/json;charset=UTF-8")
                .expect()
                .statusCode(406)
                .log().everything()
                .when()
                .get(baseURI + basePath + realmId)
                .prettyPeek().andReturn()
                .xmlPath();
    }
}
