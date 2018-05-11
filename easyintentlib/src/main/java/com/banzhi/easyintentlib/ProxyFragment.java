package com.banzhi.easyintentlib;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/5/10.
 * @desciption :中介fragment
 * @version :
 * </pre>
 */

public class ProxyFragment extends Fragment {

    SparseArray<OnActivityResultCallback> mCallbacks = new SparseArray<>();

    public static ProxyFragment newInstance() {
        ProxyFragment fragment = new ProxyFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void start(Intent intent, int requestCode, OnActivityResultCallback callback) {
        mCallbacks.put(requestCode, callback);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            OnActivityResultCallback callback = mCallbacks.get(requestCode);
            mCallbacks.remove(requestCode);
            if (callback != null) {
                callback.onActivityResultCallback(requestCode, data);
            }
        }
    }
}
