package cours.android.coursandroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
public class TaskOursAjoutMessage extends AsyncTask<String, Void, String>{

	private ListenerAsyncTask listener;
	String texte;
	
	public TaskOursAjoutMessage(ListenerAsyncTask listener)  {
		this.listener = listener;
	}
	
	@Override
	protected void onPreExecute() {
		if(listener!=null){
			listener.OnStart();
		}
	}
	
	@Override
	protected String doInBackground(String... params) {
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet("http://parlezvous.herokuapp.com/message/"+params[0]+"/"+params[1]+"/"+params[2]);
			client.execute(request);
		 
			//InputStream content = response.getEntity().getContent();
		 
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "done";
	}

	protected void onPostExecute(String result) {
		if(listener!=null){
			listener.OnFinish(texte);
		}
	}
}
