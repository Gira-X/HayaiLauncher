package com.hayaisoftware.launcher.util;

import android.content.*;
import android.content.pm.*;

import java.util.*;

public enum ContentShare {
	;

	private static final String[] filterOutPackageNames =
			{"com.android.documentsui", "org.omnirom.omniswitch", "com.bel.android.dspmanager",
					"com.android.providers.downloads.ui", "com.android.stk", "com.urbandroid.lux",
					"com.touchtype.swiftkey"};

	public static Intent shareTextIntent(final String text) {
		final Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		sendIntent.putExtra(Intent.EXTRA_TEXT, text);
		sendIntent.setType("text/plain");
		return sendIntent;
	}

	private static List<ResolveInfo> filterOutResolveInfos(final Collection<ResolveInfo> infos) {
		final List<ResolveInfo> filtered = new ArrayList<>(infos.size());
		final Collection<String> filters = new ArrayList<>(Arrays.asList(filterOutPackageNames));

		for (final ResolveInfo info : infos) {
			boolean passedFilter = true;
			for (final String filter : filters) {
				if (info.activityInfo.packageName.equals(filter)) {
					filters.remove(filter);
					passedFilter = false;
					break;
				}
			}
			if (passedFilter) {
				filtered.add(info);
			}
		}
		return filtered;
	}

	public static List<ResolveInfo> getLaunchableResolveInfos(final PackageManager pm) {
		final Intent intent = new Intent();
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		final List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
		return filterOutResolveInfos(infos);
	}
}
