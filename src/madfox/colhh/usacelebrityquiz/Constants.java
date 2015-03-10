package madfox.colhh.usacelebrityquiz;

import madfox.colhh.saleslib.Packages;
import android.net.Uri;

public final class Constants {
	
	/*Questions Constants*/
	public static final int NUMBER_OF_QUESTIONNAIRES=5;
	public static final int NUMBER_OF_QUESTIONS=5;
	public static final int NUMBER_OF_ANSWERS=4;
	public static final int APPID=1;
	/*Timing Constants*/
	public static final long TIME_FOR_ANSWER_IN_MILLS=60000;
	public static final long TIME_INTERVAL_FOR_TICK=1000;
	
	public static final Uri APP_URL=Uri.parse("http://play.google.com/store/apps/details?id=" + Packages.USACELEB.getPackage());
	
	public static int QUIZ_ID = 0;
	
	public static String QUIZ_ID_FILE = "quizid";
	public static String QUIZ_ID_FILE_KEY= "id";
}
