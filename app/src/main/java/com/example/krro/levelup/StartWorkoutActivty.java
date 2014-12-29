package com.example.krro.levelup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class StartWorkoutActivty extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startworkout);

        Intent startWorkout = getIntent();
        final int terminID = startWorkout.getIntExtra("terminID", 0);
        final int workoutID = startWorkout.getIntExtra("workoutID", 0);
    }

}
