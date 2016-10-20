package com.akshaykant.com.joketeller;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.akshaykant.com.joketeller.databinding.ActivityJokeTellerBinding;

public class JokeTellerActivity extends AppCompatActivity {

    ActivityJokeTellerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_joke_teller);

        //Retrieve the joke from intent
        String intentJoke = null;

        Intent intent = getIntent();
        intentJoke = intent.getStringExtra("joke");

        if (intentJoke != null) {
            binding.jokeTextview.setText(intentJoke);
        } else {
            //if Something goes wrong
            binding.jokeTextview.setText("You are the joke, friend.");
        }
    }
}
