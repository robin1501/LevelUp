package com.example.krro.levelup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
//test
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void onButtonClick(View view) {
		if(view.getId() == R.id.btnUebersicht) {
			startActivity(new Intent(this, UebersichtActivity.class));

                //UebersichtActivity pie = new UebersichtActivity();
                //Intent lineIntent = pie.getIntent(this);
                //startActivity(lineIntent);


		}
		else if(view.getId() == R.id.btnWorkout) {
			startActivity(new Intent(this, WorkoutActivity.class));
		}
		else if(view.getId() == R.id.btnUebungen) {
			startActivity(new Intent(this, UebungenActivity.class));
		}
		else if(view.getId() == R.id.btnProfil) {
			startActivity(new Intent(this, ProfilActivity.class));
		}
		else if(view.getId() == R.id.btnKalender) {
			startActivity(new Intent(this, KalenderActivity.class));
		}
		else if(view.getId() == R.id.btnEinstellungen) {
			startActivity(new Intent(this, EinstellungenActivity.class));
		}
	}
}
