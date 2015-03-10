package madfox.colhh.usacelebrityquiz;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

public class QuizPagerAdapter extends FragmentStatePagerAdapter {

	private SparseArray<QuesPage> pages;
	private QuizPager pager;
	
	public QuizPagerAdapter(FragmentManager fm,SparseArray<QuesPage> pages, QuizPager pager) {
		super(fm);
		this.pages=pages;
		this.pager=pager;
	}

	@Override
	public Fragment getItem(int position) {
		if(position < Constants.NUMBER_OF_QUESTIONS)
			return pages.get(position);
		else return new ReviewPage(pager);
	}

	@Override
	public int getCount() {
		return (Constants.NUMBER_OF_QUESTIONS + 1);
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		if(position < Constants.NUMBER_OF_QUESTIONS) 
			return "Question " + (position + 1);
		else return "Review";
	}

}
