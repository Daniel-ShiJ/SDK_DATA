package com.kingnet.sdk.network.responses.converter

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.kingnet.sdk.network.responses.NetworkResponse
import okhttp3.ResponseBody
import retrofit2.Converter

/**
 * Author:Daniel.ShiJ
 * Date:2023/2/28 11:39
 * Description:自定义converter
 */
class GsonResponseBodyConverter<T:Any>(private val gson: Gson,private val adapter: TypeAdapter<T>):Converter<ResponseBody,NetworkResponse<T>> {
    override fun convert(value: ResponseBody): NetworkResponse<T>? {
        val jsonReader = gson.newJsonReader(value.charStream())
        value.use {
            jsonReader.beginObject()
            var code = 0
            var msg = ""
            var data:T?=null
            while (jsonReader.hasNext()){
                when(jsonReader.nextName()){
                    "code" -> code = jsonReader.nextInt()
                    "msg" -> msg = jsonReader.nextString()
                    "data" -> data = adapter.read(jsonReader)
                    else -> jsonReader.skipValue()
                }
            }
            jsonReader.endObject()

            return if(code != 0){
                NetworkResponse.BizError(code,msg)
            }else if(data == null){
                NetworkResponse.Success(Any()) as NetworkResponse<T>
            }else{
                NetworkResponse.Success(data)
            }
        }
    }
}