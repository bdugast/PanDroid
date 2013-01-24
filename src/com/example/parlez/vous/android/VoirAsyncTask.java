package com.example.parlez.vous.android;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.AsyncTask;

public class VoirAsyncTask extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>>{

	@Override
	protected ArrayList<HashMap<String, String>> doInBackground(String... params) {
		
		/* Variables */
		String url = "http://parlezvous.herokuapp.com/messages/";
		String identifiant = params[0];
		String mdp = params[1];
		/* Fin variables */
		
		Utils utils = new Utils();
		
		// Récupère la liste de message
		String chaineBrute = utils.convertInputStream2String(utils.getWebContent(url+identifiant+"/"+mdp));
			
		// Création et renvoi du HashMap
		return utils.createHashMap(utils.split(chaineBrute));
	}

}
