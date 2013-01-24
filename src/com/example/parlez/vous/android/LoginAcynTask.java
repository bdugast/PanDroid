package com.example.parlez.vous.android;

import android.app.Activity;
import android.os.AsyncTask;

public class LoginAcynTask extends AsyncTask<String, Void, String> {

	private Activity mActivity;

	/* Constructeur */
	public LoginAcynTask(Activity activity) {
		this.mActivity = activity;
	}
	@Override
	protected String doInBackground(String... params) {

		/* Variables */
		String url = "http://parlezvous.herokuapp.com/connect/";
		String identifiant = params[0];
		String mdp = params[1];
		String retour = "erreur";
		/* Fin variables */
		
		Utils utils = new Utils();

		// On vérifie la connexion internet
		if (utils.checkConnexion(this.mActivity)) {
			// On vérifie l'authentification
			String r = utils.convertInputStream2String(utils.getWebContent(url
					+ identifiant + "/" + mdp));
			if (r.equals("true"))
				retour = "ok";
		} else {
			// En cas d'erreur de connexion
			retour = "internet";
		}

		// Return
		return retour;
	}
}
