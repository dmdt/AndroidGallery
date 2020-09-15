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
        tlm = TabLayoutMediator(tlMain, vpMain) { tab, position ->
            vpMain.setCurrentItem(tab.position, true)
            tab.orCreateBadge.number = (position + 1) * (0..1024).random()
            tab.text = tabNames[position]
        }
        tlm.attach()
        vpMain.setCurrentItem(1, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        tlm.detach()
        vpMain.adapter = null
    }
}