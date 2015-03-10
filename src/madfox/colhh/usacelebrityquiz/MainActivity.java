package madfox.colhh.usacelebrityquiz;

import madfox.colhh.saleslib.Packages;
import madfox.colhh.saleslib.adsdisplayer.AppPager;
import madfox.colhh.saleslib.feedback.AppRatings;
import madfox.colhh.saleslib.pages.SplashPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements
		DialogInterface.OnClickListener {

	private Toolbar toolbar;
	
	private FragmentManager fm;
	private TimerFragment timer_frag;
	private QuizPager quizpager_frag;
	
	private Calculation cal;
	int random = 0;

	private UserNoticeDialogs UND;
	private Questionnaire quiz_object;

	private TableRow frameads;
	private FrameLayout frametime;

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/* init views */
		toolbar=(Toolbar) findViewById(R.id.toolbar);
		frameads = (TableRow) findViewById(R.id.main_frag_ads);
		frametime = (FrameLayout) findViewById(R.id.main_frag_time);
		
		fm = getSupportFragmentManager();
		setSupportActionBar(toolbar);
		
		if(savedInstanceState==null)
		{
			new AppRatings(this).increaseViews();
			new SplashPage().show(getSupportFragmentManager(), 
					"splash");
			initQuiz();
		}else
		{
			frametime.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		int entries = getSupportFragmentManager().getBackStackEntryCount();
		if (entries > 0) {
			UND = new UserNoticeDialogs("Stop Quiz",
					R.drawable.dialog_question, "Stop Current Questionnaire ?");
			UND.setButtons(true, true, new String[] { "Yes", "No" }, this);
			UND.show(getSupportFragmentManager(), "stopcurrentquiz");
		} else {
			super.onBackPressed();
		}
	}

	/**
	 * <b>Inits quiz</b>
	 */
	public void initQuiz() {
		StartQuiz.QUIZ_STARTED = false;
		frametime.setVisibility(View.GONE);
		frameads.setVisibility(ViewPager.VISIBLE);
		QuizLogics.transformFragment(fm, new StartQuiz(), R.id.main_frag_body,
				"startfrag", FragmentTransaction.TRANSIT_FRAGMENT_OPEN, false);
		QuizLogics.transformFragment(fm, new AppPager(0,Packages.JOKES_SENDER,
				Packages.QUOTES_SENDER, Packages.BD_WISHES_SENDER, Packages.SSP,
				Packages.XMAS_SENDER).setRotateTime(3000),
				R.id.main_frag_ads, "ads",
				FragmentTransaction.TRANSIT_FRAGMENT_CLOSE, false);
	}

	/**
	 * <b>Starts Quiz</b>
	 */
	public Void startQuiz(boolean retry,String json) {
		StartQuiz.QUIZ_STARTED = true;
		frametime.setVisibility(View.VISIBLE);
		frameads.setVisibility(ViewPager.GONE);
		QuizLogics.transformFragment(fm, timer_frag = new TimerFragment(),
				R.id.main_frag_time, "timerfrag",
				FragmentTransaction.TRANSIT_FRAGMENT_OPEN, true);
		
		if (!retry)
		{
			if(json.isEmpty()) 
			{
				Toast.makeText(this, "Internal Error", Toast.LENGTH_LONG).show();
				return null;
			}
			
			String questions[] = null;
			int correct[] = null;
			String answers[][] = null;
			JSONObject jobj = null;
			
			try {
				JSONArray jsona = new JSONArray(json);
				questions = new String[jsona.length()];
				correct = new int[jsona.length()];
				answers = new String[jsona.length()][4];
				
				for(int r=0;r<jsona.length();r++)
				{
					jobj = jsona.getJSONObject(r);
					questions[r] = jobj.getString("Question");
					correct[r] = jobj.getInt("Correct");
					JSONArray ans = jobj.getJSONArray("Answers");
					for(int a=0;a<4;a++){answers[r][a] = ans.getString(a);}
				}
			} catch (Exception e) {
				if(correct.length<4)
				{
					Toast.makeText(this, "Json Error " + e.getMessage(), Toast.LENGTH_LONG).show();
					return null;
				}
				try {
					Constants.QUIZ_ID = jobj.getInt("ID");
				} catch (JSONException e1) {
					Constants.QUIZ_ID =1 ;
				}finally
				{
					new AppRatings(this).writeData(Constants.QUIZ_ID_FILE, Constants.QUIZ_ID_FILE_KEY, 
							Constants.QUIZ_ID + "");
				}
			}
			quiz_object = Questionnaire.getInstance();
			quiz_object
				.setQues(questions)
				.setAnswers(answers)
				.setCorrects(correct);
		}		

		/* prepare questionnaires object */
		if (cal != null)
			cal = null;
		cal = new Calculation();
		
		QuizLogics.transformFragment(fm, quizpager_frag = new QuizPager(
				cal, this), R.id.main_frag_body, "quizpager",
				FragmentTransaction.TRANSIT_FRAGMENT_OPEN, false);
		quizpager_frag.registerQuesListener(cal);
		timer_frag.registerTimeElapsedListener(cal);

		frameads.setVisibility(View.GONE);
		return null;
	}

	public void onSubmit() {
		StartQuiz.QUIZ_STARTED = false;
		QuizLogics.removeFragment(fm, timer_frag, R.id.main_frag_time,
				FragmentTransaction.TRANSIT_EXIT_MASK);
		QuizLogics.transformFragment(fm, new ResultQuiz(cal, this),
				R.id.main_frag_body, "resultfrag",
				FragmentTransaction.TRANSIT_FRAGMENT_FADE, false);
		fm.popBackStackImmediate();
		frameads.setVisibility(View.VISIBLE);
		frametime.setVisibility(View.GONE);
		/* Mem free */
		timer_frag = null;
		quizpager_frag = null;
	}

	/* Listeners */
	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {
		case DialogInterface.BUTTON_POSITIVE:
			getSupportFragmentManager().popBackStackImmediate();

			if (timer_frag != null)
				timer_frag = null;
			if (quiz_object != null)
				quiz_object = null;
			if (quizpager_frag != null)
				quizpager_frag = null;
			if (UND != null)
				UND = null;
			initQuiz();
			break;
		}
	}
}