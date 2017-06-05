package realm.realmE2ETests.realmGet;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import realm.common.RealmCommon;

import java.util.UUID;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;


public class Negative_GetRealm extends RealmCommon{

//    Test 7: Ream id above 9999- 400 invalidrealmid
//    Test 8: Realmid valid but doesnt exist - 404 Not found - realmNotFound

    @Test // Test 9
    public void givenRealmIdNotAnInteger_whenGetRealm_400InvalidRealmIdErrorReturned(){
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
                .get(baseURI + basePath + realmId)
                .prettyPeek().andReturn()
                .xmlPath();
    }

    @Test // Test 10
    public void givenRealmIdOutsideMaxAllowedRange_InvalidRealm_400ErrorReturned(){
        int realmId=10000;
        given()
                .header("Accept", "application/xml;charset=utf-8")
                .contentType(ContentType.XML)
                .expect()
                .statusCode(400)
                .body("error.code",equalTo("InvalidRealmId"))
                .body("error.message",equalTo("Realm [" + realmId + "] not found."))
                .contentType(ContentType.XML)
                .log().everything()
                .when()
                .get(baseURI + basePath + realmId)
                .prettyPeek().andReturn()
                .xmlPath();
    }

    @Test // Test 11
    public void givenRealmIdNotBelongToAnyRealmItem_InvalidRealm_400ErrorReturned(){
        int realmId= 1;
        deleteRealmItem(realmId);

        given()
                .header("Accept", "application/xml;charset=utf-8")
                .contentType(ContentType.XML)
                .expect()
                .statusCode(400)
                .body("error.code",equalTo("RealmNotFound"))
                .body("error.message",equalTo("Realm [" + realmId + "] not found."))
                .contentType(ContentType.XML)
                .log().everything()
                .when()
                .get(baseURI + basePath + realmId)
                .prettyPeek().andReturn()
                .xmlPath();
    }
}
