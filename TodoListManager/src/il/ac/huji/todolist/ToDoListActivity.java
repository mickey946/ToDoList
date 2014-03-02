package il.ac.huji.todolist;

import il.ac.huji.todolist.DeleteTaskDialog.DeleteTaskDialogListener;

import java.util.ArrayList;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

/**
 * To Do list activity. A user can add a task to the end of a list (which is presented
 * with alternating colors).
 * With a long press on a task, the user would be asked to delete it (or not). 
 * 
 * @author mickey
 */
public class ToDoListActivity extends Activity implements DeleteTaskDialogListener {

	ArrayAdapter<String> _adapter;
	ArrayList<String> _listItems = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do_list);

		_adapter = new AlternateRowArrayAdapter(this, R.layout.todo_task, _listItems);

		ListView listToDoTask = (ListView) findViewById(R.id.list_todo_tasks);
		listToDoTask.setAdapter(_adapter);
		listToDoTask.setOnItemLongClickListener(new OnItemLongClickListener() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@SuppressLint("NewApi")
			public boolean onItemLongClick(AdapterView<?> parent, View child, int pos, long id) {
				DeleteTaskDialog deleteTaskDialog = new DeleteTaskDialog(pos);
				deleteTaskDialog.show(getFragmentManager(), "deleteTask");
				return true;
			}
		}); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_add_todo_task:
			addTask();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void addTask() {
		EditText editAddToDoTask = (EditText) findViewById(R.id.edit_add_todo_task);
		String task = editAddToDoTask.getText().toString();

		// add the item
		if(task != null) {
			if(task.length() > 0) {
				_listItems.add(task);
				_adapter.notifyDataSetChanged();
			}
		}

		// remove the text from the edit view
		editAddToDoTask.setText(null);
	}

	@Override
	public void onDialogPositiveClick(DeleteTaskDialog dialog) {
		_listItems.remove(dialog.getPos());
		_adapter.notifyDataSetChanged();
	}

	@Override
	public void onDialogNegativeClick(DeleteTaskDialog dialog) {
		// ignore
	}
}
