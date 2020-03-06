package com.danqiu.online.live.demo.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SystemUtil {

	/** 屏幕分辨率高 **/
	public static int getScreenHeightFen(Activity paramActivity) {
		Display display = paramActivity.getWindowManager().getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		return metrics.heightPixels;
	}
	/** statusBar高度 **/
	public static int getStatusBarHeight(Activity activity) {
		int result = 0;
		int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = activity.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}
	/** 可见屏幕高度 **/
	public static int getAppHeight(Activity paramActivity) {
		Rect localRect = new Rect();
		paramActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
		return localRect.height();
	}
	/** 底部导航栏 **/
	public static int getNavigationBarHeight(Activity activity) {
		Resources resources = activity.getResources();
		int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
		// 获取NavigationBar的高度
		int height = resources.getDimensionPixelSize(resourceId);
		return height;
	}
	/** 底部虚拟键高度 **/
	public static int getBotBarHeight(Activity activity) {
		Rect rect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		int winHeight = activity.getWindow().getDecorView().getHeight();
		return winHeight - rect.bottom;
	}
	/** 显示状态栏 **/
	public static void showStatusBar(Activity activity) {
		WindowManager.LayoutParams attr = activity.getWindow().getAttributes();
		attr.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
		activity.getWindow().setAttributes(attr);
	}
	/** 设置状态栏高度 **/
	public static void setStatusHeight(View status, Activity activity) {
		LayoutParams params = (LayoutParams) status.getLayoutParams();
		params.height = getStatusBarHeight(activity);
		status.setLayoutParams(params);
	}
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	/** 屏幕高 **/
	public static int getScreenHeight(Activity context) {
//		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//
//		int height = wm.getDefaultDisplay().getHeight();
		int realHeight = 0;
		Display display = context.getWindowManager().getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		if (Build.VERSION.SDK_INT >= 17) {
			Point size = new Point();
			display.getRealSize(size);
			realHeight = size.y;
		} else if (Build.VERSION.SDK_INT < 17
				&& Build.VERSION.SDK_INT >= 14) {
			try {
				Method mGetRawH =Display.class.getMethod("getRawHeight");
				Method mGetRawW = Display.class.getMethod("getRawWidth");
				realHeight = (Integer) mGetRawH.invoke(display);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else {
			realHeight = metrics.heightPixels;
		}
		return realHeight;
	}
	/** 屏幕宽 **/
	public static int getScreenWidth(Activity context) {
		int realWidth = 0;
		Display display = context.getWindowManager().getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		if (Build.VERSION.SDK_INT >= 17) {
			Point size = new Point();
			display.getRealSize(size);
			realWidth = size.x;
		} else if (Build.VERSION.SDK_INT < 17
				&& Build.VERSION.SDK_INT >= 14) {
			try {
				Method mGetRawH =Display.class.getMethod("getRawHeight");
				Method mGetRawW = Display.class.getMethod("getRawWidth");
				realWidth = (Integer) mGetRawW.invoke(display);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else {
			realWidth = metrics.widthPixels;
		}
		return realWidth;
	}
	/** 关闭键盘 **/
	public static void hideSoftInput(View paramEditText, Context context) {
		((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(paramEditText.getWindowToken(), 0);
	}

	/** 键盘是否在显示 **/
	public static boolean isKeyBoardShow(Activity paramActivity) {
		int height = SystemUtil.getScreenHeight(paramActivity) - SystemUtil.getStatusBarHeight(paramActivity)
				- SystemUtil.getAppHeight(paramActivity);
		return height != 0;
	}
	/**
	 * [获取应用程序版本名称信息]
	 * @param context
	 * @return 当前应用的版本名称
	 */
	public static synchronized String getPackageName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.packageName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/** 显示键盘 **/
	public static void showKeyBoard(final View paramEditText, final Context context) {
		paramEditText.requestFocus();
		paramEditText.postDelayed(new Runnable() {
			@Override
			public void run() {
				((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(paramEditText, 0);
			}
		},10);
	}


	// 版本名
	public static String getVersionName(Context context) {
		return getPackageInfo(context).versionName;
	}

	// 版本号
	public static int getVersionCode(Context context) {
		return getPackageInfo(context).versionCode;
	}

	private static PackageInfo getPackageInfo(Context context) {
		PackageInfo pi = null;

		try {
			PackageManager pm = context.getPackageManager();
			pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);

			return pi;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pi;
	}

	/**
	 * 判断底部navigator是否已经显示
	 *
	 * @param windowManager
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	public static boolean hasSoftKeys(WindowManager windowManager) {
		Display d = windowManager.getDefaultDisplay();

		DisplayMetrics realDisplayMetrics = new DisplayMetrics();
		d.getRealMetrics(realDisplayMetrics);

		int realHeight = realDisplayMetrics.heightPixels;
		int realWidth = realDisplayMetrics.widthPixels;

		DisplayMetrics displayMetrics = new DisplayMetrics();
		d.getMetrics(displayMetrics);

		int displayHeight = displayMetrics.heightPixels;
		int displayWidth = displayMetrics.widthPixels;

		return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
	}



	// 去除重复数据
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List removeDuplicateWithOrder(List list) {
		Set<String> set = new LinkedHashSet<String>();
		set.addAll(list);
		list.clear();
		list.addAll(set);
		return list;
	}



	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	/**
	 * android 5.0之后如何获取当前运行的应用包名？
	 * @param context
	 * @return
	 */
	public static String getCurrentPkgName(Context context) {
		ActivityManager.RunningAppProcessInfo currentInfo = null;
		Field field = null;
		int START_TASK_TO_FRONT = 2;
		String pkgName = null;
		try {
			field = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");
		} catch (Exception e) {
			e.printStackTrace();
		}
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List appList = am.getRunningAppProcesses();
		List<ActivityManager.RunningAppProcessInfo> processes = ((ActivityManager) context.getSystemService(
				Context.ACTIVITY_SERVICE)).getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo app : processes) {
			if (app.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				Integer state = null;
				try {
					state = field.getInt(app);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (state != null && state == START_TASK_TO_FRONT) {
					currentInfo = app;
					break;
				}
			}
		}
		if (currentInfo != null) {
			pkgName = currentInfo.processName;
		}
		return pkgName;
	}

	/**
	 * 获取当前应用名称
	 *
	 * @param context
	 * @return
	 */
	public static String getAppName(Context context) {
		String appName = null;
		PackageManager packageManager = context.getPackageManager();
		try {
			ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			//CharSequence这两者效果是一样的.
			appName = packageManager.getApplicationLabel(applicationInfo).toString();
			appName = (String) packageManager.getApplicationLabel(applicationInfo);

		} catch (Exception e) {
			e.printStackTrace();
			Log.d("test","Exception=" + e.toString());
			return null;
		}
		return appName;
	}


}
