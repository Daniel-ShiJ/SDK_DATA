package com.kingnet.sdk.collectdata.utils

import com.kingnet.sdk.collectdata.CollectDataConfig
import com.kingnet.sdk.common.utils.LogUtils
import com.kingnet.sdk.common.utils.MetaUtils
import com.kingnet.sdk.common.utils.SpUtils

/**
 * Author:Daniel.ShiJ
 * Date:2024/10/12 13:36
 * Description:
 */
object DataMetaUtils {
    private const val APP_ID_KEY = "APP_ID"
    private const val DATA_TOPIC_KEY = "data_topic"
    private const val DATA_TOKEN_KEY = "data_token"
    private const val DATA_PROJECT_KEY = "data_project"

    /**
     * 保存appId
     */
    fun saveAppId() {
        CollectDataConfig.mContext?.apply {
//            val appId = MetaUtils.getMetaValue(this, APP_ID_KEY)
//            LogUtils.e("appId = $appId")
//            if (appId.isBlank()) {
//                throw IllegalStateException("cannot obtain meta APP_ID from AndroidManifest ")
//            }
//            SpUtils.save(this, APP_ID_KEY, appId)
        }
    }

    /**
     * 保存topic
     */
    fun saveTopic() {
        CollectDataConfig.mContext?.apply {
            val topic = MetaUtils.getMetaValue(this, DATA_TOPIC_KEY)
            LogUtils.e("topic = $topic")
            if (topic.isBlank()) {
                throw IllegalStateException("cannot obtain meta data_topic from AndroidManifest ")
            }
            SpUtils.save(this, DATA_TOPIC_KEY, topic)
        }
    }

    /**
     * 保存token
     */
    fun saveToken() {
        CollectDataConfig.mContext?.apply {
            val token = MetaUtils.getMetaValue(this, DATA_TOKEN_KEY)
            LogUtils.e("token = $token")
            if (token.isBlank()) {
                throw IllegalStateException("cannot obtain meta data_token from AndroidManifest ")
            }
            SpUtils.save(this, DATA_TOKEN_KEY, token)
        }
    }

    /**
     * 保存project
     */
    fun saveProject() {
        CollectDataConfig.mContext?.apply {
            val project = MetaUtils.getMetaValue(this, DATA_PROJECT_KEY)
            LogUtils.e("project = $project")
            if (project.isBlank()) {
                throw IllegalStateException("cannot obtain meta data_project from AndroidManifest ")
            }
            SpUtils.save(this, DATA_PROJECT_KEY, project)
        }
    }

    /**
     * 获取appId
     */
    fun getAppId() = SpUtils.getString(CollectDataConfig.mContext, APP_ID_KEY)

    /**
     * 获取topic
     */
    fun getTopic() = SpUtils.getString(CollectDataConfig.mContext, DATA_TOPIC_KEY)

    /**
     * 获取token
     */
    fun getToken() = SpUtils.getString(CollectDataConfig.mContext, DATA_TOKEN_KEY)

    /**
     * 获取project
     */
    fun getProject() = SpUtils.getString(CollectDataConfig.mContext, DATA_PROJECT_KEY)
}