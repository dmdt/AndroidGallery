package pw.prsk.gallery.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import pw.prsk.gallery.R

class MainActivity : AppCompatActivity() {
    private val tabNames = listOf(
        "News",
        "Gallery",
        "Test tab"
    )
    private lateinit var tlm: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewPager()
    }

    private fun initViewPager() {
        vpMain.adapter = PagerAdapter(this)
        val tabs = MainTabs.values()
        tlm = TabLayoutMediator(tlMain, vpMain) { tab, position ->
            vpMain.setCurrentItem(tab.position, true)
            tab.text = tabs[position].tabName
        }
        tlm.attach()
        vpMain.setCurrentItem(1, false)
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
}