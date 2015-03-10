package madfox.colhh.usacelebrityquiz;

import java.util.ArrayList;

import madfox.colhh.madfoxcustom.httpclients.HttpClientMadfox;
import madfox.colhh.madfoxcustom.httpclients.HttpClientMadfox.OnReceiveListener;
import madfox.colhh.saleslib.feedback.AppRatings;
import madfox.colhh.usacelebrityquiz.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public final class QuizLogics {

	/**
	 * <b>Do transform a v4.Fragment</b>
	 * @param fm
	 * @param frag
	 * @param container
	 * @param tag
	 * @param transformstyle
	 * @param backstack
	 */
	public static void transformFragment(FragmentManager fm,Fragment frag,int container,String tag,
			int transformstyle,boolean backstack)
	{
		FragmentTransaction ft=fm.beginTransaction();
		ft.replace(container, frag, tag)
			.setTransitionStyle(transformstyle);
		if(backstack)ft.addToBackStack(tag);
		ft.commit();
	}
	/**
	 * <b>Remove Fragments</b>
	 * @param fm
	 * @param frag
	 * @param container
	 * @param style
	 */
	public static void removeFragment(FragmentManager fm,Fragment frag, int container, int style)
	{
		fm.beginTransaction()
			.remove(frag)
			.setTransitionStyle(style)
			.commit();
	}
	/**
	 * <b>Get a Random number</b>
	 * @return
	 */
	/*public static int getRand()
	{
		return new Random().nextInt((Constants.NUMBER_OF_QUESTIONNAIRES - 1)+1) + 1;
	}*/
	
	public static void changeBackColor(View v,int color)
	{
		((TextView)v).setBackgroundColor(color);
	}
	
	public static boolean isLollipop()
	{
		return Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP ? true : false;
	}
	
	@SuppressLint("NewApi")
	public static void setMaterialStyles(Activity activity)
	{
		if(isLollipop())
		{
			activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.d_green));
		}
	}
	
	public static void pressBtnStart(Activity activity, OnReceiveListener ORL)
	{
		Constants.QUIZ_ID = Integer.parseInt((String) new AppRatings(activity).readData(
				Constants.QUIZ_ID_FILE, Constants.QUIZ_ID_FILE_KEY));
		
		getNewJson(activity, ORL,String.valueOf(Constants.QUIZ_ID));
	}
	
	public static void getNewJson(Activity activity, OnReceiveListener ORL,String lastid)
	{
		if(HttpClientMadfox.isConnected(activity))
		{
			HttpClientMadfox http_client = new HttpClientMadfox();
			http_client.setMethod(false);
			ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("AppID",String.valueOf(Constants.APPID))); //adding AppId
			params.add(new BasicNameValuePair("LastQuiz",lastid)); //adding LastQuizId
			try {
				http_client.setOnReceiveListener(ORL);
				http_client.setParamsForPost(params);
				http_client.connectAndGet("http://apptest.byethost17.com/quizlogic/index.php");
			} catch (Exception e) {
				Toast.makeText(activity, "Error Data - " + e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}else
		{
			Toast.makeText(activity, "No Internet Connectivity", Toast.LENGTH_LONG).show();
		}
	}
	
	public static String getJsonStringValue(JSONObject jobj, String element)
	{
		try{
			String array = jobj.getJSONObject(element).toString();
			return array;
		}catch (JSONException e) {
			return null;
		}
	}
	
	
}
