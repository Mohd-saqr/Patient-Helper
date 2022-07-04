package com.patient.patienthelper.helperClass;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

import java.util.Map;
import java.util.Set;

public class MySharedPreferences implements SharedPreferences.Editor, SharedPreferences {

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;


    public MySharedPreferences(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }


    @Override
    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    @Nullable
    @Override
    public String getString(String key, @Nullable String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        return sharedPreferences.getStringSet(key, defValues);
    }

    @Override
    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    @Override
    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    @Override
    public Editor edit() {
        return editor;
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

    }


    @Override
    public Editor putString(String key, @Nullable String value) {
        return editor.putString(key, value);
    }

    @Override
    public Editor putStringSet(String key, @Nullable Set<String> values) {
        return editor.putStringSet(key, values);
    }

    @Override
    public Editor putInt(String key, int value) {
        return editor.putInt(key, value);
    }

    @Override
    public Editor putLong(String key, long value) {
        return editor.putLong(key, value);
    }

    @Override
    public Editor putFloat(String key, float value) {
        return editor.putFloat(key, value);
    }

    @Override
    public Editor putBoolean(String key, boolean value) {
        return editor.putBoolean(key, value);
    }

    @Override
    public Editor remove(String key) {
        return editor.remove(key);
    }

    @Override
    public Editor clear() {
        return editor.clear();
    }

    @Override
    public boolean commit() {
        return editor.commit();
    }

    @Override
    public void apply() {
        editor.apply();
    }
}
