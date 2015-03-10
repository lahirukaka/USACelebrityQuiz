 package madfox.colhh.usacelebrityquiz;

import madfox.colhh.usacelebrityquiz.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ReviewPage extends Fragment implements OnPageChangeListener, OnClickListener {

	private TextView nuof_questions,nuof_answered,nuof_skipped;
	private Button submit;
	
	private QuizPager pager;
	
	public ReviewPage() {
	}
	
	public ReviewPage(QuizPager pager) {
		this.pager=pager;
		this.pager.pager.setOnPageChangeListener(this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_review, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		/*init views*/
		View v=getView();
		nuof_questions=(TextView)v.findViewById(R.id.review_nu_quez);
		nuof_answered=(TextView)v.findViewById(R.id.review_nu_answered);
		nuof_skipped=(TextView)v.findViewById(R.id.review_nu_skip);
		submit=(Button)v.findViewById(R.id.submit);
		submit.setOnClickListener(this);
		
		nuof_questions.setText(Constants.NUMBER_OF_QUESTIONS+"");
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.review_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
			case R.id.submit_item:
				pager.activity.onSubmit();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPause() {
		super.onPause();
		nuof_answered=null;
		nuof_questions=null;
		nuof_skipped=null;
		submit=null;
	}
	
	/*Listeners*/
	
	@Override
	public void onPageScrollStateChanged(int arg0) {	
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int position) {
		if(position==Constants.NUMBER_OF_QUESTIONS)
		{
			nuof_answered.setText(pager.answered+"");
			nuof_skipped.setText(pager.skipped+"");
		}
	}

	@Override
	public void onClick(View v) {
		pager.activity.onSubmit();
	}
	
}