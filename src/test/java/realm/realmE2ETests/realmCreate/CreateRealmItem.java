package realm.realmE2ETests.realmCreate;

import com.jayway.restassured.path.xml.XmlPath;
import org.junit.Test;
import realm.common.RealmCommon;

import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class CreateRealmItem extends RealmCommon {

    @Test // Test 1
    public void givenMinRequiredFieldsProvided_whenCreateRealm_StatusSuccess() throws Exception {
        String name = UUID.randomUUID().toString();
        String xmlRequest = "<realm name ='" + name + "'> </realm>";

        XmlPath xmlPath = createRealmItemAndCheckStatusOK(xmlRequest);

        int realmId = xmlPath.getInt("realm.@id");
        String realmKey = xmlPath.getString("realm.key");

        assertTrue("realmId is out of range: " + realmId, 1 <= realmId && realmId <= 9999);
        isBase32(realmKey);

        deleteRealmItem(realmId);
    }

    @Test // Test 2
    public void givenAllFieldsProvided_whenCreateRealm_AllFieldsValidatedSuccesfully() throws Exception {
        String name = UUID.randomUUID().toString();
        String description = UUID.randomUUID().toString();
        String xmlRequest = "<realm name ='" + name + "'>" +
                "<description>'" + description + "'</description>" +
                " </realm>";

        XmlPath xmlPath = createRealmItemAndCheckStatusOK(xmlRequest);

        int realmId = xmlPath.getInt("realm.@id");

        deleteRealmItem(realmId);
    }
}