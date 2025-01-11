package ru.mrfix1033.retrofiteweatherapi.adapters

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.mrfix1033.retrofiteweatherapi.data.models.ScreenElement
import ru.mrfix1033.retrofiteweatherapi.enumerations.Key
import ru.mrfix1033.retrofiteweatherapi.fragments.ViewPagerFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity, val list: List<ScreenElement>) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = ViewPagerFragment()
        fragment.arguments = bundleOf(
            Key.screenElement to list[position],
            Key.isLast to (position == list.size - 1)
        )
        return fragment
    }
}