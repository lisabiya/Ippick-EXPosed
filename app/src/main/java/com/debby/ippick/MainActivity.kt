package com.debby.ippick

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fabStop.setOnClickListener { view ->
            tvMessage.text = getInfo();
        }
        fabStart.setOnClickListener {
        }
        tvMessage.text = getInfo();
    }

    private fun getInfo(): String? {
        Log.i("fake", "isModuleActive");
        return "获取正常数据成功"
    }

}
