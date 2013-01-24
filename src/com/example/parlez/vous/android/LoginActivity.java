package com.example.parlez.vous.android;

import java.util.concurrent.ExecutionException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoginActivity extends Activity {

	/* Variables */
	EditText editT_id;
	EditText editT_mdp;
	TextView textV_infos;
	ImageButton bouton_connexion;
	ImageButton bouton_annuler;
	CheckBox check_rappel;
	SharedPreferences preferences;
	ProgressBar progressBar;
	/* Fin variables */
	
	protected void onRestart() {
		// Lorsque l'activité revient au premier plan
		super.onRestart();
		String username = preferences.getString("username", "");
		if (!username.equals("")) {
			editT_id.setText(username);
			check_rappel.setChecked(true);
			editT_mdp.requestFocus(); // Focus sur le champs mdp
		}
		else
		{
			// Clear les champs
			editT_id.setText(null);
			editT_id.setText(null);
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);

		// Bind entre le code et le XML
		editT_id = (EditText) findViewById(R.id.editText_login_identifiant);
		editT_mdp = (EditText) findViewById(R.id.editText_login_mdp);
		textV_infos = (TextView) findViewById(R.id.textView_login_infos);
		bouton_connexion = (ImageButton) findViewById(R.id.imageButton_login_connexion);
		bouton_annuler = (ImageButton) findViewById(R.id.imageButton_login_annuler);
		check_rappel = (CheckBox) findViewById(R.id.checkBox_login_rappel);
		progressBar = (ProgressBar)findViewById(R.id.progressBar_login);

		// Gestion des préfèrences
		preferences = getSharedPreferences("usersettings", 0);
		String username = preferences.getString("username", "");
		if (!username.equals("")) {
			editT_id.setText(username);
			check_rappel.setChecked(true);
			editT_mdp.requestFocus(); // Focus sur le champs mdp
		}

		// On ajoute un écouteur "onClick" sur le bouton de connexion
		bouton_connexion.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// Récupération des valeurs
				String identifiant = editT_id.getText().toString();
				String mdp = editT_mdp.getText().toString();

				textV_infos.setText(null);

				// Si l'utilisateur n'a pas saisi son identifiant ou son mdp
				if (identifiant.length() == 0 || mdp.length() == 0) {
					textV_infos
							.setText("Erreur : pas d'identifiant / mot de passe saisi");

					// Le message disparait après 5 secondes
					textV_infos.postDelayed(new Runnable() {
						public void run() {
							textV_infos.setText(null);
						}
					}, 5000);

				} else // Sinon
				{
					// Nouveau AsyncTask pour l'affichage de la progressBar
					LoginAcynTask lat = new LoginAcynTask(LoginActivity.this);
					
					try {
						String resultat = lat.execute(identifiant, mdp).get();

						// Si aucune connexion internet
						if (resultat.equals("internet")) {
							textV_infos
									.setText("Erreur : Pas de connexion internet disponible");
							// Le message disparait après 5 secondes
							textV_infos.postDelayed(new Runnable() {
								public void run() {
									textV_infos.setText(null);
								}
							}, 5000);
						}
						// Si l'authentification est effectué avec succès
						else if (resultat.equals("ok")) {

							// Envoi des données à l'activité suivante
							Bundle transmitData = new Bundle();
							transmitData.putString("id", identifiant);
							transmitData.putString("mdp", mdp);
							editT_mdp.setText(null);

							SharedPreferences.Editor editor = preferences
									.edit();
							// Gestion des préférences
							if (check_rappel.isChecked())
								editor.putString("username", identifiant);
							else
								editor.clear();
							editor.commit();

							// Direction -> MenuActivity
							Intent menuActivity = new Intent(
									getApplicationContext(), MenuActivity.class);
							menuActivity.putExtras(transmitData);
							startActivity(menuActivity);

							// Effet de transition -> animation
							LoginActivity.this.overridePendingTransition(
									R.anim.anim_slideinright,
									R.anim.anim_slideoutright);
							// Sinon
						} else {
							textV_infos
									.setText("Erreur : authentification echouée");
							// Le message disparait après 5 secondes
							textV_infos.postDelayed(new Runnable() {
								public void run() {
									textV_infos.setText(null);
								}
							}, 5000);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			}
		});

		// On ajoute un écouteur "onClick" sur le bouton d'annulation
		bouton_annuler.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Clean
				editT_id.setText(null);
				editT_mdp.setText(null);
				textV_infos.setText(null);
				check_rappel.setChecked(false);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_activity, menu);
		return true;
	}
}
