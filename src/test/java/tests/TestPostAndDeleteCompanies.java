package tests;

import RestHttpClient.ApiCalls;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestPostAndDeleteCompanies {

    CloseableHttpClient httpClient;
    ApiCalls apiCalls;
    String url, jsonData;
    int id;

    @BeforeClass
    public void initialize(){
        httpClient = HttpClients.createDefault();
        apiCalls = new ApiCalls();
        url = "http://localhost:3000/companies";
        jsonData = "{ \"name\": \"name1\", \"Description\": \"Description1\" }";
    }

    @Test(groups = "PostCompanies")
    public void testPostCompanies() throws IOException {
        HttpResponse response = apiCalls.postRequest(httpClient,url,jsonData);
        String responseString = apiCalls.getApiResponseInString(response);
        JsonObject jsonObject = apiCalls.getJsonObject(responseString);
        id = jsonObject.get("id").getAsInt();
        Assert.assertTrue(id > 0);
    }


    @Test(dependsOnGroups = "PostCompanies")
    public void testDeleteCompanies() throws IOException {
        url = url + "/" + id;
        HttpResponse response = apiCalls.deleteRequest(httpClient,url);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode,200);

        //validate GET call should return 404 Not Found
        response = apiCalls.getRequest(httpClient,url);
        statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode,404);
    }

    @AfterClass
    public void cleanup(){
        httpClient.getConnectionManager().shutdown();
    }
}
