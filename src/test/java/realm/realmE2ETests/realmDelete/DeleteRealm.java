package realm.realmE2ETests.realmDelete;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.xml.XmlPath;
import org.junit.Test;
import realm.common.RealmCommon;

import java.util.UUID;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;


public class DeleteRealm extends RealmCommon {

    @Test // Test 12
    public void givenRealmIDIsValid_whenDeleteRealm_RealmDeletedSuccessfully(){
        String name= UUID.randomUUID().toString();
        String description = UUID.randomUUID().toString();
        String xmlRequest = "<realm name ='" + name + "'>" +"<description>"+description+"</description>"+ "</realm>";

        // CREATE REALMITEM
        XmlPath xmlPath_CreateRealm             = createRealmItemAndCheckStatusOK(xmlRequest);
        int realmId                             = xmlPath_CreateRealm.getInt("realm.@id");
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
