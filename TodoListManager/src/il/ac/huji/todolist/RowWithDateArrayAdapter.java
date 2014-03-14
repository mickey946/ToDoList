package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;   
import android.graphics.Color;  
import android.text.format.DateFormat;
import android.util.Pair;
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
public class RowWithDateArrayAdapter extends ArrayAdapter<Pair<String, Long>>{  

	private ArrayList<Pair<String, Long>> _listItems;
	private int _layout;

	public RowWithDateArrayAdapter(Context context, ArrayList<Pair<String, Long>> listItems) {  
		super(context, R.layout.todo_task, listItems);
		_layout = R.layout.todo_task;
		_listItems = listItems;
	}

	/** 
	 * Display rows in alternating colors 
	 */  
	@Override  
	public View getView(int position, View convertView, ViewGroup parent) {  
		LayoutInflater inflater = LayoutInflater.from(getContext());

		if (convertView == null) { // new row
			convertView = inflater.inflate(_layout, parent, false);
		}

		TextView taskTitle = (TextView)convertView.findViewById(R.id.txtTodoTitle);
		taskTitle.setText(_listItems.get(position).first);

		TextView taskDueDate = (TextView)convertView.findViewById(R.id.txtTodoDueDate);

		Long today = System.currentTimeMillis(), taskDue = _listItems.get(position).second;

		if (taskDue == null) { // should not happen
			taskDueDate.setText(getContext().getResources().getString(R.string.no_due_date));
		}
		else {
			Date date = new Date(); 
			date.setTime(taskDue);
			String dueDate = DateFormat.getDateFormat(getContext()).format(date);
			taskDueDate.setText(dueDate);
		}
		

		if (today - taskDue > 0) { // over due
			taskTitle.setTextColor(Color.RED);
			taskDueDate.setTextColor(Color.RED);
		}
		else {
			taskTitle.setTextColor(Color.BLACK);
			taskDueDate.setTextColor(Color.BLACK);
		}

		return convertView;  
	}  
}  
