package cours.android.coursandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cours.android.coursandroid.R;

public class AjoutMessage extends Activity{
	
	String log, mdp;
	Button envoi;
	EditText message;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajout_message);

		log = getIntent().getExtras().getString("log");
		mdp = getIntent().getExtras().getString("mdp");
		
		message = (EditText) findViewById(R.id.texte);
		
		envoi = (Button)findViewById(R.id.buttonEnvoi);
		envoi.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				if(message.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "Veuillez saisir du texte", Toast.LENGTH_LONG).show();
				}else{
					String[] donnees = new String[]{log,mdp, message.getText().toString()};
					
					TaskOursAjoutMessage charge = new TaskOursAjoutMessage(new ListenerAsyncTask() {
					
						@Override
						public void OnStart() {
						}
						
						@Override
						public void OnFinish(String result) {
							Toast.makeText(getApplicationContext(), "Message ajouté", Toast.LENGTH_LONG).show();
						}
					});
					charge.execute(donnees);
	                onBackPressed();
				}
			}
		});
	}
}
