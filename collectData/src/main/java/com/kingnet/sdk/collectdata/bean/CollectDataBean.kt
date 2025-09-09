package com.kingnet.sdk.collectdata.bean

import com.kingnet.sdk.common.extensions.toJson
import com.kingnet.sdk.common.utils.LogUtils
import org.json.JSONArray
import org.json.JSONObject

/**
 * Author:Daniel.ShiJ
 * Date:2024/10/12 11:43
 * Description:数据收集对象
 */
data class CollectDataBean(
    private val ouid: String,
    private val project: String,
    private val did: String,
    private val event: String,
    private val timestamp: String,
    private val properties: JSONObject
){
    fun selfToJson():String{
        try {
            val jsonArray = JSONArray()
            val jsonObject = JSONObject().apply {
                putOpt("project", project)
                putOpt("did", did)
                putOpt("ouid", ouid)
                putOpt("timestamp", timestamp)
                putOpt("event", event)
                putOpt("properties",properties)
            }
            jsonArray.put(jsonObject)
            LogUtils.e("jsonArray：${jsonArray.toJson()}")
            return jsonArray.toString()
        }catch (e:Exception){
            LogUtils.e("CollectDataBean Exception -> ${e.message}")
            e.printStackTrace()
            return ""
        }
    }

}