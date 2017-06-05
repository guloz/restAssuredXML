package realm.realmE2ETests.fieldValidations;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.xml.XmlPath;
import org.junit.Test;
import realm.common.RealmCommon;

import java.util.UUID;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class FieldValidations extends RealmCommon {

    @Test // Test 17
    public void givenNameHasMaxCharProvided_whenCreateRealm_thenRealmCreatedSuccessfully() throws Exception {
        String name = HUNDRED_CHAR_STRING;
        String xmlRequest = "<realm name ='" + name + "'> </realm>";

        XmlPath xmlPath = createRealmItemAndCheckStatusOK(xmlRequest);

        int realmId = xmlPath.getInt("realm.@id");
        String realmName = xmlPath.getString("realm.@name");

        XmlPath xmlPath_GET = getRealmItem(realmId);

        // CHECK GET VALUES MATCH CREATED OBJECT
        assertEquals(realmName,               xmlPath_GET.getString("realm.@name"));
        deleteRealmItem(realmId);
    }

    @Test // Test 18
    public void givenDescripionHasMaxCharProvided_whenCreateRealm_thenRealmCreatedSuccessfully() throws Exception {
        String name = UUID.randomUUID().toString();
        String description = TWO_HUNDRED_AND_FIFTY_FIVE_CHAR_STRING;
        String xmlRequest = "<realm name ='" + name + "'>" +
                "<description>'" + description + "'</description>" +
                " </realm>";

        XmlPath xmlPath = createRealmItemAndCheckStatusOK(xmlRequest);

        int realmId = xmlPath.getInt("realm.@id");
        String realmDescription = xmlPath.getString("realm.description");

        XmlPath xmlPath_GET = getRealmItem(realmId);

        // CHECK GET VALUES MATCH CREATED OBJECT
        assertEquals(realmDescription,               xmlPath_GET.getString("realm.description"));
        deleteRealmItem(realmId);
    }

    @Test // Test 19
    public void givenRealmIdAboveThreeDigits_IDShouldNotHaveComma(){
        int realmId= 9999;
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
