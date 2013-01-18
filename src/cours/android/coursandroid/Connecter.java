package cours.android.coursandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Connecter extends Activity{
	
	TextView login;
	Button voirMessages;
	Button ajouterMessage;
	
	String log, mdp;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.espace_connecter);
		
		log = getIntent().getExtras().getString("log");
		mdp = getIntent().getExtras().getString("mdp");
		
		login = (TextView) findViewById(R.id.texteConnexion);
		login.setText("Bienvenue : "+ log);
		
		voirMessages = (Button)findViewById(R.id.boutonVoirMessage);
		voirMessages.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				String[] donnees = new String[]{log,mdp};
				TaskOursLireMessages charge = new TaskOursLireMessages(new ListenerAsyncTask() {
				
				@Override
				public void OnStart() {
				}
				
				@Override
				public void OnFinish(String result) {
					afficherMessages(result);
				}
			});
			charge.execute(donnees);
			}
		});
		
		ajouterMessage = (Button)findViewById(R.id.boutonAddMessage);
		ajouterMessage.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intentAjoutMessage = new Intent(getApplicationContext(),AjoutMessage.class);
				Bundle donnees = new Bundle();
				donnees.putString("log", log);
				donnees.putString("mdp", mdp);
				intentAjoutMessage.putExtras(donnees);
				startActivity(intentAjoutMessage);			
			}
		});
	}
	public void afficherMessages(String result){
		Intent intentVoirMessage = new Intent(getApplicationContext(),VoirMessages.class);
		Bundle donnees = new Bundle();
		donnees.putString("log", log);
		donnees.putString("mdp", mdp);
		donnees.putString("messages", result);
		intentVoirMessage.putExtras(donnees);
		startActivity(intentVoirMessage);
	}
}
