package com.example.android.database;

import android.content.Context;
import android.media.audiofx.BassBoost;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.example.android.settings.Settings;
/**
 * Created by YingYi on 2016/8/30.
 */
public class HttpConnector {
    private Context application;
    private ArrayList<NameValuePair> params;
    public HttpConnector(Context context) {
        this.application = context;

    }
    public void insert(ArrayList<NameValuePair> params) {
        this.params = params;
        Thread networkThread = new Thread(insertRunnable);
        networkThread.start();
    }

    private Runnable insertRunnable = new Runnable() {
        @Override
        public void run() {
            String result = "";
            Log.d("log_tag_DB", "start insert");
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(Settings.serverURL);

                httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                // view_account.setText(httpResponse.getStatusLine().toString());
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream inputStream = httpEntity.getContent();
                // Receive response from server
                BufferedReader bufReader = new BufferedReader(
                        new InputStreamReader(inputStream, "utf-8"), 8);
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = bufReader.readLine()) != null) {
                    builder.append(line + "\n");
                }
                inputStream.close();
                result = builder.toString();
            } catch (Exception e) {
                Log.e("log_tag_DB", e.toString());
            }
            Log.e("log_tag_DB", result);
            Message message = mHandler.obtainMessage(0, result);
            message.sendToTarget();

        }
    };

    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message message) {
            String result = message.obj.toString();
            if (result.equals("Successfully\n")) {
                Toast.makeText(application, "Successful", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(application, "Upload Error", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
