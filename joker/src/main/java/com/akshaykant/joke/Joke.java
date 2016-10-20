package com.akshaykant.joke;

import java.util.ArrayList;
import java.util.Random;

public class Joke {

    ArrayList<String> jokeList;
    private Random randomJokeGenerator;

    public Joke() {

        jokeList = new ArrayList<String>();
        jokeList.add("I wanted to grow my own food but I couldn’t get bacon seeds anywhere.");
        jokeList.add("I can’t believe I forgot to go to the gym today. That’s 7 years in a row now.");
        jokeList.add("A naked women robbed a bank. Nobody could remember her face.");
        jokeList.add("My wife’s cooking is so bad we usually pray after our food.");
        jokeList.add("I'd like to buy a new boomerang please. Also, can you tell me how to throw the old one away?");
        jokeList.add("A woman in a bikini reveals about 90% of her body.... and yet most men are so polite they only look at the covered parts.");
        jokeList.add("I asked my North Korean friend how it was to live in North Korea. He said he can't complain.");
        jokeList.add("Why did the physics teacher break up with the biology teacher? There was no chemistry.");
        jokeList.add("I’m certain there are female hormones in beer. When I drink too much, I talk nonsense and I cannot control my car.");
        jokeList.add("Can a kangaroo jump higher than a house? Of course, a house doesn’t jump at all.");
        jokeList.add("My dog used to chase people on a bike a lot. It got so bad, finally I had to take his bike away.");
        randomJokeGenerator = new Random();
    }

    public String getJoke() {
        int index = randomJokeGenerator.nextInt(jokeList.size());
        return jokeList.get(index);
    }
}
