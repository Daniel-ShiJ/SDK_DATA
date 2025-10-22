package com.kingnet.sdk.common.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import com.kingnet.sdk.common.extensions.md5
import java.util.Locale

/**
 * Author:Daniel.ShiJ
 * Date:2024/10/12 16:15
 * Description:
 */
object PhoneUtils {

    /**
     * 获取分辨率
     */
    fun getRes(context: Context):String{
        val displayMetrics = context.resources.displayMetrics
        return String.format(Locale.CHINA, "%d*%d", displayMetrics.heightPixels, displayMetrics.widthPixels)
    }

    /**
     * 获取应用程序名称
     */
    fun getAppName(context: Context):String{
        var result = ""
        try {
            context.apply {
                val packManager = packageManager
                val packageInfo = packManager.getPackageInfo(packageName,0)
                val labelRes = packageInfo.applicationInfo.labelRes
                result = resources.getString(labelRes)
            }
        }catch (e:Exception){
            result=""
        }
        return result
    }

    /**
     * 获取应用程序版本名称信息
     */
    fun getPackageName(context: Context):String{
        var result = ""
        context.apply {
            try {
                val packageInfo = packageManager.getPackageInfo(context.packageName,0)
                result = packageInfo.packageName
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        return result
    }

    /**
     * 获取客户端版本号
     */
    fun getVersionName(context: Context):String{
        var result = ""
        context.apply {
            try {
                val packageInfo = packageManager.getPackageInfo(context.packageName,0)
                result = packageInfo.versionName
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        return result
    }

    /**
     * 获取手机系统语言
     */
    fun getLanguage(context: Context):String{
        var result = ""
        context.apply {
            var locale:Locale
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                locale = resources.configuration.locales.get(0)
            }else{
                locale = resources.configuration.locale
            }

            result = locale.language
        }
        return result
    }

    /**
     * 获取网络类型
     * @return
     */
    @SuppressLint("MissingPermission")
    fun getNetWorkType(context: Context): String {
        var result = ""
        try {
            context.apply {
                var manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                manager?.apply {
                    val networkInfo = getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    if (networkInfo != null && networkInfo.isConnectedOrConnecting) {
                        result = "wifi"
                    }
                }
                val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                @SuppressLint("MissingPermission") val networkType = telephonyManager.networkType
                when (networkType) {
                    TelephonyManager.NETWORK_TYPE_GPRS,
                    TelephonyManager.NETWORK_TYPE_EDGE,
                    TelephonyManager.NETWORK_TYPE_CDMA,
                    TelephonyManager.NETWORK_TYPE_1xRTT,
                    TelephonyManager.NETWORK_TYPE_IDEN -> {
                        result = "2G"
                    }

                    TelephonyManager.NETWORK_TYPE_UMTS,
                    TelephonyManager.NETWORK_TYPE_EVDO_0,
                    TelephonyManager.NETWORK_TYPE_EVDO_A,
                    TelephonyManager.NETWORK_TYPE_HSDPA,
                    TelephonyManager.NETWORK_TYPE_HSUPA,
                    TelephonyManager.NETWORK_TYPE_HSPA,
                    TelephonyManager.NETWORK_TYPE_EVDO_B,
                    TelephonyManager.NETWORK_TYPE_EHRPD,
                    TelephonyManager.NETWORK_TYPE_HSPAP -> {
                        result = "3G"
                    }

                    TelephonyManager.NETWORK_TYPE_LTE -> {
                        result = "4G"
                    }
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        return result
    }

    private const val MAC_ADDRESS_KEY = "mac_address_key"
    @SuppressLint("MissingPermission")
    fun getMacAddress(context: Context): String {
        var macAddress = SpUtils.getString(context,MAC_ADDRESS_KEY)

        if (!macAddress.isNullOrBlank()){
            return macAddress
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                macAddress =
                    Settings.Global.getString(context.contentResolver, "ethernet_mac_address")
//                获取mac地址返回：02:00:00:00:00:00
//                val wifiManager =
//                    context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
//                val wifiInfo = wifiManager.connectionInfo
//                macAddress = wifiInfo.macAddress
            } else {
                val wifiManager =
                    context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val wifiInfo = wifiManager.connectionInfo
                macAddress = wifiInfo.macAddress
            }
            SpUtils.save(context,MAC_ADDRESS_KEY,macAddress)
//        }
        } catch (e: Exception) {
            macAddress = ""
        }
        return macAddress
    }

    private const val ANDROID_ID_KEY = "android_id_key"
    /**
     * 获取Android Id
     */
    private fun getAndroidId(context: Context):String{
        var androidId = SpUtils.getString(context,ANDROID_ID_KEY)

        if (!androidId.isNullOrBlank()){
            return androidId
        }
        androidId = "${
            Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }".md5()

        SpUtils.save(context,ANDROID_ID_KEY,androidId)
        return androidId
    }

    private const val DEVICE_ID_KEY = "device_id_key"
    /**
     * 获取设备id
     */
    fun getDeviceId(context: Context): String {
        var deviceID = SpUtils.getString(context,DEVICE_ID_KEY)
        if (!deviceID.isNullOrBlank()){
            return deviceID
        }
        deviceID = try {
            val tm =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            tm.deviceId
        } catch (e: Exception) {
            ""
        }
        if (deviceID.isNullOrBlank()) {
            deviceID = getAndroidId(context)
        }
        SpUtils.save(context,DEVICE_ID_KEY,deviceID)
        return deviceID
    }

}