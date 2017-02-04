package com.hayaisoftware.launcher.util;

import android.content.*;
import android.content.pm.*;

import java.util.*;

public enum ContentShare {
	;

	private static final String[] filterOutTasks =
			{"dspmanager", "omniswitch", "urbandroid.lux", "downloads.ui", "documentsui",
					"android.stk", "touchtype.swiftkey"};

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
		for (final ResolveInfo info : infos) {
			boolean passedFilter = true;
			for (final String filter : filterOutTasks) {
				if (info.activityInfo.packageName.contains(filter)) {
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
