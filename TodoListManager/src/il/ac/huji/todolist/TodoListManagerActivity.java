package il.ac.huji.todolist;

import il.ac.huji.todolist.ActionTaskDialog.DeleteTaskDialogListener;

import java.util.ArrayList;
import java.util.Date;

//import com.parse.Parse;





import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

/**
 * To Do list activity. A user can add a task to the end of a list (which is presented
 * with alternating colors).
 * With a long press on a task, the user would be asked to delete it (or not). 
 * 
 * @author mickey
 */
public class TodoListManagerActivity extends Activity implements DeleteTaskDialogListener {

	//	private TodoTaskCursorAdapter _adapter;
	private TodoTaskArrayAdapter _adapter;
	private TodoTaskSQLiteHelper _helper;
	private ActionTaskDialog _actionTaskDialog;
	private ListView _listToDoTask;

	private class TodoTaskSaver extends AsyncTask<TodoTask, Void, TodoTask[]> {

		@Override
		protected TodoTask[] doInBackground(TodoTask... tasks) {
			for(TodoTask task : tasks) {
				Long id = _helper.addTask(task);
				task.setID(id);
			}
			return tasks;
		}

		@Override
		protected void onPostExecute(TodoTask[] tasks) {
			for (TodoTask task : tasks)
				_adapter.add(task);
			_adapter.notifyDataSetChanged();
		}

	}

	private class TodoTaskDeleter extends AsyncTask<TodoTask, Void, TodoTask[]> {

		@Override
		protected TodoTask[] doInBackground(TodoTask... tasks) {
			for (TodoTask task : tasks) {
				_helper.deleteTask(task.getID());
			}
			return tasks;
		}

		@Override
		protected void onPostExecute(TodoTask[] tasks) {
			for (TodoTask task : tasks)
				_adapter.remove(task);
			_adapter.notifyDataSetChanged();	
		}

	}

	private class TodoTaskLoader extends AsyncTask<Cursor, TodoTask, Void> {

		@Override
		protected Void doInBackground(Cursor... cursors) {
			Cursor current = cursors[0];
			while (current.moveToNext()) {
				Long id = current.getLong(current.getColumnIndex(TodoTaskSQLiteHelper.COLUMN_ID));
				Long due = current.getLong(current.getColumnIndex(TodoTaskSQLiteHelper.COLUMN_DUE));
				String title = current.getString(current.getColumnIndex(TodoTaskSQLiteHelper.COLUMN_TITLE));

				TodoTask task = new TodoTask(id, title, due);

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) { 
					// well, shit
				}

				publishProgress(task);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(TodoTask... tasks) {
			for (TodoTask task : tasks){
				_adapter.add(task);
			}
			_adapter.notifyDataSetChanged();
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do_list);

		// Initialize databases
		_helper = TodoTaskSQLiteHelper.getInstanse(this);
		//		Parse.initialize(this, "z6oaJ0v3LcRPapj4w3ZDJS7ImSl9beyDlATdGjtZ", 
		//				"seCevpBGpkvBEmuUZyCHHaJAAqHGVitVwkw2dEEI");

		_listToDoTask = (ListView) findViewById(R.id.list_todo_tasks);

		_adapter = new TodoTaskArrayAdapter(getApplicationContext(), 
				new ArrayList<TodoTask>());
		_listToDoTask.setAdapter(_adapter);

		new Handler().post(new Runnable() {

			@Override
			public void run() {	
				TodoTaskLoader loader = new TodoTaskLoader();
				loader.execute(_helper.getAllTasks());
			}

		});

		_listToDoTask.setOnItemLongClickListener(new OnItemLongClickListener() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@SuppressLint("NewApi")
			public boolean onItemLongClick(AdapterView<?> parent, View child, int pos, long id) {
				TodoTask task = _adapter.getItem(pos);
				_actionTaskDialog = new ActionTaskDialog(pos, task);
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
		Intent intent = new Intent(this, AddNewTodoTaskActivity.class);
		startActivityForResult(intent, TASK_N_DATE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// If the request went well (OK) and the request was TASK_N_DATE
		if (resultCode == Activity.RESULT_OK && requestCode == TASK_N_DATE) {
			String task = data.getStringExtra("title");
			Date ddate = (Date) data.getSerializableExtra("dueDate");
			long date = ddate.getTime();

			// add the item
			if(task != null) {
				if(task.length() > 0) {
					TodoTaskSaver saver = new TodoTaskSaver();
					saver.execute(new TodoTask(task, date));
				}
			}
		}
	}

	@Override
	public void onDialogPositiveClick(ActionTaskDialog dialog) {
		TodoTaskDeleter deleter = new TodoTaskDeleter();
		TodoTask task = _adapter.getItem(dialog.getPos());
		deleter.execute(task);
	}

	@Override
	public void onDialogNegativeClick(ActionTaskDialog dialog) {
		// ignore
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
