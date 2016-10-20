package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akshaykant.com.joketeller.JokeTellerActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.databinding.FragmentMainBinding;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {

    FragmentMainBinding binding;

    InterstitialAd mInterstitialAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        View root = binding.getRoot();

        binding.jokeLoadingSpinner.setVisibility(View.INVISIBLE);
        binding.tellJokeButton.setEnabled(true);

        interstitialAddPreFetch();

        //Kick off the fetch
        requestNewInterstitial();

        binding.tellJokeButton.setOnClickListener(this);

        return root;
    }

    private void interstitialAddPreFetch() {

        //Set up for pre-fetching interstitial ad request
        mInterstitialAd = new InterstitialAd(getContext());

        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_test_ad_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();

                //process the joke Request
                binding.jokeLoadingSpinner.setVisibility(View.VISIBLE);
                fetchJoke();

                //pre-fetch the next ad
                requestNewInterstitial();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);

                //pre-fetch the next ad
                requestNewInterstitial();

            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
        });

        //Kick off the fetch
        requestNewInterstitial();
    }

    @Override
    public void onClick(View view) {


        if (view.getId() == R.id.tell_joke_button) {

            //show the interstitial if it's ready
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                binding.jokeLoadingSpinner.setVisibility(View.VISIBLE);
                fetchJoke();
            }
        }
    }

    private void fetchJoke() {

        binding.jokeLoadingSpinner.setVisibility(View.VISIBLE);
        binding.tellJokeButton.setEnabled(false);

        new EndpointsAsyncTask(new EndpointsAsyncTask.ResponseInterface() {

            @Override
            public void onResponse(boolean isSuccess, String result) {

                binding.jokeLoadingSpinner.setVisibility(View.INVISIBLE);
                binding.tellJokeButton.setEnabled(true);

                if (isSuccess) {
                    Intent intentJoke = new Intent(getActivity(), JokeTellerActivity.class);
                    intentJoke.putExtra("joke", result);

                    startActivity(intentJoke);
                } else {

                    Toast.makeText(getActivity(), "Error: " + result, Toast.LENGTH_LONG).show();
                }

            }
        }).execute();
    }


    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }
}
