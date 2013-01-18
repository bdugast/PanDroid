package cours.android.coursandroid;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	EditText login;
	EditText mdp;
	Button valider;
	Button clean;
	TextView erreurLog;
	LinearLayout titre;
	private ProgressBar chargement;
	TextView loginText;
	
	Drawable fond;
	
	private static final String TAG = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Log.i(TAG, "On Create");
		
		login = (EditText) findViewById(R.id.login);
		mdp = (EditText) findViewById(R.id.mdp);
		
		chargement =(ProgressBar) findViewById(R.id.loginChargement);

		erreurLog = (TextView) findViewById(R.id.erreurLog);
		loginText = (TextView) findViewById(R.id.texteConnexion);
		
		titre = (LinearLayout) findViewById(R.id.linearLayoutTitre);
	
		valider = (Button) findViewById(R.id.boutonAccept);
		valider.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if((login.getText().toString().equals(""))||(mdp.getText().toString().equals(""))){
					erreurLog.setVisibility(View.VISIBLE);
					erreurLog.postDelayed(new Runnable() {
						public void run() {
							erreurLog.setVisibility(View.GONE);
						}
					}, 5000);
				}else{
					String[] donnees = new String[]{login.getText().toString(),mdp.getText().toString()};
					TaskOurs charge = new TaskOurs(new ListenerAsyncTask() {
						
						@Override
						public void OnStart() {
							chargement.setVisibility(View.VISIBLE);
						}
						
						@Override
						public void OnFinish(String result) {
							if(result.equals("true")){
								connexionOk(login.getText().toString(),mdp.getText().toString());
							}else{
								connexionPasbon();
							}
							chargement.setVisibility(View.GONE);
						}
					});
					charge.execute(donnees);
				}
				// Toast.makeText(getApplicationContext(), "YOUHOU :D", Toast.LENGTH_LONG).show();				
			}
		});
		
		
		clean = (Button) findViewById(R.id.boutonClean);
		clean.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				login.setText("");
				mdp.setText("");
				
			}
		});
		
	}
	
	public void connexionOk(String login, String mdp){
		Intent intentConnexion = new Intent(getApplicationContext(),Connecter.class);
		Bundle donnees = new Bundle();
		donnees.putString("log", login);
		donnees.putString("mdp", mdp);
		intentConnexion.putExtras(donnees);
		startActivity(intentConnexion);
	}
	
	public void connexionPasbon(){
		Toast.makeText(getApplicationContext(), "Erreur de mot de passe ou login", Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
