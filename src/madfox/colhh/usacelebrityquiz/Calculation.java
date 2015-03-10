package madfox.colhh.usacelebrityquiz;

import java.io.Serializable;
import java.util.HashMap;

import madfox.colhh.usacelebrityquiz.QuizPager.QuesListener;
import madfox.colhh.usacelebrityquiz.TimerFragment.TimeElapsedListener;
import android.annotation.SuppressLint;

public final class Calculation implements QuesListener, TimeElapsedListener, Serializable {

	private static final long serialVersionUID = 4869493399298326532L;

	private HashMap<Integer, Boolean> map;
	
	private int allques=Constants.NUMBER_OF_QUESTIONS;
	private int answered=0;
	private int skipped=allques;
	private int time_elapsed=0;
	private int marks=0;
	
	@SuppressLint("UseSparseArrays") public Calculation() {
		map=new HashMap<Integer, Boolean>(allques);
	}
	
	public void destroyObject()
	{
		map=null;
		answered=0;
		time_elapsed=0;
		marks=0;
	}
	
	@Override
	public void onAnswered(int pageid, boolean correct) {
		if(map.put(pageid, correct)==null)
		{
			answered++;
			skipped--;
		}
		if(Cal!=null)Cal.onAnswered(pageid, answered, skipped);
	}
	
	/**
	 * <b>Calculate Marks</b>
	 * @return Marks
	 */
	public short calculateMarks()
	{
		short m=0;
		for(int r=1;r<=allques;r++)
		{
			try{
				if(map.get(r))
				{
					m+=(100/allques);
				}
			}catch (NullPointerException e) {}
		}
		marks=m;
		return m;
	}
	/**
	 * <b>Time Elapsed</b>
	 * @return Time in seconds
	 */
	public int getTimeElapsed()
	{
		return time_elapsed;
	}
	
	public String getGrade()
	{
		if(marks>=80)
		{
			return "EXCELLENT";
		}else if(marks>=60)
		{
			return "GOOD";
		}else if(marks>=40)
		{
			return "AVERAGE";
		}else
		{
			return "BAD";
		}
	}
	
	/*Listeners*/
	@Override
	public void onTick(int seconds) {
		time_elapsed=seconds;
	}
	
	/*interface stuff*/
	CalculationListener Cal;
	interface CalculationListener
	{
		public void onAnswered(int qid, int answered, int skipped);
	}
	
	public void registerCalculationListener(CalculationListener Cal)
	{
		this.Cal=Cal;
	}
}
