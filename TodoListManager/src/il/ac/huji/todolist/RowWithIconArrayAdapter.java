package il.ac.huji.todolist;

import java.util.ArrayList;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RowWithIconArrayAdapter extends ArrayAdapter<String> {

	private ArrayList<Integer> images;
	private int _layout;

	public RowWithIconArrayAdapter(Context context, ArrayList<String> items, ArrayList<Integer> images) {
		super(context, R.layout.todo_task_action, items);
		this._layout = R.layout.todo_task_action;
		this.images = images;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(getContext());

		if (convertView == null) { // new row
			convertView = inflater.inflate(_layout, parent, false);
		}

		TextView textView = (TextView) convertView.findViewById(R.id.todo_task_action_title);
		textView.setText(this.getItem(position));
		textView.setCompoundDrawablesWithIntrinsicBounds(images.get(position), 0, 0, 0);
		textView.setCompoundDrawablePadding(
				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, 
						getContext().getResources().getDisplayMetrics()));
		
		return convertView;
	}
}
