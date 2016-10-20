package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.fail;
import static org.junit.Assert.assertTrue;


/**
 * Created by Akshay Kant on 20-10-2016.
 */

@RunWith(AndroidJUnit4.class)
public class EndpointUnitTest {

    private final CountDownLatch mSignal = new CountDownLatch(1);

    @Test
    public void testEndpointJokeRetriever() {

        new EndpointsAsyncTask(new EndpointsAsyncTask.ResponseInterface() {
            @Override
            public void onResponse(boolean isSuccess, String result) {

                //test condition
                assertTrue(isSuccess && result != null && result.length() > 0);
                mSignal.countDown();
            }
        }).execute();

        try {
            boolean success = mSignal.await(5, TimeUnit.SECONDS);
            if (!success) {
                fail("Test timed out, make sure the server is actually running.");
            }
        } catch (InterruptedException e) {
            fail();
        }

    }
}
