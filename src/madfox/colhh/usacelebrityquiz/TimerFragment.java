package madfox.colhh.usacelebrityquiz;

import madfox.colhh.usacelebrityquiz.R;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TimerFragment extends Fragment implements DialogInterface.OnClickListener {

	private TextView timer;
	private CountDownTimer cdt;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_time_remaining, 
				container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		/*init views*/
		timer=(TextView)getView().findViewById(R.id.chronometer1);

		startCountDown();
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		if(cdt!=null)cdt.cancel();
	}
	
	/*Timer*/
	long counter=Constants.TIME_FOR_ANSWER_IN_MILLS/1000;
	private void startCountDown()
	{
		timer.setText(counter+"");
		timer.setTextColor(Color.WHITE);
		
		cdt=new CountDownTimer(Constants.TIME_FOR_ANSWER_IN_MILLS, Constants.TIME_INTERVAL_FOR_TICK) {
			int col;
			@Override
			public void onTick(long millisUntilFinished) {
				col=(int)(millisUntilFinished/1000)*4;
				
				timer.setTextColor(Color.rgb(255, col, col));
				timer.setText(millisUntilFinished/1000+"");
				if(TEL!=null)TEL.onTick((int)((Constants.TIME_FOR_ANSWER_IN_MILLS-millisUntilFinished)/1000));
			}
			
			@Override
			public void onFinish() {
				timer.setText("00");
				UserNoticeDialogs dialog=new UserNoticeDialogs("Time is up", 
						R.drawable.dialog_information, "You have to submit");
				dialog.setButtons(true, false, new String[] {"Submit"}, TimerFragment.this);
				dialog.show(getActivity().getSupportFragmentManager(), "submit");
			}
		}.start();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		((MainActivity)getActivity()).onSubmit();
	}
	
	/*interface*/
	TimeElapsedListener TEL;
	interface TimeElapsedListener
	{
		public void onTick(int seconds);
	}
	
	public void registerTimeElapsedListener(TimeElapsedListener TEL)
	{
		this.TEL=TEL;
	}
}
