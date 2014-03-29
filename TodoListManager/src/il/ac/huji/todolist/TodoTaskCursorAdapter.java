package il.ac.huji.todolist;

import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TodoTaskCursorAdapter extends CursorAdapter {
	private int _layout;

	public TodoTaskCursorAdapter(Context context, Cursor c) {
		super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		_layout = R.layout.todo_task;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView taskTitle = (TextView)view.findViewById(R.id.txtTodoTitle);
		taskTitle.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));

		TextView taskDueDate = (TextView)view.findViewById(R.id.txtTodoDueDate);

		Long today = System.currentTimeMillis();
		Long taskDue = cursor.getLong(cursor.getColumnIndex(cursor.getColumnName(2)));
		
		if (taskDue == null) { // should not happen
			taskDueDate.setText(context.getResources().getString(R.string.no_due_date));
		}
		else {
			Date date = new Date(); 
			date.setTime(taskDue);
			String dueDate = DateFormat.getDateFormat(context).format(date);
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
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// when the view will be created for first time, we inflate the row layout
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(_layout, parent, false);
		
		return view;
	}

}
