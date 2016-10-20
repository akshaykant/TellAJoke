package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akshaykant.com.joketeller.JokeTellerActivity;
import com.akshaykant.joke.Joke;
import com.google.android.gms.ads.AdRequest;
import com.udacity.gradle.builditbigger.databinding.FragmentMainBinding;



/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {

    FragmentMainBinding binding;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        View root = binding.getRoot();

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        binding.adView.loadAd(adRequest);

        //Toast the Joke on click
        binding.tellJokeButton.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View view) {

        Joke tellJoke = new Joke();

        if (view.getId() == R.id.tell_joke_button) {

            Intent intentJoke = new Intent(getActivity(), JokeTellerActivity.class);

            intentJoke.putExtra("joke", tellJoke.getJoke());

            //start activity
            startActivity(intentJoke);
        }
    }
}
