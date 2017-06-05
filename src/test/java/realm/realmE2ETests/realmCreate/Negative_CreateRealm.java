package realm.realmE2ETests.realmCreate;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import realm.common.RealmCommon;

import java.util.UUID;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;


public class Negative_CreateRealm extends RealmCommon{
//    Test 5: Other: 415 Unsupported Media Type The entity supplied in the body of the request cannot be processed in the media-type specified in the Content-Type request header.

    @Test // Test 3
    public void givenMandotaryRealmNameMissing_createRealm_Returns400MissingRealmNameError() throws Exception {
        String xmlRequest = "<realm></realm>";
        given()
                .header("Accept", "application/xml;charset=utf-8")
                .contentType(ContentType.XML)
                .body(xmlRequest)
                .expect()
                .statusCode(400)
                .contentType(ContentType.XML)
                .body("error.code", equalTo("MissingRealmName"))
                .body("error.message", equalTo("Realm name is mandatory and must be supplied."))
                .log().everything()
                .when()
                .post(baseURI + basePath)
                .prettyPeek().andReturn()
                .xmlPath();
    }

    @Test // Test 4
    public void givenMandotaryRealmNameMatchesAnExistingName_createRealm_Returns400DublicateNameError() throws Exception {
        String name= UUID.randomUUID().toString();
        String xmlRequest1 = "<realm name ='" + name + "'> </realm>";
        createRealmItemAndCheckStatusOK(xmlRequest1);
        String xmlRequest2 = xmlRequest1;

        given()
                .header("Accept", "application/xml;charset=utf-8")
                .contentType(ContentType.XML)
                .body(xmlRequest2)
                .expect()
                .statusCode(400)
                .contentType(ContentType.XML)
                .body("error.code", equalTo("DuplicateRealmName"))
                .body("error.message", equalTo("Duplicate realm name [" + name + "]."))
                .log().everything()
                .when()
                .post(baseURI + basePath)
                .prettyPeek().andReturn()
                .xmlPath();
    }

    @Test // Test 5
    public void givenMandotaryRealmNameLongerThan100Char_createRealm_Returns400InvalidRealmNameError() throws Exception {

        String name= HUNDRED_AND_ONE_CHAR_STRING;

        String xmlRequest = "<realm name ='" + name + "'> </realm>";

        given()
                .header("Accept", "application/xml;charset=utf-8")
                .contentType(ContentType.XML)
                .body(xmlRequest)
                .expect()
                .statusCode(400)
                .contentType(ContentType.XML)
                .body("error.code", equalTo("InvalidRealmName"))
                .body("error.message", equalTo("Realm name should not be longer than 100 chars."))
                .log().everything()
                .when()
                .post(baseURI + basePath)
                .prettyPeek().andReturn()
                .xmlPath();
    }

    @Test // Test 6
    public void givenMandotaryRealmDescriptionLongerThan255Char_createRealm_Returns400InvalidRealmDescriptionError() throws Exception {

        String name= UUID.randomUUID().toString();
        String description = TWO_HUNDRED_AND_FIFTY_SIX_CHAR_STRING;
        String xmlRequest = "<realm name ='" + name + "'><description>"+description+"</description></realm>";

        given()
                .header("Accept", "application/xml;charset=utf-8")
                .contentType(ContentType.XML)
                .body(xmlRequest)
                .expect()
                .statusCode(400)
                .contentType(ContentType.XML)
                .body("error.code", equalTo("InvalidRealmDescription"))
                .body("error.message", equalTo("Realm description should not be longer than 255 chars."))
                .log().everything()
                .when()
                .post(baseURI + basePath)
                .prettyPeek().andReturn()
                .xmlPath();
    }

    @Test // Test 7
    public void givenContentTypeInBodyNotXML_createRealm_Returns415UnsupportedMediaTypeErrorReturned() throws Exception {
        String name= UUID.randomUUID().toString();
        String xmlRequest = "<realm name ='" + name + "'> </realm>";
        given()
                .header("Accept", "application/xml;charset=utf-8")
                .contentType(ContentType.JSON)
                .body(xmlRequest)
                .expect()
                .statusCode(415)
                .log().everything()
                .when()
                .post(baseURI + basePath)
                .prettyPeek().andReturn()
                .xmlPath();
    }
}
