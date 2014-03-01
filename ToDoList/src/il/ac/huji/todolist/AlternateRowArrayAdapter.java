package il.ac.huji.todolist;
import java.util.ArrayList;

import android.content.Context;   
import android.graphics.Color;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 
 * @author mickey
 *
 */
public class AlternateRowArrayAdapter extends ArrayAdapter<String>{  

	private int[] colors = new int[] { 
			Color.RED, Color.BLUE 
	};  
	  
	public AlternateRowArrayAdapter(Context context, int layout, ArrayList<String> listItems) {  
		super(context, layout, listItems);  
	}
	
	/** 
	 * Display rows in alternating colors 
	 */  
	@Override  
	public View getView(int position, View convertView, ViewGroup parent) {  
		TextView textView = (TextView)super.getView(position, convertView, parent);  
		
		int colorPos = position % colors.length;
		textView.setTextColor(colors[colorPos]);
		
		return textView;  
	}  
}  
