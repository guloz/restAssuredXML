package realm.common;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.xml.XmlPath;
import org.junit.Assert;
import org.junit.BeforeClass;

import java.util.UUID;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by Ozeli on 04/06/2017.
 */
public class RealmCommon {
    public static final String ONE_LETTER_STRING = "Z";
    public static final String HUNDRED_CHAR_STRING = UUID.randomUUID() + "7890123456789013245678901234567890123456789012345678901234567890";
    public static final String HUNDRED_AND_ONE_CHAR_STRING = UUID.randomUUID() + "78901234567890132456789012345678901234567890123456789012345678901";
    public static final String TWO_HUNDRED_AND_FIFTY_FIVE_CHAR_STRING = UUID.randomUUID() + "132456789012345678901234567890123456789012345678901324567890123456789012345678901234567890123456789013245678901234567890123456789012345678901234567890132456789012345678901234567890123456789012345678901234567890123456789";
    public static final String TWO_HUNDRED_AND_FIFTY_SIX_CHAR_STRING = UUID.randomUUID() + "1324567890123456789012345678901234567890123456789013245678901234567890123456789012345678901234567890132456789012345678901234567890123456789012345678901324567890123456789012345678901234567890123456789012345678901234567890";
    public static final String RANDOM = UUID.randomUUID() + "132456789012345678901234567890123456789012345678901324567890123456789012345678901234567890123456789013245678901234567890123456789012345678901234567890113245678901234567890123451324567890123456789012345661324567890123456";

    @BeforeClass
    public static void setup() {
        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = Integer.valueOf(8080);
        }
        else{
            RestAssured.port = Integer.valueOf(port);
        }
        String basePath = System.getProperty("server.base");
        if(basePath==null){
            basePath = "/user/realm/";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if(baseHost==null){
            baseHost = "http://recruit01.test01.brighttalk.net";
        }
        RestAssured.baseURI = baseHost;
    }




    protected XmlPath createRealmItemAndCheckStatusOK(String xmlRequest) {
        return given()
                .header("Accept", "application/xml;charset=utf-8")
                .contentType(ContentType.XML)
                .body(xmlRequest)
                .expect()
                .statusCode(201)
                .contentType(ContentType.XML)
                .body("realm.name", notNullValue())
                .log().everything()
                .when()
                .post(baseURI + basePath)
                .prettyPeek().andReturn()
                .xmlPath();
    }

    protected XmlPath getRealmItem(int realmId) {
        return given()
                .header("Accept", "application/xml;charset=utf-8")
                .contentType(ContentType.XML)
                .expect()
                .statusCode(200)
                .contentType(ContentType.XML)
                .body("realm.id", notNullValue())
                .body("realm.name", notNullValue())
                .body("realm.key", notNullValue())
                .log().everything()
                .when()
                .get(baseURI + basePath + realmId)
                .prettyPeek().andReturn()
                .xmlPath();
    }

    protected void deleteRealmItem(int realmId) {
        given()
                .header("Accept", "application/xml;charset=utf-8")
                .contentType(ContentType.XML)
                .expect()
                .statusCode(204)
                .log().everything()
                .when()
                .delete(baseURI + basePath + realmId)
                .prettyPeek().andReturn()
                .xmlPath();
    }


    public boolean checkStringLenth(String realmName, int minLength, int maxLength) {
        int stringLength = realmName.length();
        Assert.assertTrue(stringLength>minLength && stringLength<maxLength);
        return true;
    }


    public boolean isBase32(String input) {
        String base32Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
        char[] base32Array = input.toCharArray();
        char[] base32CharsArray = base32Chars.toCharArray();

        for (int i = 0; i < base32Array.length; i++) {
            boolean isBase32 = false;
            for (int j = 0; j < base32CharsArray.length; j++) {
                if (base32Array[i] == base32CharsArray[j]) {
                    isBase32 = true;
                    break;
                }
            }
            if (!isBase32) {
                return false;
            }
        }
        return true;
    }
}
