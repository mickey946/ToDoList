package il.ac.huji.todolist;
import java.util.ArrayList;

import android.content.Context;   
import android.graphics.Color;  
import android.view.LayoutInflater;
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * A custom array adapter for presenting the list with alternating color text.
 * 
 * @author mickey
 */
public class AlternateRowArrayAdapter extends ArrayAdapter<String>{  

	private int[] colors = new int[] { 
			Color.RED, Color.BLUE 
	};
	
	private ArrayList<String> _listItems;

	public AlternateRowArrayAdapter(Context context, int layout, ArrayList<String> listItems) {  
		super(context, layout, listItems);
		_listItems = listItems;
	}

	/** 
	 * Display rows in alternating colors 
	 */  
	@Override  
	public View getView(int position, View convertView, ViewGroup parent) {  
		//		TextView textView = (TextView)super.getView(position, convertView, parent);  

		LayoutInflater inflater = LayoutInflater.from(getContext());
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.todo_task, parent, false);
			
		}
		
		TextView textView = (TextView)convertView.findViewById(R.id.todo_task_text);
		textView.setText(_listItems.get(position));
		int colorPos = position % colors.length;
		textView.setTextColor(colors[colorPos]);

		return convertView;  
	}  
}  
