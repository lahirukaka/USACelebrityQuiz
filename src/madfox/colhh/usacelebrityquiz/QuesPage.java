package madfox.colhh.usacelebrityquiz;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public final class QuesPage extends Fragment implements OnTouchListener, OnClickListener {

	private TextView title, answer1, answer2, answer3, answer4;
	private Button skip;
	
	private int pageid;
	private String ques;
	private String[] answers;
	private int correct;
	
	private Resources res;
	private QuizPager pager;
	private ViewPager vpager;
	
	public QuesPage() {
	}
	
	public QuesPage(QuizPager pager) {
		this.pager=pager;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_questions, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		View v=getView();
		title=(TextView)v.findViewById(R.id.question_title);
		answer1=(TextView)v.findViewById(R.id.answer1);
		answer2=(TextView)v.findViewById(R.id.answer2);
		answer3=(TextView)v.findViewById(R.id.answer3);
		answer4=(TextView)v.findViewById(R.id.answer4);
		
		answer1.setOnTouchListener(this);
		answer2.setOnTouchListener(this);
		answer3.setOnTouchListener(this);
		answer4.setOnTouchListener(this);
		
		skip=(Button)v.findViewById(R.id.btn_skip);
		skip.setOnClickListener(this);
		
		v=null;
		res=getResources();
		setProperties();
		setHasOptionsMenu(true);
		((ActionBarActivity)getActivity()).supportInvalidateOptionsMenu();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.ques_page, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		vpager.setCurrentItem(vpager.getCurrentItem() + 1, true);
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		vpager=pager.pager;
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		title=null;	answer1=null;	answer2=null;
		answer3=null;	answer4=null;	skip=null;
		vpager=null;
		//pager=null;
	}

	private void setProperties() {
		title.setText(ques);
		//Log.w("ques", ques);
		answer1.setText(answers[0]);
		answer2.setText(answers[1]);
		answer3.setText(answers[2]);
		answer4.setText(answers[3]);
	}

	/*Getters & Setters*/
	
	/**
	 * @param id
	 */
	public final QuesPage setPageID(int id)
	{
		pageid=id;
		return this;
	}
	
	/**
	 * @param ques the ques to set
	 */
	public final QuesPage setQues(String ques) {
		this.ques = ques;
		return this;
	}

	/**
	 * @param answers the answers to set
	 */
	public final QuesPage setAnswers(String[] answers) {
		this.answers = answers;
		return this;
	}

	/**
	 * @param correct the correct to set
	 */
	public final QuesPage setCorrect(int correct) {
		this.correct = correct;
		return this;
	}

	/*Listeners*/
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_DOWN)
		{
			QuizLogics.changeBackColor(v, res.getColor(R.color.l_green));
			return true;
		}else if(event.getAction()==MotionEvent.ACTION_UP)
		{  
			v.performClick();
			QuizLogics.changeBackColor(v, res.getColor(android.R.color.transparent));
			boolean correctness=false;
			int id=v.getId();
			if(id==R.id.answer1)
			{
					if(correct==1)correctness=true;
					pager.QL.onAnswered(pageid, correctness);
			}else if(id==R.id.answer2)
			{
					if(correct==2)correctness=true;
					pager.QL.onAnswered(pageid, correctness);
			}else if(id==R.id.answer3)
			{
					if(correct==3)correctness=true;
					pager.QL.onAnswered(pageid, correctness);
			}else if(id==R.id.answer4)
			{
					if(correct==4)correctness=true;
					pager.QL.onAnswered(pageid, correctness);
			}else
			{
				return false;
			}
			
			/*swipe to next page*/
			vpager.setCurrentItem(vpager.getCurrentItem() + 1, true);
			return true;
		}else
		{
			QuizLogics.changeBackColor(v, res.getColor(android.R.color.transparent));
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btn_skip)
		{
			vpager.setCurrentItem(vpager.getCurrentItem() + 1, true);
		}
	}
}
