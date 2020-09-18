package pw.prsk.gallery.ui.settings

class SettingsPresenter {
    var view: SettingsViewInterface? = null

    fun attachView(view: SettingsViewInterface) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }
}