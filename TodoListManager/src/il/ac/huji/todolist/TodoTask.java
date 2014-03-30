package il.ac.huji.todolist;

public class TodoTask {
	
	private int _id;
	private String _title;
	private long _due;
	
	public TodoTask() {
	}

	public TodoTask(int id, String title, long due) {
		this._id = id;
		this._title = title;
		this._due = due;
	}
	
	public TodoTask(String title, long due) {
		this._title = title;
		this._due = due;
	}
	
	public TodoTask(String title) {
		this._title = title;
	}

	public int getID() {
		return _id;
	}

	public void setID(int id) {
		this._id = id;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		this._title = title;
	}

	public long getDue() {
		return _due;
	}

	public void setDue(long due) {
		this._due = due;
	}
}
