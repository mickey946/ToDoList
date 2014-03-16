package il.ac.huji.todolist;

import il.ac.huji.todolist.ActionTaskDialog.DeleteTaskDialogListener;

import java.util.ArrayList;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * To Do list activity. A user can add a task to the end of a list (which is presented
 * with alternating colors).
 * With a long press on a task, the user would be asked to delete it (or not). 
 * 
 * @author mickey
 */
public class TodoListManagerActivity extends Activity implements DeleteTaskDialogListener {

	private RetainedFragment<ArrayList<Pair<String, Long>>> _dataFragment;
	private ArrayAdapter<Pair<String, Long>> _adapter;
	private ArrayList<Pair<String, Long>> _listItems;
	
	private ActionTaskDialog _actionTaskDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do_list);

		// find the retained fragment on activity restarts
		FragmentManager fm = getFragmentManager();
		@SuppressWarnings("unchecked")
		RetainedFragment<ArrayList<Pair<String, Long>>> findFragmentByTag = 
				(RetainedFragment<ArrayList<Pair<String, Long>>>) fm.findFragmentByTag("dataMain");
		_dataFragment = findFragmentByTag;

		// create the fragment and data the first time
		if (_dataFragment == null) {
			// add the fragment
			_dataFragment = new RetainedFragment<ArrayList<Pair<String, Long>>>();
			fm.beginTransaction().add(_dataFragment, "dataMain").commit();
			// create new list
			_dataFragment.setData(new ArrayList<Pair<String, Long>>());
		}

		_listItems = _dataFragment.getData();
		_adapter = new RowWithDateArrayAdapter(this, _listItems);

		ListView listToDoTask = (ListView) findViewById(R.id.list_todo_tasks);
		listToDoTask.setAdapter(_adapter);
		listToDoTask.setOnItemLongClickListener(new OnItemLongClickListener() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@SuppressLint("NewApi")
			public boolean onItemLongClick(AdapterView<?> parent, View child, int pos, long id) {
				_actionTaskDialog = new ActionTaskDialog(pos, _listItems.get(pos).first);
				_actionTaskDialog.show(getFragmentManager(), "actionTask");
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

	private static final int TASK_N_DATE = 15;

	private void addTask() {
		Intent intent = new Intent(this, AddNewTodoItemActivity.class);
		startActivityForResult(intent, TASK_N_DATE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// If the request went well (OK) and the request was TASK_N_DATE
		if (resultCode == Activity.RESULT_OK && requestCode == TASK_N_DATE) {
			String task = data.getStringExtra("title");
			long date = data.getLongExtra("dueDate", 0);

			// add the item
			if(task != null) {
				if(task.length() > 0) {
					_listItems.add(new Pair<String, Long>(task, date));
					_adapter.notifyDataSetChanged();
				}
			}
		}
	}

	@Override
	public void onDialogPositiveClick(ActionTaskDialog dialog) {
		_listItems.remove(dialog.getPos());
		_adapter.notifyDataSetChanged();
	}

	@Override
	public void onDialogNegativeClick(ActionTaskDialog dialog) {
		// ignore
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		_dataFragment.setData(_listItems);
	}
}
