package com.debby.ippick

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.io.File


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fabStop.setOnClickListener { view ->
            requestStorage2()
        }
        tvMessage.text = getInfo();
    }

    private val filePath = PathUtils.getExternalDownloadsPath()
    private val fileName = "ip地址101" + ".txt"


    private fun requestStorage2() {
        PermissionUtils.permission(PermissionConstants.STORAGE)
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(permissionsGranted: List<String>) {
                    LogUtils.d(permissionsGranted)
                    val file = File(filePath, fileName)
                    LogUtils.d("QQQQ", FileUtils.createOrExistsFile(file))
                }

                override fun onDenied(
                    permissionsDeniedForever: List<String>,
                    permissionsDenied: List<String>
                ) {
                    ToastUtils.showShort("需要存储权限")
                }
            })
            .theme { activity -> ScreenUtils.setFullScreen(activity) }
            .request()
    }

    private fun getInfo(): String? {
        Log.i("fake", "isModuleActive");
        Log.i("fake", "isModuleActive");
        Log.i("fake", "isModuleActive");
        Log.i("fake", "isModuleActive");
        Log.i("fake", "isModuleActive");
        Log.i("fake", "isModuleActive");
        Log.i("fake", "isModuleActive");
        Log.i("fake", "isModuleActive");
        return "hello ,my name is Tom .I am from china"
    }

}
