package com.androidemu.gbc;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import android.content.Intent;
import android.content.SharedPreferences;

import android.net.Uri;

import android.os.Bundle;

import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

import com.androidemu.HelpActivity;
import com.androidemu.GameKeyPreference;
import com.androidemu.gbc.input.Keycodes;

import com.androidemu.wrapper.Wrapper;

public class EmulatorSettings extends PreferenceActivity
{

	private static final String SEARCH_ROM_URI = "http://www.romfind.com/?sid=YONG&c=5";
	private static final Uri ABOUT_URI = Uri.parse("file:///android_asset/about.html");
	private static final String MARKET_URI = "http://market.android.com/details?id=";
	private static final String GAME_GRIPPER_URI = "https://sites.google.com/site/gamegripper";

	private static final int REQUEST_LOAD_KEY_PROFILE = 1;
	private static final int REQUEST_SAVE_KEY_PROFILE = 2;

	public static final int[] gameKeys = { Keycodes.GAMEPAD_UP, Keycodes.GAMEPAD_DOWN,
			Keycodes.GAMEPAD_LEFT, Keycodes.GAMEPAD_RIGHT, Keycodes.GAMEPAD_UP_LEFT,
			Keycodes.GAMEPAD_UP_RIGHT, Keycodes.GAMEPAD_DOWN_LEFT,
			Keycodes.GAMEPAD_DOWN_RIGHT, Keycodes.GAMEPAD_SELECT, Keycodes.GAMEPAD_START,
			Keycodes.GAMEPAD_A, Keycodes.GAMEPAD_B, Keycodes.GAMEPAD_A_TURBO,
			Keycodes.GAMEPAD_B_TURBO, Keycodes.GAMEPAD_AB, };

	public static final String[] gameKeysPref = { "gamepad_up", "gamepad_down",
			"gamepad_left", "gamepad_right", "gamepad_up_left", "gamepad_up_right",
			"gamepad_down_left", "gamepad_down_right", "gamepad_select", "gamepad_start",
			"gamepad_A", "gamepad_B", "gamepad_A_turbo", "gamepad_B_turbo", "gamepad_AB", };

	private static final int[] gameKeysName = { R.string.gamepad_up,
			R.string.gamepad_down, R.string.gamepad_left, R.string.gamepad_right,
			R.string.gamepad_up_left, R.string.gamepad_up_right,
			R.string.gamepad_down_left, R.string.gamepad_down_right,
			R.string.gamepad_select, R.string.gamepad_start, R.string.gamepad_A,
			R.string.gamepad_B, R.string.gamepad_A_turbo, R.string.gamepad_B_turbo,
			R.string.gamepad_AB, };

	static
	{
		final int n = gameKeys.length;
		if (gameKeysPref.length != n || gameKeysName.length != n)
			throw new AssertionError("Key configurations are not consistent");
	}

	public static Intent getSearchROMIntent()
	{
		return new Intent(Intent.ACTION_VIEW, Uri.parse(SEARCH_ROM_URI))
				.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	}

	// FIXME
	private static ArrayList<String> getAllKeyPrefs()
	{
		ArrayList<String> result = new ArrayList<String>();
		result.addAll(Arrays.asList(gameKeysPref));
		return result;
	}

	private SharedPreferences settings;

	private Map<String, Integer> getKeyMappings()
	{
		TreeMap<String, Integer> mappings = new TreeMap<String, Integer>();

		for (String key : getAllKeyPrefs())
		{
			GameKeyPreference pref = (GameKeyPreference) findPreference(key);
			mappings.put(key, Integer.valueOf(pref.getKeyValue()));
		}
		return mappings;
	}

	private void setKeyMappings(Map<String, Integer> mappings)
	{
		SharedPreferences.Editor editor = settings.edit();

		for (String key : getAllKeyPrefs())
		{
			Integer value = mappings.get(key);
			if (value != null)
			{
				GameKeyPreference pref = (GameKeyPreference) findPreference(key);
				pref.setKey(value.intValue());
				editor.putInt(key, value.intValue());
			}
		}
		editor.commit();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setTitle(R.string.settings);
		addPreferencesFromResource(R.xml.preferences);

		settings = PreferenceManager.getDefaultSharedPreferences(this);

		final int[] defaultKeys = DefaultPreferences.getKeyMappings(this);

		PreferenceGroup group = (PreferenceGroup) findPreference("gamepad");
		for (int i = 0; i < gameKeysPref.length; i++)
		{
			GameKeyPreference pref = new GameKeyPreference(this);
			pref.setKey(gameKeysPref[i]);
			pref.setTitle(gameKeysName[i]);
			pref.setDefaultValue(defaultKeys[i]);
			group.addPreference(pref);
		}

		if (!Wrapper.supportsMultitouch(this))
		{
			findPreference("enableVKeypad").setSummary(R.string.multitouch_unsupported);
		}

		findPreference("about").setIntent(
				new Intent(this, HelpActivity.class).setData(ABOUT_URI));
		findPreference("upgrade").setIntent(
				new Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_URI + getPackageName())));
	}

	@Override
	protected void onActivityResult(int request, int result, Intent data)
	{
		switch (request)
		{
			case REQUEST_LOAD_KEY_PROFILE:
				if (result == RESULT_OK)
				{
					setKeyMappings(KeyProfilesActivity
							.loadProfile(this, data.getAction()));
				}
				break;

			case REQUEST_SAVE_KEY_PROFILE:
				if (result == RESULT_OK)
				{
					KeyProfilesActivity.saveProfile(this, data.getAction(),
							getKeyMappings());
				}
				break;
		}
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference)
	{
		String key = preference.getKey();

		if ("loadKeyProfile".equals(key))
		{
			Intent intent = new Intent(this, KeyProfilesActivity.class);
			intent.putExtra(KeyProfilesActivity.EXTRA_TITLE,
					getString(R.string.load_profile));
			startActivityForResult(intent, REQUEST_LOAD_KEY_PROFILE);
			return true;
		}
		if ("saveKeyProfile".equals(key))
		{
			Intent intent = new Intent(this, KeyProfilesActivity.class);
			intent.putExtra(KeyProfilesActivity.EXTRA_TITLE,
					getString(R.string.save_profile));
			startActivityForResult(intent, REQUEST_SAVE_KEY_PROFILE);
			return true;
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}
}
