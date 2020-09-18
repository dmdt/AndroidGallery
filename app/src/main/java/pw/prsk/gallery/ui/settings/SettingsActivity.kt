package pw.prsk.gallery.ui.settings

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import pw.prsk.gallery.R

class SettingsActivity: AppCompatActivity(), SettingsViewInterface {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_settings)
    }
}