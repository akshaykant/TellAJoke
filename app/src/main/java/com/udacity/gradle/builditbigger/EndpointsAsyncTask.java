package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;

import com.akshaykant.tellajoke.jokebackend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by Akshay Kant on 20-10-2016.
 */

public class EndpointsAsyncTask extends AsyncTask<String, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private ResponseInterface responseInterface;
    private boolean isSuccess;

    EndpointsAsyncTask(ResponseInterface responseInterface) {
        this.responseInterface = responseInterface;

    }

    @Override
    protected String doInBackground(String... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("https://tellajoke-12345.appspot.com/_ah/api/") //a ddress of the Genymotion emulator
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            isSuccess = true;
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            isSuccess = false;
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {

        responseInterface.onResponse(isSuccess, result);

    }

    public interface ResponseInterface {

        void onResponse(boolean isSuccess, String result);

    }
}