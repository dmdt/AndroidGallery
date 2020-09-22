package pw.prsk.gallery.ui.preferences

import android.os.Bundle
import android.widget.Toast
import androidx.preference.ListPreference
import androidx.preference.Preference.OnPreferenceChangeListener
import androidx.preference.PreferenceFragmentCompat
import pw.prsk.gallery.R

class PreferenceFragment : PreferenceFragmentCompat(), PreferenceViewInterface {
    private val presenter: PreferencePresenter = PreferencePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.sharedPreferencesName = SETTINGS_FILE_NAME
        setPreferencesFromResource(R.xml.preferences, rootKey)
        preferenceManager.findPreference<ListPreference>("theme")?.onPreferenceChangeListener =
            listChangeListener
        preferenceManager.findPreference<ListPreference>("language")?.onPreferenceChangeListener = listChangeListener
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private val listChangeListener = OnPreferenceChangeListener { preference, newValue ->
        when (preference.key) {
            THEME_SETTING -> presenter.onThemeChange(newValue as String)
            LANGUAGE_SETTING -> presenter.onLanguageChange(newValue as String)
            else -> showToast("Unknown property.")
        }
        true
    }

    override fun showToast(resId: Int) {
        Toast.makeText(this.context, resId, Toast.LENGTH_SHORT).show()
    }

    override fun showToast(string: String) {
        Toast.makeText(this.context, string, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val SETTINGS_FILE_NAME = "app.settings"

        const val THEME_SETTING = "theme"
        const val LANGUAGE_SETTING = "language"
    }
}