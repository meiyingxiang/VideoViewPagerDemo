package com.example.videopagerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.videopagerdemo.databinding.ActivityMainBinding
import com.example.videopagerdemo.fragment.FooFragment
import com.example.videopagerdemo.fragment.VideoFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.mainViewPager.apply {
            adapter = object : FragmentStateAdapter(this@MainActivity) {
                override fun getItemCount(): Int {
                    return 2
                }

                override fun createFragment(position: Int): Fragment {
                    return when (position) {
                        1 -> VideoFragment()
                        else -> FooFragment()
                    }
                }
            }
            //false表示不要出现平滑滚动的过程
            setCurrentItem(1, false)
        }
        TabLayoutMediator(
            viewBinding.tabLayout,
            viewBinding.mainViewPager
        ) { tab: TabLayout.Tab, i: Int ->
            tab.text = when (i) {
                1 -> "video"
                else -> "foo"
            }
        }.attach()
    }
}