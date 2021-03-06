package il.ac.huji.todolist;

import java.util.Date;

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
public class AddNewTodoTaskActivity extends Activity {

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
		Date date = new Date(datePicker.getCalendarView().getDate());

		Intent returnIntent = new Intent();
		returnIntent.putExtra("title", task);
		returnIntent.putExtra("dueDate", date);
		setResult(Activity.RESULT_OK, returnIntent);  
		finish();
	}

}
