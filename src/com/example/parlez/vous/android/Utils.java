package com.example.parlez.vous.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

	/* Constructeur */
	public Utils() {

	}

	/* Vérifie la connexion internet de l'appareil */
	public boolean checkConnexion(Activity mActivity) {

		boolean isOnline = false;

		ConnectivityManager cm = (ConnectivityManager) mActivity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			isOnline = true;
		}

		return isOnline;
	}

	/* Récupèrer le contenu d'une page Web */
	public InputStream getWebContent(String url) {
		System.out.println(url);
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		InputStream content = null;

		try {
			HttpResponse response = client.execute(request);
			content = response.getEntity().getContent();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	/* Converti le contenu d'un InputStream vers un objet String */
	public String convertInputStream2String(InputStream is) {
		String line = "";
		StringBuilder builder = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		try {
			while ((line = rd.readLine()) != null) {
				builder.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	/* Split la chaine de messages en une liste d'objets message */
	public ArrayList<ClassMessage> split(String messages) {
		int i;
		String messagesSplit[] = messages.split(";");
		String userMessage[];

		ArrayList<ClassMessage> listeMessages = new ArrayList<ClassMessage>();
		
		for (i = 0; i < messagesSplit.length; i++) {
			userMessage = messagesSplit[i].split(":");
			ClassMessage cm = new ClassMessage(userMessage[0], userMessage[1]);
			listeMessages.add(cm);
		}
		
		// Inverse la liste -> message le plus récent en dernier
		Collections.reverse(listeMessages);
		
		return listeMessages;
	}
	
	/* Converti la liste d'objet message en HashMap pour affichage dans la listView */
	public ArrayList<HashMap<String, String>> createHashMap(ArrayList<ClassMessage> cm)
	{
		int i;
		ArrayList<HashMap<String, String>> listeItem = new ArrayList<HashMap<String,String>>();
		for(i=0;i<cm.size();i++)
		{
			HashMap<String, String> hm = new HashMap<String, String>();
			hm.put("utilisateur", cm.get(i).getUser());
			hm.put("message", cm.get(i).getMessage());
			listeItem.add(hm);
		}
		return listeItem;
	}
	
	/* Envoi de messages */
	public void sendMessage(String url)
	{
		this.getWebContent(url);
	}
}
