package madfox.colhh.usacelebrityquiz;

import madfox.colhh.madfoxcustom.httpclients.HttpClientMadfox;
import madfox.colhh.madfoxcustom.httpclients.HttpClientMadfox.OnReceiveListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ResultQuiz extends Fragment implements OnClickListener,OnReceiveListener {

	private Calculation cal;
	private MainActivity activity;
	
	private TextView marks, time_elapsed, grade;
	private Button tryagain, startnew, share;
	
	private String SHARE_DATA;
	
	public ResultQuiz() {
	}
	
	public ResultQuiz(Calculation cal,MainActivity activity) {
		this.cal=cal;
		this.activity=activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*restore*/
		if(savedInstanceState!=null)
		{
			cal=(Calculation) savedInstanceState.getSerializable("cal");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_result, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		SHARE_DATA="I got " + cal.calculateMarks() + " Marks within " + 
				cal.getTimeElapsed() + " sec by answering " + 
				activity.getResources().getString(R.string.category_title) + 
				" Quesionnaire. \nVia : " + Constants.APP_URL;

		/*init views*/
		View v=getView();
		marks=(TextView)v.findViewById(R.id.result_marks);
		time_elapsed=(TextView)v.findViewById(R.id.time);
		grade=(TextView)v.findViewById(R.id.grade);
		tryagain=(Button)v.findViewById(R.id.btn_retry);
		startnew=(Button)v.findViewById(R.id.btn_new);
		share=(Button)v.findViewById(R.id.btn_share);
		
		share.setOnClickListener(this);
		tryagain.setOnClickListener(this);
		startnew.setOnClickListener(this);
		
		marks.setText(cal.calculateMarks()+"");
		time_elapsed.setText(cal.getTimeElapsed()+" Seconds");
		grade.setText("GRADE " + cal.getGrade());
		cal.destroyObject();	cal=null;
		
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("cal", cal);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.result_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
			case R.id.publish_item:
				new ShareKit(getActivity())
				.setType("text/plain")
				.setData(SHARE_DATA)
				.setTitle("Share Result")
				.shareChooser();
				break;
			case R.id.try_item:
				activity.startQuiz(true,"");
				break;
			case R.id.startnew_item:
				QuizLogics.pressBtnStart(getActivity(), this);
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/*Listeners*/
	@Override
	public void onClick(View v) {
		int id=v.getId();
		if(id==R.id.btn_retry)
		{
			activity.startQuiz(true,"");
		}else if(id==R.id.btn_new)
		{
			QuizLogics.pressBtnStart(getActivity(), this);
			
		}else if(id==R.id.btn_share)
		{
			new ShareKit(getActivity())
			.setType("text/plain")
			.setData(SHARE_DATA)
			.setTitle("Share Result")
			.shareChooser();
		}
	}

	@Override
	public void onReceive(HttpClientMadfox client) {
		try {
			String json = client.readAsString(1000);
			((MainActivity)getActivity()).startQuiz(false,json);
		} catch (Exception e) {
			Toast.makeText(getActivity(), "Data Read Error" + e.getClass() , Toast.LENGTH_LONG).show();
		}
	}
}
