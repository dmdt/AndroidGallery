package pw.prsk.gallery.ui.home

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import pw.prsk.gallery.R
import pw.prsk.gallery.ui.preferences.PreferenceFragment
import pw.prsk.gallery.ui.preferences.SettingsActivity
import pw.prsk.gallery.utils.ShortcutsHelper

class MainActivity : AppCompatActivity() {
    private lateinit var tlm: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAppSettings()
        setContentView(R.layout.activity_main)

        initShortcuts()
        initViewPager()
    }

    private fun initShortcuts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val shortcutsHelper = ShortcutsHelper(this)
            shortcutsHelper.restoreDynamicShortcuts()
            shortcutsHelper.refreshShortcuts()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.miSettings -> {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun initAppSettings() {
        val preferences = getSharedPreferences(PreferenceFragment.SETTINGS_FILE_NAME, Context.MODE_PRIVATE)
        val themeMode = when (preferences.getString(PreferenceFragment.THEME_SETTING, "")) {
            "Dark theme" -> AppCompatDelegate.MODE_NIGHT_YES
            "Light theme" -> AppCompatDelegate.MODE_NIGHT_NO
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(themeMode)
    }

    private fun initViewPager() {
        vpMain.adapter = PagerAdapter(this)
        val tabs = MainTabs.values()
        tlm = TabLayoutMediator(tlMain, vpMain) { tab, position ->
            vpMain.setCurrentItem(tab.position, true)
            tab.text = tabs[position].tabName
        }
        tlm.attach()

        when (intent.action) {
            ACTION_OPEN_NEWS -> {
                vpMain.setCurrentItem(0, false)
            }
            else -> {
                vpMain.setCurrentItem(1, false)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tlm.detach()
        vpMain.adapter = null
    }

    fun updateTabBadge(tabInfo: MainTabs, count: Int) {
        val tab = tlMain.getTabAt(tabInfo.tabNum)
        tab?.orCreateBadge?.number = count
    }

    enum class MainTabs(val tabNum: Int, val tabName: String) {
        TAB_NEWS(0, "News"),
        TAB_GALLERY(1, "Gallery"),
        TAB_TEST(2, "Test")
    }

    companion object {
//        Intent actions
        const val ACTION_OPEN_NEWS = "pw.prsk.pw.news"
    }
}