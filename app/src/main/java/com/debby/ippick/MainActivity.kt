package com.debby.ippick

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.*
import dalvik.system.PathClassLoader
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.io.*
import java.util.*


class MainActivity : AppCompatActivity() {

    var mClassLoaders =
        HashMap<String, ClassLoader>()
    var mParentClassLoader: ClassLoaderFilter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fabStop.setOnClickListener { view ->
            requestStorage2()
        }
        fabStart.setOnClickListener {
            getList()
        }
        tvMessage.text = getInfo();
    }

    private val filePath = PathUtils.getExternalDownloadsPath()
    private val fileName = "ip地址101" + ".txt"


    fun getList() {
//        PackageManager pm = getPackageManager();
//        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);  // 获取所以已安装的包
//        ArrayList<HashMap<String, Object>> listItems = new ArrayList<HashMap<String, Object>>();
//        for (PackageInfo packageInfo : installedPackages) {
//            ExposedBridge.loadModule()
//        }
        var remoteContext: Context? = null
        val dexPacName = "com.debby.xposetest"
        try {
            remoteContext = createPackageContext(
                dexPacName,
                Context.CONTEXT_INCLUDE_CODE or Context.CONTEXT_IGNORE_SECURITY
            )
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        var moduleClassName: String? = ""
        if (null != remoteContext) {
            var `is`: InputStream? = null
            try {
                `is` = remoteContext.assets.open("xposed_init")
                val moduleClassesReader =
                    BufferedReader(InputStreamReader(`is`))
                while (moduleClassesReader.readLine().also {
                        if (it != null) {
                            moduleClassName = it
                        }
                    } != null) {
                    moduleClassName = moduleClassName!!.trim()
                    if (moduleClassName!!.isEmpty() || moduleClassName!!.startsWith("#")) continue
                    Log.e("moduleClassName", moduleClassName!!)
                }

            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                closeSliently(`is`)
            }
        }
        val info: ApplicationInfo = packageManager.getApplicationInfo(dexPacName, 0)
        val mcl = getClassLoader(info.sourceDir, info.packageName)
        val moduleClass: Class<*> = mcl!!.loadClass(moduleClassName!!)
        val moduleInstance = moduleClass.newInstance()


        if (moduleInstance is IXposedHookLoadPackage) {
            // hookLoadPackage(new IXposedHookLoadPackage.Wrapper((IXposedHookLoadPackage) moduleInstance));
            val wrapper =
                IXposedHookLoadPackage.Wrapper(moduleInstance as IXposedHookLoadPackage)
            val xc_loadPackageCopyOnWriteSortedSet =
                XposedBridge.CopyOnWriteSortedSet<XC_LoadPackage>()
            xc_loadPackageCopyOnWriteSortedSet.add(wrapper)
            val lpparam = LoadPackageParam(xc_loadPackageCopyOnWriteSortedSet)
            lpparam.packageName = info.packageName
            lpparam.processName = info.processName
            lpparam.classLoader = classLoader
            lpparam.appInfo = info
            lpparam.isFirstApplication = true
            XC_LoadPackage.callAll(lpparam)
        }
//        ExposedBridge.loadModule()
    }

    private fun closeSliently(closeable: Closeable?) {
        if (closeable == null) {
            return
        }
        try {
            closeable.close()
        } catch (e: Throwable) {
            // IGNORE
        }
    }

    private fun getClassLoader(
        sourceDir: String,
        pkg: String
    ): ClassLoader? {
        //防止重复加载
        if (mClassLoaders.containsKey(pkg)) {
            return mClassLoaders.get(pkg)
        }
        //
        val classLoader: ClassLoader =
            PathClassLoader(sourceDir, getParentClassLoader())
        mClassLoaders.put(pkg, classLoader)
        return classLoader
    }

    private fun getParentClassLoader(): ClassLoader? {
        if (mParentClassLoader == null) {
            // 获取父类的加载器
            Log.d("getParentClassLoader", "hostClassLoader= $classLoader")
            mParentClassLoader = ClassLoaderFilter(classLoader, "android")
        }
        return mParentClassLoader
    }

    class ClassLoaderFilter(private val mBase: ClassLoader, private val mPackage: String) :
        ClassLoader(getSystemClassLoader()) {
        @Throws(ClassNotFoundException::class)
        override fun loadClass(
            name: String,
            resolve: Boolean
        ): Class<*> {
            if (!name.startsWith(mPackage)) super.loadClass(name, resolve)
            return mBase.loadClass(name)
        }

    }


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
