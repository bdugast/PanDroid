package com.example.parlez.vous.android;

import android.os.AsyncTask;

public class EcrireAsyncTask extends AsyncTask<String, Void, Boolean> {

	@Override
	protected Boolean doInBackground(String... params) {
		
		boolean flag = false;
		String identifiant = params[0];
		String mdp = params[1];
		String message = params[2];
		
		String url = "http://parlezvous.herokuapp.com/message/";
		
		// Envoi du message
		Utils utils = new Utils();
		utils.sendMessage(url+identifiant+"/"+mdp+"/"+message);
		flag = true;
		
		return flag;
	}
}
