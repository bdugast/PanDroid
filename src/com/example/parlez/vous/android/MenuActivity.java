package com.example.parlez.vous.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MenuActivity extends Activity {

	/* Variables */
	TextView textV_menu_informations;
	ImageButton imageButton_voir;
	ImageButton imageButton_ecrire;

	/* Fin variables */

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_activity);

		// Récupére les données envoyées par la précédente activité
		final Bundle extras = getIntent().getExtras();
		String identifiant = extras.getString("id");

		// Bind entre le code et le XML
		textV_menu_informations = (TextView) findViewById(R.id.textView_menu_informations);
		imageButton_voir = (ImageButton) findViewById(R.id.imageButton_menu_voir);
		imageButton_ecrire = (ImageButton) findViewById(R.id.imageButton_menu_ecrire);

		textV_menu_informations.setText("Bienvenue : " + identifiant);

		// Visualiser les messages
		imageButton_voir.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// Envoi des informations
				Intent voirIntent = new Intent(getApplicationContext(),
						VoirActivity.class);
				voirIntent.putExtras(extras);
				startActivity(voirIntent);

				// Effet de transition -> animation
				MenuActivity.this.overridePendingTransition(
						R.anim.anim_slideinright, R.anim.anim_slideoutright);
			}
		});

		// Ecrire un nouveau message
		imageButton_ecrire.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Envoi des informations
				Intent voirIntent = new Intent(getApplicationContext(),
						EcrireActivity.class);
				voirIntent.putExtras(extras);
				startActivity(voirIntent);

				// Effet de transition -> animation
				MenuActivity.this.overridePendingTransition(
						R.anim.anim_slideinright, R.anim.anim_slideoutright);
			}
		});
	}

	/* Lors de l'appui sur le bouton retour */
	@Override
	public void onBackPressed() {
		// Création de l'AlertDialog pour confirmation quitter l'application
		AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
		builder.setCancelable(true);
		builder.setTitle("Confirmation");
		builder.setMessage("Etes-vous sur de vouloir vous déconnecter ?");
		builder.setInverseBackgroundForced(true);
		// On quitte l'application
		builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				MenuActivity.super.onBackPressed();
				MenuActivity.this.overridePendingTransition(R.anim.anim_slideoutleft,
						R.anim.anim_slideinleft);
			}
		});
		// Sinon
		builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();// Affichage
	}
}
