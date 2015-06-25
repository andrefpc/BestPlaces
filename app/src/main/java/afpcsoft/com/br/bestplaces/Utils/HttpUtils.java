package afpcsoft.com.br.bestplaces.Utils;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by Andr�Felipe on 18/06/2015.
 */
public class HttpUtils {

    public static String makePostRequest(List<NameValuePair> nameValuePair, String url) {

        HttpClient httpClient = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost(url); // replace with
        // your url

        // Encoding data

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            // log exception
            e.printStackTrace();
        }

        // making request

        try {
            Log.d("POST URL", httpPost.getURI().toString());
            HttpResponse response = httpClient.execute(httpPost);
            // write response to log
            Log.d("Http Post Response:", response.toString());

            String responseString = new BasicResponseHandler().handleResponse(response);
            responseString = new String(responseString.getBytes(), Charset.forName("UTF-8"));
            return responseString;
        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
            return e.getMessage();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
            return e.getMessage();
        }

    }

    public static String makeGetRequest(String url) {

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url); // replace with your
        // url
        // making request

        HttpResponse response;
        try {
            response = client.execute(request);
            Log.d("Response of GET request", response.toString());
            String responseString = new BasicResponseHandler().handleResponse(response);
            return responseString;
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.getMessage();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.getMessage();
        }


    }
}
