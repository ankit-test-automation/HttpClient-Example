package tests;

import RestHttpClient.ApiCalls;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestGetCalls {

    CloseableHttpClient httpClient;
    ApiCalls apiCalls;
    String url;

    @BeforeClass
    public void initialize(){
        httpClient = HttpClients.createDefault();
        apiCalls =  new ApiCalls();
        url = "http://localhost:3000/";
    }

    @Test
    public void testGetUsers() throws IOException {
        HttpResponse response =  apiCalls.getRequest(httpClient, url+"Users/?id=1");
        System.out.println(url+"Users/?id=1");
        String responseString = apiCalls.getApiResponseInString(response);
        JsonArray jsonArray = apiCalls.getJsonArray(responseString);

        JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
        Assert.assertTrue(jsonObject.get("firstname").toString().contains("John"));
    }

    @Test
    public  void testGetCompanies() throws IOException {
        HttpResponse response =  apiCalls.getRequest(httpClient, url+"companies/?id=1");
        System.out.println(url+"Users/?id=1");
        String responseString = apiCalls.getApiResponseInString(response);
        JsonArray jsonArray = apiCalls.getJsonArray(responseString);
        JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();

        Assert.assertTrue(jsonObject.get("name").toString().contains("Google"));
        Assert.assertTrue(jsonObject.get("Description").toString().contains("Google LLC is an American multinational technology"));
    }

    @AfterClass
    public void cleanUp(){
        apiCalls.closeHttpClientConnection(httpClient);
    }
}
