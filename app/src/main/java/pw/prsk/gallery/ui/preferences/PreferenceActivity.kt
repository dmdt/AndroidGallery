package pw.prsk.gallery.ui.preferences

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pw.prsk.gallery.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        var fragment = supportFragmentManager.findFragmentById(R.id.clFragmentContainer)
        if (fragment == null) {
            fragment = PreferenceFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.clFragmentContainer, fragment)
                .commit()
        }
    }
}