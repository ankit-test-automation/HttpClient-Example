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
import org.apache.http.impl.client.HttpClients;

public class TestPostAndDeleteUsers {

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

    @Test(groups = "PostUsers")
    public void testPostUsers() throws IOException {
        HttpResponse response = apiCalls.postRequest(url,inputJson);
        String responseString = apiCalls.getApiResponseInString(response);

        JsonObject jsonObject =  apiCalls.getJsonObject(responseString);
        id = jsonObject.get("id").getAsInt();
        Assert.assertTrue(id > 0);
    }

    @Test(dependsOnGroups = "PostUsers")
    public void testDeleteUsers() throws IOException {
        url = url + "/" + id;
        HttpResponse response = apiCalls.deleteRequest(url);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode,200);

        response = apiCalls.getRequest(httpClient,url);
        statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode,404);
    }

    @AfterClass
    public void cleanup(){
        apiCalls.closeHttpClientConnection(httpClient);
    }

}
