package com.example.administrator.geetolsdktest;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;

import com.example.administrator.geetolsdktest.R;

public class ShortcutManager {

	private static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
	private static final String ACTION_DEL_SHORTCUT = "com.android.launcher.action.UNINSTALL_SHORTCUT";

	public static String WECHAT_PACKAGE = "com.tencent.mm";//这个包名留给读者测试使用

	private Context context;

	public ShortcutManager(Context context) {
		super();
		this.context = context;
	}


	public boolean addLaunchShortcut(String packageName) {
		PackageManager pManager = context.getPackageManager();
		Intent launchIntent = pManager.getLaunchIntentForPackage(packageName);
		if (launchIntent == null) {
			return false;
		}
		@SuppressLint("WrongConstant") List<ResolveInfo> infoList = pManager.queryIntentActivities(launchIntent, PackageManager.GET_ACTIVITIES);
		if (infoList == null || infoList.size() == 0) {
			return false;
		}
		ResolveInfo launchInfo = infoList.get(0);

		String name = launchInfo.loadLabel(pManager).toString();
		String launchActName = launchInfo.activityInfo.name;
		//int iconId = launchInfo.activityInfo.applicationInfo.icon;
		int iconId = R.mipmap.ic_launcher;
		Intent addShortcutIntent = new Intent(ACTION_ADD_SHORTCUT);
		// 添加名称
		addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "假微信");
		// 添加图标
		Context pkgContext;
		try {
			pkgContext = context.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY | Context.CONTEXT_INCLUDE_CODE);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		Parcelable iconRes = ShortcutIconResource.fromContext(pkgContext, R.drawable.ic_launcher_background);
		addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,R.drawable.ic_launcher_background);

		// 添加Component
		ComponentName compName = new ComponentName(packageName, launchActName);
		Intent extraIntent = new Intent(Intent.ACTION_MAIN);
		extraIntent.setComponent(compName);
		extraIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,R.drawable.ic_launcher_background);
		addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, extraIntent);

		addShortcutIntent.putExtra("duplicate", false);
		context.sendBroadcast(addShortcutIntent);

		return true;
	}

	public void addDownloadShortcut(int icon, String name, String packageName) {
		Intent intentAddShortcut = new Intent(ACTION_ADD_SHORTCUT);
		// 添加名称
		intentAddShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
		// 添加图标
		intentAddShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);

		Intent launchIntent = new Intent(Intent.ACTION_VIEW);
		String appAddr = "market://details?id=" + packageName;
		launchIntent.setData(Uri.parse(appAddr));

		intentAddShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launchIntent);
		intentAddShortcut.putExtra("duplicate", false);

		context.sendBroadcast(intentAddShortcut);
	}

	public boolean removeLaunchShortcut(String packageName) {
		Intent delShortcutIntent = new Intent(ACTION_DEL_SHORTCUT);

		PackageManager pManager = context.getPackageManager();
		Intent launchIntent = pManager.getLaunchIntentForPackage(packageName);
		if (launchIntent == null) {
			return false;
		}
		@SuppressLint("WrongConstant") List<ResolveInfo> infoList = pManager.queryIntentActivities(launchIntent, PackageManager.GET_ACTIVITIES);
		if (infoList == null || infoList.size() == 0) {
			return false;
		}
		ResolveInfo launchInfo = infoList.get(0);

		String name = launchInfo.loadLabel(pManager).toString();
		String launchActName = launchInfo.activityInfo.name;

		// 快捷方式的名称
		delShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);

		ComponentName comp = new ComponentName(packageName, launchActName);
		delShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));

		context.sendBroadcast(delShortcutIntent);
		return true;
	}

	public void removeDownloadShortcut(Parcelable icon, String name, String packageName) {
		Intent intentAddShortcut = new Intent(ACTION_DEL_SHORTCUT);
		// 添加名称
		intentAddShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
		// 添加图标
		intentAddShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);

		Intent launchIntent = new Intent(Intent.ACTION_VIEW);
		String appAddr = "market://details?id=" + packageName;
		launchIntent.setData(Uri.parse(appAddr));

		intentAddShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launchIntent);
		intentAddShortcut.putExtra("duplicate", false);

		context.sendBroadcast(intentAddShortcut);
	}

	public boolean hasApp(String packageName) {
		PackageManager pManager = context.getPackageManager();
		try {
			pManager.getApplicationInfo(packageName, 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
