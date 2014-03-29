package il.ac.huji.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TodoTaskSQLiteHelper extends SQLiteOpenHelper {

	private static final String TABLE_TASKS = "todo_tasks";
	private static final String COLUMN_ID = "_id";
	private static final String COLUMN_TITLE = "title";
	private static final String COLUMN_DUE = "due";

	private static final String DATABASE_NAME = "todo_db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	private static final String DATABASE_CREATE = 
			"CREATE TABLE " + TABLE_TASKS
			+ "(" 
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_TITLE + " TEXT,"
			+ COLUMN_DUE + " TEXT"
			+ ");";

	public TodoTaskSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
		Log.i(TodoTaskSQLiteHelper.class.getName(), 
				"Creating database");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TodoTaskSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
		onCreate(db);
	}

	/* All CRUD(Create, Read, Update, Delete) Operations */

	/**
	 * A a new task to the database.
	 * @param task The task to add.
	 */
	public void addTask(TodoTask task) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_TITLE, task.getTitle()); 	// task title
		values.put(COLUMN_DUE, task.getDue()); // task due

		// inserting row
		db.insert(TABLE_TASKS, null, values);
		db.close(); // closing database connection

		Log.i(TodoTaskSQLiteHelper.class.getName(),
				"Inserting new task: " + task.getTitle() + " due: " + task.getDue());
	}

	/**
	 * Get a task with an id.
	 * @param id The id of the task as it appears in the database.
	 * @return The TodoTask with the corresponding id.
	 */
	public TodoTask getTask(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(
				TABLE_TASKS, 
				new String[] {COLUMN_ID, COLUMN_TITLE, COLUMN_DUE },
				COLUMN_ID + "=?",
				new String[] {String.valueOf(id)}, 
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		return new TodoTask(
				cursor.getInt(0), // id
				cursor.getString(1), // title
				cursor.getLong(2) // due date
				);
	}

	/**
	 * Create a Cursor for the whole task table (to be used with CursorAdapter).
	 * @return A Cursor which handles the the task table.
	 */
	public Cursor getAllTasks() {
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_TASKS;

		SQLiteDatabase db = this.getWritableDatabase();
		return db.rawQuery(selectQuery, null);
	}

	/**
	 * Delete a task with an id.
	 * @param id The id of the task as it appears in the database.
	 */
	public void deleteTask(int id){
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(TABLE_TASKS, COLUMN_ID + " = ?",
				new String[] { String.valueOf(id) });
		db.close();

		Log.i(TodoTaskSQLiteHelper.class.getName(), 
				"Deleted task with id: " + id);
	}
}
