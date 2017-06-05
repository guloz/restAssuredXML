package realm.realmE2ETests.realmGet;

import com.jayway.restassured.path.xml.XmlPath;
import org.junit.Test;
import realm.common.RealmCommon;

import java.util.UUID;

import static org.junit.Assert.assertEquals;


public class GetRealm extends RealmCommon {

    @Test // Test 8
    public void givenRealmIdExist_whenGETRealm_DetailsOfRealmItemReturnedSuccesfully() throws Exception {
        String name= UUID.randomUUID().toString();
        String description = UUID.randomUUID().toString();
        String xmlRequest = "<realm name ='" + name + "'>" +"<description>"+description+"</description>"+ "</realm>";

        // CREATE REALMITEM
        XmlPath xmlPath_CreateRealm             = createRealmItemAndCheckStatusOK(xmlRequest);
        //ASSIGN FIELDS TO STRINGS AND INTEGERS
        int realmId                             = xmlPath_CreateRealm.getInt("realm.@id");
        String realmKey                         = xmlPath_CreateRealm.getString("realm.key");

        //GET REALM ITEM
        XmlPath xmlPath_GET = getRealmItem(realmId);

        // CHECK GET VALUES MATCH CREATED OBJECT
        assertEquals(realmId,                   xmlPath_GET.getInt("realm.@id"));
        assertEquals(name,                      xmlPath_GET.getString("realm.@name"));
        assertEquals(description,               xmlPath_GET.getString("realm.description"));
        assertEquals(realmKey,                  xmlPath_GET.getString("realm.key"));

        deleteRealmItem(realmId);

    }
}