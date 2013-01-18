package cours.android.coursandroid;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import cours.android.coursandroid.R;

public class VoirMessages extends Activity{

	String log, mdp, messages;
	ArrayList<HashMap<String, String>> listeItem = new ArrayList<HashMap<String, String>>();
	ListView listeMessages;
	Button rafraichir;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.affichage_messages);

		log = getIntent().getExtras().getString("log");
		mdp = getIntent().getExtras().getString("mdp");
		messages = getIntent().getExtras().getString("messages");
		
		parser(messages);

		SimpleAdapter miseEnFormeItems = new SimpleAdapter(getApplicationContext(),listeItem, R.layout.affichageitem,
				new String[] {"user","message" },	new int[] {R.id.user, R.id.message});
		listeMessages = (ListView) findViewById(R.id.listViewMessages);
		listeMessages.setAdapter(miseEnFormeItems);
		
		rafraichir = (Button) findViewById(R.id.buttonRafraichir);
		rafraichir.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Toast.makeText(getApplicationContext(), "Faire quelque chose pour refresh", Toast.LENGTH_LONG).show();
			}
		});
	}

	private void parser(String messages) {
		int i, nb;
		HashMap<String, String> unItem;
		String messagesSplit[] = messages.split(";");
		String userMessage[];
			
		nb = messagesSplit.length;
		for(i=0;i<nb;i++){
			unItem = new HashMap<String, String>();
			userMessage = messagesSplit[i].split(":");
			
			unItem.put("user", userMessage[0]);
			unItem.put("message", userMessage[1]);
			
			listeItem.add(unItem);
		}
	}
}
