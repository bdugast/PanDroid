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
public class TaskOurs extends AsyncTask<String, Void, String>{

	private ListenerAsyncTask listener;
	String texte;
	
	public TaskOurs(ListenerAsyncTask listener)  {
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
			HttpGet request = new HttpGet("http://parlezvous.herokuapp.com/connect/"+params[0]+"/"+params[1]);
			HttpResponse response = client.execute(request);
		 
			InputStream content = response.getEntity().getContent();
		    
			texte = convert(content);
		 
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texte;
	}

	protected void onPostExecute(String result) {
		if(listener!=null){
			listener.OnFinish(texte);
		}
	}

	
	public static String convert(InputStream is) {
	    String line = "";
	    StringBuilder builder = new StringBuilder();
	    
	    BufferedReader rd=new BufferedReader(new InputStreamReader(is));
 
	    try {
			while ((line = rd.readLine()) != null) { 
			    builder.append(line); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
 
	    return builder.toString();
	}
}
