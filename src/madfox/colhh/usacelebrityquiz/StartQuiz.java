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
import android.widget.Toast;


public class StartQuiz extends Fragment implements OnClickListener, OnReceiveListener {
	
	public static boolean QUIZ_STARTED=false;
	
	/*Objects*/
	private Button btn_start;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_start_quiz, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		/*init views*/
		View view=getView();
		btn_start=(Button)view.findViewById(R.id.btn_start);
		btn_start.setOnClickListener(this);
		
		setHasOptionsMenu(true);
		/*start ads frag*/
		//QuizLogics.transformFragment(getActivity().getSupportFragmentManager(), frag, container, tag, transformstyle, backstack)
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.start_quiz, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
			case R.id.start_new:
				QuizLogics.pressBtnStart(getActivity(), this);
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onClick(View view) {
		if(view.getId()==R.id.btn_start)
		{
			QuizLogics.pressBtnStart(getActivity(), this);
		}
	}

	@Override
	public void onReceive(HttpClientMadfox client) {
		try {
			String json = client.readAsString(1000);
			((MainActivity)getActivity()).startQuiz(false,json);
		} catch (Exception e) {
			Toast.makeText(getActivity(), "Data Receiving Error ", Toast.LENGTH_LONG).show();
		}
	}
}
