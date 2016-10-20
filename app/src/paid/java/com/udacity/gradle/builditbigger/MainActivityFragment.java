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
import com.udacity.gradle.builditbigger.databinding.FragmentMainBinding;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {

    FragmentMainBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        View root = binding.getRoot();

        binding.jokeLoadingSpinner.setVisibility(View.INVISIBLE);
        binding.tellJokeButton.setEnabled(true);

        binding.tellJokeButton.setOnClickListener(this);

        return root;
    }


    @Override
    public void onClick(View view) {


        if (view.getId() == R.id.tell_joke_button) {

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
    }
}
