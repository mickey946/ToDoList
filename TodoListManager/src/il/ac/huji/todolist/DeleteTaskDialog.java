package il.ac.huji.todolist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Delete task dialog
 *   
 * @author mickey
 */
@SuppressLint({ "NewApi", "ValidFragment" })
public class DeleteTaskDialog extends DialogFragment {

	private int _pos; // position of the task in the list
	private String _task; // the long-pressed task

	@SuppressLint("ValidFragment")
	public DeleteTaskDialog(int pos, String task) {
		super();
		_pos = pos;
		_task = task;
	}

	public int getPos() {
		return _pos;
	}

	/* The activity that creates an instance of this dialog fragment must
	 * implement this interface in order to receive event callbacks.
	 * Each method passes the DialogFragment in case the host needs to query it. */
	public interface DeleteTaskDialogListener {
		public void onDialogPositiveClick(DeleteTaskDialog dialog);
		public void onDialogNegativeClick(DeleteTaskDialog dialog);
	}

	// Use this instance of the interface to deliver action events
	DeleteTaskDialogListener _listener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try {
			// Instantiate the NoticeDialogListener so we can send events to the host
			_listener = (DeleteTaskDialogListener) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement DeleteTaskDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.dialog_delete_todo_task)
		.setTitle(_task)
		.setIcon(R.drawable.ic_action_discard)
		.setPositiveButton(R.string.dialog_delete_button, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// FIRE ZE MISSILES!
				_listener.onDialogPositiveClick(DeleteTaskDialog.this);
			}
		})
		.setNegativeButton(R.string.dialog_cancel_button, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User cancelled the dialog
				_listener.onDialogNegativeClick(DeleteTaskDialog.this);
			}
		});
		// Create the AlertDialog object and return it
		return builder.create();
	}
}
