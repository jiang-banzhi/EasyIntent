package com.banzhi.easyintentlib;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/5/10.
 * @desciption :
 * @version :
 * </pre>
 */

public class EasyIntent {
    private static final String TAG = "com.banzhi.easyintentlib.tag";
    private Intent intent;
    private Activity mActivity;
    private int requestCode;
    private OnActivityResultCallback mCallback;

    public EasyIntent(Intent intent, Activity mActivity, int requestCode, OnActivityResultCallback mCallback) {
        this.intent = intent;
        this.mActivity = mActivity;
        this.requestCode = requestCode;
        this.mCallback = mCallback;
    }

    public void start() {
        FragmentManager fragmentManager = mActivity.getFragmentManager();
        ProxyFragment proxyFragment = findFragmentByTag(fragmentManager);
        if (proxyFragment == null) {
            proxyFragment = ProxyFragment.newInstance();
            fragmentManager.beginTransaction()
                    .add(proxyFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
            proxyFragment.start(intent, requestCode, mCallback);
        }
    }

    private ProxyFragment findFragmentByTag(FragmentManager fragmentManager) {
        return (ProxyFragment) fragmentManager.findFragmentByTag(TAG);
    }

    public static class Builder {
        private Intent mIntent;
        private Bundle mBundle;
        private Activity mActivity;
        private int requestCode;
        private OnActivityResultCallback mCallback;
        private Class<?> mClass;

        public Builder toClass(Class<?> clazz) {
            this.mClass = clazz;
            return this;
        }

        public Builder toClass(String className) {
            try {
                this.mClass = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return this;
        }

        public Builder putIntent(Intent mIntent) {
            this.mIntent = mIntent;
            return this;
        }

        public Builder putBundle(Bundle mBundle) {
            this.mBundle = mBundle;
            return this;
        }

        public Builder with(Context context) {
            if (context instanceof Activity) {
                this.mActivity = (Activity) context;
            } else {
                throw new IllegalArgumentException("the context must instanceof activity");
            }
            return this;
        }

        public Builder with(Activity activity) {
            this.mActivity = activity;
            return this;
        }

        public Builder setRequestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public Builder setmCallback(OnActivityResultCallback mCallback) {
            this.mCallback = mCallback;
            return this;
        }

        public EasyIntent build() {
            if (mIntent == null) {
                mIntent = new Intent(mActivity, mClass);
            }
            mIntent.putExtras(mBundle);
            return new EasyIntent(mIntent, mActivity, requestCode, mCallback);
        }
    }

}
