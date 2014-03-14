package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Calendar;

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
	private int _layout;

	public AlternateRowArrayAdapter(Context context, ArrayList<String> listItems) {  
		super(context, R.layout.todo_task, listItems);
		_layout = R.layout.todo_task;
		_listItems = listItems;
	}

	/** 
	 * Display rows in alternating colors 
	 */  
	@Override  
	public View getView(int position, View convertView, ViewGroup parent) {  
		//		TextView textView = (TextView)super.getView(position, convertView, parent);  

		LayoutInflater inflater = LayoutInflater.from(getContext());

		if (convertView == null) { // new row
			convertView = inflater.inflate(_layout, parent, false);
		}

		TextView taskTitle = (TextView)convertView.findViewById(R.id.txtTodoTitle);
		taskTitle.setText(_listItems.get(position));
		int colorPos = position % colors.length;
		taskTitle.setTextColor(colors[colorPos]);

		TextView taskDueDate = (TextView)convertView.findViewById(R.id.txtTodoDueDate);
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DATE),
				mounth = calendar.get(Calendar.MONTH) + 1, // January = 0
				year = calendar.get(Calendar.YEAR);
		taskDueDate.setText(day + "/" + mounth + "/" + year);

		return convertView;  
	}  
}  
