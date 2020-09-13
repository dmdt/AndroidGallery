package pw.prsk.gallery.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(activity: MainActivity) : FragmentStateAdapter(activity) {
    val tabNames = MutableList(3) { index ->
        index.toString()
    }

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> NewsFragment()
        1 -> GalleryFragment()
        2 -> RvFragment()
        else -> NewsFragment()
    }

    override fun getItemCount() = 3
}