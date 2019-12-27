package RestHttpClient;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class ApiCalls {

    public HttpResponse getRequest(CloseableHttpClient httpClient, String url) throws IOException {
        httpClient = HttpClients.createDefault();
        HttpGet getRequest =  new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(getRequest);
        return httpResponse;
    }

    public HttpResponse postRequest(String url, String jsonData) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost postRequest = new HttpPost(url);
        postRequest.addHeader("content-type","application/json");

        //set request post body
        StringEntity entity =  new StringEntity(jsonData);
        postRequest.setEntity(entity);
        HttpResponse response =  httpClient.execute(postRequest);

        return response;
    }

    public HttpResponse deleteRequest(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete deleteRequest =  new HttpDelete(url);
        deleteRequest.addHeader("content-type","application/json");
        HttpResponse response = httpClient.execute(deleteRequest);

        return response;
    }

    public String getApiResponseInString(HttpResponse response){
        HttpEntity httpEntity = response.getEntity();
        try {
            String apiResponseString = EntityUtils.toString(httpEntity);
            return apiResponseString;
        }
        catch (IOException e){
            return null;
        }
    }

    public void closeHttpClientConnection(CloseableHttpClient httpClient){
        //close connection
        httpClient.getConnectionManager().shutdown();
    }

    public JsonArray getJsonArray(String apiResponseString){
        JsonArray jsonArray = new Gson().fromJson(apiResponseString,JsonArray.class);
        return jsonArray;
    }

    public JsonObject getJsonObject(String apiResponseString){
        JsonObject jsonObject =  new Gson().fromJson(apiResponseString,JsonObject.class);
        return jsonObject;
    }


}
