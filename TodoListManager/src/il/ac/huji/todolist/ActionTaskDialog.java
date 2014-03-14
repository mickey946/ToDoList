package il.ac.huji.todolist;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ListAdapter;

/**
 * Delete task dialog
 *   
 * @author mickey
 */
@SuppressLint({ "NewApi", "ValidFragment" })
public class ActionTaskDialog extends DialogFragment {

	private static final int DELETE_POS = 0;
	private static final int CALL_POS = 1;

	private int _pos; // position of the task in the list
	private String _task; // the long-pressed task

	@SuppressLint("ValidFragment")
	public ActionTaskDialog(int pos, String task) {
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
		public void onDialogPositiveClick(ActionTaskDialog dialog);
		public void onDialogNegativeClick(ActionTaskDialog dialog);
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
		ArrayList<String> actions = new ArrayList<String>();
		ArrayList<Integer> images = new ArrayList<Integer>();

		actions.add(getResources().getString(R.string.dialog_delete_list_item));
		images.add(R.drawable.ic_action_discard);

		if(_task.startsWith(getResources().getString(R.string.dialog_call_list_item))) {
			actions.add(_task);
			images.add(R.drawable.ic_action_call);
		}

		ListAdapter adapter; // list adapter for the dialog
		adapter = new RowWithIconArrayAdapter(getActivity(), actions, images);

		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(_task)
		.setAdapter(adapter, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which == DELETE_POS) {
					_listener.onDialogPositiveClick(ActionTaskDialog.this);
				}
				else if (which == CALL_POS) {
					//TODO create call intent
				}
			}
		})
		.setNegativeButton(R.string.dialog_cancel_button, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User cancelled the dialog
				_listener.onDialogNegativeClick(ActionTaskDialog.this);
			}
		});
		// Create the AlertDialog object and return it
		return builder.create();
	}
}
