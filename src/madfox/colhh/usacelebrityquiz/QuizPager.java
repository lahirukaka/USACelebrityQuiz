package madfox.colhh.usacelebrityquiz;

import madfox.colhh.usacelebrityquiz.Calculation.CalculationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class QuizPager extends Fragment implements CalculationListener {

	ViewPager pager;
	
	private SparseArray<QuesPage> quespages;
	private Questionnaire quiz_object;
	MainActivity activity;
	
	int answered;
	int skipped;
	
	public QuizPager() {
	}
	
	public QuizPager(Calculation cal,MainActivity activity) {
		quespages=new SparseArray<QuesPage>();
		this.quiz_object=Questionnaire.getInstance();
		this.activity=activity;
		cal.registerCalculationListener(this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_ques_pager, container,false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
				
		pager=(ViewPager)getView().findViewById(R.id.questions_pager);
		initQuesPages();
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		pager=null; quespages=null;
	}
	
	private void initQuesPages()
	{
		for(int r=0;r<Constants.NUMBER_OF_QUESTIONS;r++)
		{
			quespages.append(r, new QuesPage(this)
					.setPageID(r+1)
					.setQues(quiz_object.getQues(r))
					.setAnswers(quiz_object.getAnswers(r))
					.setCorrect(quiz_object.getCorrects(r)));
		}
		pager.setAdapter(new QuizPagerAdapter(getActivity().getSupportFragmentManager(),quespages,this));
	}
	
	/*Interface*/
	QuesListener QL;
	interface QuesListener
	{
		public void onAnswered(int pageid,boolean correct);
	}
	
	public void registerQuesListener(QuesListener QL)
	{
		this.QL=QL;
	}

	/*Listeners*/
	
	@Override
	public void onAnswered(int qid, int answered, int skipped)
	{
		this.answered=answered;
		this.skipped=skipped;
	}
}
