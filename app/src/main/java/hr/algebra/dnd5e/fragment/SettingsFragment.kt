package hr.algebra.dnd5e.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import hr.algebra.dnd5e.R


class SettingsFragment  : PreferenceFragmentCompat(){
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        findPreference<ListPreference>("pref_theme")?.setOnPreferenceChangeListener { _, newValue ->
            applyTheme(newValue as String)
            true
        }
        findPreference<ListPreference>("pref_language")?.setOnPreferenceChangeListener { _, newValue ->
            applyLanguage(newValue as String)
            true

        }
    }
    private fun applyTheme(value: String){
        val mode = when(value){
            "light"-> AppCompatDelegate.MODE_NIGHT_NO
            "dark"-> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    private fun applyLanguage(value: String){
        val locales= when(value){
            "system"-> LocaleListCompat.getEmptyLocaleList()
            else -> LocaleListCompat.forLanguageTags(value)
        }
        AppCompatDelegate.setApplicationLocales(locales)
    }
}