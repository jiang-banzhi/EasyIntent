package com.banzhi.easyintentlib;

import android.content.Intent;

/**
 * <pre>
 * @author : No.1
 * @time : 2018/5/10.
 * @desciption :
 * @version :
 * </pre>
 */

public interface OnActivityResultCallback {
    void onActivityResultCallback(int requestCode, Intent data);
}
