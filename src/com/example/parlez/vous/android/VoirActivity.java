package com.example.parlez.vous.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class VoirActivity extends Activity {

	ArrayList<HashMap<String, String>> listeItem;
	ListView listeMessages;
	TextView textV_infos;
	ImageButton imageButton_rafraichir;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voir_activity);

		// Bind xml et code
		listeMessages = (ListView) findViewById(R.id.listView_voir_messages);
		textV_infos = (TextView) findViewById(R.id.textView_infos_messages);
		imageButton_rafraichir = (ImageButton) findViewById(R.id.imageButton_voir_rafraichir);

		// Récupération des données envoyées par l'activité précédente
		final String identifiant = getIntent().getExtras().getString("id");
		final String mdp = getIntent().getExtras().getString("mdp");
		
		// récupération des messages
		getMessages(identifiant,mdp);
		
		// Lorsque l'on clique sur le bouton rafraichir
		imageButton_rafraichir.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getMessages(identifiant,mdp);
			}
		});
	}

	/* Récupération des messages sur le serveur */
	public void getMessages(String identifiant, String mdp) {
		//listeItem.clear();
		
		// Exécute l'asyncTask -> récupération des messages sur le serveur
		VoirAsyncTask vat = new VoirAsyncTask();
		try {
			// Récupération de la liste des messages
			listeItem = vat.execute(identifiant, mdp).get();
			SimpleAdapter miseEnFormeItems = new SimpleAdapter(
					getApplicationContext(), listeItem, R.layout.row_listview,
					new String[] { "utilisateur", "message" }, new int[] {
							R.id.user, R.id.message });

			listeMessages.setAdapter(miseEnFormeItems);
			textV_infos.setText(listeItem.size() + " messages");

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	/* Lors de l'appui sur le bouton retour */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		VoirActivity.this.overridePendingTransition(R.anim.anim_slideoutleft,
				R.anim.anim_slideinleft);
	}
}
