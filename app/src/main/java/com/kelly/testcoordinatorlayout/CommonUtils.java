package com.kelly.testcoordinatorlayout;

import android.content.Context;

/**
 * Created by zongkaili on 2016/12/28.
 */
public class CommonUtils {
    // 获取状态栏高度
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

//    public static void setTranslucentStatus(Activity activity, View view){
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//            Window win = activity.getWindow();
//            WindowManager.LayoutParams winParams = win.getAttributes();
//            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//            winParams.flags |= bits;
//            win.setAttributes(winParams);
//            SystemStatusManager tintManager = new SystemStatusManager(activity);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setStatusBarTintResource(0);
//
//            view.setPadding(0 , getStatusBarHeight(activity) , 0 , 0);
//        }
//    }
}
