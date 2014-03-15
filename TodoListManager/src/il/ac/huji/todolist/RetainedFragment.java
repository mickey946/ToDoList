package il.ac.huji.todolist;

import android.app.Fragment;
import android.os.Bundle;

public class RetainedFragment<T> extends Fragment {

    // data object we want to retain
    private T _data;

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public void setData(T data) {
        this._data = data;
    }

    public T getData() {
        return _data;
    }
}
