package com.banzhi.easyintentlib;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

/**
 * <pre>
 * @author : No.1
 * @time : 201805010.
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

    private EasyIntent(Builder builder) {
        this.intent = builder.mIntent;
        this.mActivity = builder.mActivity;
        this.requestCode = builder.requestCode;
    }

    public static Builder getBuilder(Activity activity) {
        return new Builder(activity);
    }

    public static Builder getBuilder(Fragment fragment) {
        return new Builder(fragment);
    }

    public static Builder getBuilder(android.support.v4.app.Fragment fragment) {
        return new Builder(fragment);
    }

    public void startForResult(OnActivityResultCallback callback) {
        this.mCallback = callback;
        start();
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
        }
        if (proxyFragment != null) {
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

        public Builder(Activity activity) {
            this.mActivity = activity;
        }

        public Builder(Fragment fragment) {
            this.mActivity = fragment.getActivity();
        }

        public Builder(android.support.v4.app.Fragment fragment) {
            this.mActivity = fragment.getActivity();
        }

        public Builder putIntent(Intent mIntent) {
            this.mIntent = mIntent;
            return this;
        }

        public Builder putBundle(Bundle mBundle) {
            this.mBundle = mBundle;
            return this;
        }


        public Builder setRequestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }


        public EasyIntent build() {
            if (mBundle != null) {
                mIntent.putExtras(mBundle);
            }
            return new EasyIntent(this);
        }
    }

}
