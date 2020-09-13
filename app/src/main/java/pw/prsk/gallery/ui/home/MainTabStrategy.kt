package pw.prsk.gallery.ui.home

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

interface MainTabStrategy: TabLayoutMediator.TabConfigurationStrategy {
    override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {

    }
}