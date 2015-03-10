package madfox.colhh.usacelebrityquiz;

import android.content.Context;
import android.content.Intent;

public class ShareKit {

	private String type;
	private String data;
	private String title;
	
	private Context context;
	
	public ShareKit(Context context) {
		this.context=context;
	}
	
	public void shareChooser()
	{
		Intent intent=new Intent();
		intent.setAction(Intent.ACTION_SEND);
		/*set data*/
		intent.setType(type);
		intent.putExtra(Intent.EXTRA_TEXT, data);
		
		context.startActivity(Intent.createChooser(intent, title));
	}
	
	/*Getters & Setters*/
	/**
	 * @param type the type to set
	 */
	public final ShareKit setType(String type) {
		this.type = type;
		return this;
	}
	/**
	 * @param data the data to set
	 */
	public final ShareKit setData(String data) {
		this.data = data;
		return this;
	}
	/**
	 * @param title the title to set
	 */
	public final ShareKit setTitle(String title) {
		this.title = title;
		return this;
	}
	
	
}
