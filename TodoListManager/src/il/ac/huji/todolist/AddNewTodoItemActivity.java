package il.ac.huji.todolist;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * Activity with a dialog theme used to add a new task with a due date.
 * 
 * @author mickey
 */
public class AddNewTodoItemActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_todo_item);
	}

	public void cancel(View view) {
		Intent returnIntent = new Intent();
		setResult(Activity.RESULT_CANCELED, returnIntent);        
		finish(); 
	}

	@SuppressLint("NewApi")
	public void add(View view) {
		TextView edtTask = (TextView) findViewById(R.id.edtNewItem);
		String task = edtTask.getText().toString();

		DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
		long date = datePicker.getCalendarView().getDate();

		Intent returnIntent = new Intent();
		returnIntent.putExtra("task", task);
		returnIntent.putExtra("date", date);
		setResult(Activity.RESULT_OK, returnIntent);  
		finish();
	}

}
