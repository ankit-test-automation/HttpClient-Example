package tests;

import RestHttpClient.ApiCalls;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestPatchUser {

    CloseableHttpClient httpClient;
    ApiCalls apiCalls;
    String url,inputJson;
    int id;

    @BeforeClass
    public void initialize(){
        httpClient = HttpClients.createDefault();
        apiCalls = new ApiCalls();
        url = "http://localhost:3000/Users";
        inputJson = "{\"firstname\": \"FirstName\",\"lastname\": \"LastName\",\"companyid\": 1,\"email\": \"firstname@gmail.com\",\"age\": 40  }";
    }

    @BeforeMethod
    public void CreateUser() throws IOException {
        HttpResponse response = apiCalls.postRequest(httpClient,url,inputJson);
        String responseString = apiCalls.getApiResponseInString(response);
        JsonObject jsonObject = apiCalls.getJsonObject(responseString);
        id = jsonObject.get("id").getAsInt();
    }

    @Test
    public void patchUser() throws IOException {
        String inputJson = "{\"firstname\" : \"updated\", \"lastname\" : \"updated\"}";
        HttpResponse response = apiCalls.patchRequest(httpClient,url,id,inputJson);
        String responseString = apiCalls.getApiResponseInString(response);
        JsonObject jsonObject = apiCalls.getJsonObject(responseString);
        int updatedId = jsonObject.get("id").getAsInt();
        Assert.assertEquals(id,updatedId);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode,200);
    }

    @AfterClass
    public void cleanUp() throws IOException {
        apiCalls.deleteRequest(httpClient,url+"/"+id);
        apiCalls.closeHttpClientConnection(httpClient);
    }

}
