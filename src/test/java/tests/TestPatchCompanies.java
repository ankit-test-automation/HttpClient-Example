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

public class TestPatchCompanies {

    CloseableHttpClient httpClient;
    ApiCalls apiCalls;
    String url,jsonData;
    int id;

    @BeforeClass
    public void initialize(){
        httpClient = HttpClients.createDefault();
        apiCalls = new ApiCalls();
        url = "http://localhost:3000/Companies";
    }

    @BeforeMethod
    public void createCompany() throws IOException {
        jsonData = "{\"name\" : \"Test\" , \"Description\" : \"Test\" }";
        HttpResponse response = apiCalls.postRequest(httpClient,url,jsonData);
        String responseString = apiCalls.getApiResponseInString(response);
        JsonObject responseJson = apiCalls.getJsonObject(responseString);
        id = responseJson.get("id").getAsInt();
    }

    @Test
    public void testPatchCompany() throws IOException {
        String inputJson = "{\"name\" : \"updated\" , \"Description\" : \"updated\" }";
        HttpResponse response = apiCalls.patchRequest(httpClient,url,id,inputJson);
        String responseString = apiCalls.getApiResponseInString(response);
        JsonObject jsonObject = apiCalls.getJsonObject(responseString);
        int updatedId = jsonObject.get("id").getAsInt();
        Assert.assertEquals(updatedId,id);
        int responseCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(responseCode,200);
    }

    @AfterClass
    public void cleanUp() throws IOException {
        apiCalls.deleteRequest(httpClient,url+"/"+id);
        apiCalls.closeHttpClientConnection(httpClient);

    }


}
