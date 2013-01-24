package com.example.parlez.vous.android;

import java.util.concurrent.ExecutionException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class EcrireActivity extends Activity {

	/* Variables */
	ImageButton bouton_envoyer;
	ImageButton bouton_effacer;
	EditText editT_message;
	TextView textV_auteur;
	TextView textV_infos;

	/* Fin variables */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ecrire_activity);

		// R��cup��ration des donn��es de l'activit�� pr��c��dente
		final String identifiant = getIntent().getExtras().getString("id");
		final String mdp = getIntent().getExtras().getString("mdp");

		// Bind xml et code
		bouton_envoyer = (ImageButton) findViewById(R.id.imageButton_ecrire_envoi);
		bouton_effacer = (ImageButton) findViewById(R.id.imageButton_ecrire_effacer);
		textV_auteur = (TextView) findViewById(R.id.editText_ecrire_auteur);
		editT_message = (EditText) findViewById(R.id.editText_ecrire_message);
		textV_infos = (TextView) findViewById(R.id.textView_ecrire_informations);

		textV_auteur.setText("Auteur : " + identifiant);

		// Lors du clique sur le bouton envoyer
		bouton_envoyer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (editT_message.getText().length() == 0) {
					
					// Informations pour l'utilisateur
					textV_infos.setText("Erreur : Veuillez saisir un message");
					textV_infos.setTextColor(getResources().getColor(
							R.color.color_error));
					
					// Le message disparait apr��s 5 secondes
					textV_infos.postDelayed(new Runnable() {
						public void run() {
							textV_infos.setText(null);
						}
					}, 5000);

				} else {
					// Cr��ation de l'AlertDialog pour confirmation
					AlertDialog.Builder builder = new AlertDialog.Builder(
							EcrireActivity.this);
					builder.setCancelable(true);
					builder.setTitle("Confirmation");
					builder.setMessage("Etes-vous sur de vouloir envoyer ce message ?");
					builder.setInverseBackgroundForced(true);
					builder.setPositiveButton("Oui",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									String msg = editT_message.getText()
											.toString();
									msg = msg.replaceAll(" ", "%20"); // Gestion
																		// des
																		// espaces
																		// Gestion
																		// des
																		// retours
																		// �� la
																		// ligne

									EcrireAsyncTask eat = new EcrireAsyncTask();
									try {
										String info_msg = null;
										if (eat.execute(identifiant, mdp, msg)
												.get()) {
											info_msg = "Message envoyé avec succès";
											textV_infos
													.setTextColor(getResources()
															.getColor(
																	R.color.color_valid));
										} else {
											info_msg = "Erreur : Problème lors de l'envoi du message";
											textV_infos
													.setTextColor(getResources()
															.getColor(
																	R.color.color_error));
										}
										textV_infos.setText(info_msg);
										editT_message.setText(null);

										// Le message disparait apr��s 5 secondes
										textV_infos.postDelayed(new Runnable() {
											public void run() {
												textV_infos.setText(null);
											}
										}, 5000);

									} catch (InterruptedException e) {
										e.printStackTrace();
									} catch (ExecutionException e) {
										e.printStackTrace();
									}

									dialog.dismiss();
								}
							});
					builder.setNegativeButton("Non",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
					AlertDialog alert = builder.create();
					alert.show();// Affichage
				}
			}
		});

		// Lors du clique sur le bouton effacer
		bouton_effacer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editT_message.setText(null);
				textV_infos.setText(null);
			}
		});
	}

	/* Lors de l'appui sur le bouton retour */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		EcrireActivity.this.overridePendingTransition(R.anim.anim_slideoutleft,
				R.anim.anim_slideinleft);
	}
}
