package pw.prsk.gallery.ui.preferences

import androidx.appcompat.app.AppCompatDelegate

class PreferencePresenter {
    var view: PreferenceViewInterface? = null

    fun attachView(view: PreferenceViewInterface) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    fun onThemeChange(string: String) {
        view?.showToast("You set app theme to \"$string\".")
        val mode = when (string) {
            "Dark theme" -> AppCompatDelegate.MODE_NIGHT_YES
            "Light theme" -> AppCompatDelegate.MODE_NIGHT_NO
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    fun onLanguageChange(string: String) {
        view?.showToast("You set app language to \"$string\".")
    }
}