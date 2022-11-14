package com.example.testproject.utilsAndData

import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Date: 15/12/2021
 * @author [Riz1Ahmed](https://fb.com/Riz1Ahmed)
 * */
object GSonUtils {

    /**
     * Load json (.js) file from asset folder
     * @param filePath the .js file path. For example a template.js
     * file inside 'json' folder, then will value will like 'json/template.js'
     * @return return the loaded .js file as a JSON string.
     * You can use thi by [GSonUtils.fromJson] method.
     * */
    fun loadJsonFromAsset(assetManager: AssetManager, filePath: String) =
        assetManager.open(filePath).bufferedReader().use { it.readText() }

    /**
     * Convert data to json string.
     * @param data it would be any type of data. Would be
     * Int,String,Float or model data class EX: UserInfo,ProductModel etc.
     *
     * You will rollback your data by call fromJson function
     * with parameter of it's returned value.
     */
    fun <T> toJson(data: T): String? = Gson().toJson(data)

    /**
     * @param jsonData jsonString of T type data. T is your this json Data type.
     * It would be anything (data class, Int, String etc).
     *
     * how to call:
     * ```
     *      GSonUtils.fromJson<YourDataType>(JsonStrData)
     * ```
     *
     * Given two Example:
     *  ```
     *      val products = GSonUtils.fromJson<ArrayList<ProductData>>(productsJson)
     *      val products:ArrayList<ProductData> = GSonUtils.fromJson(productsJson)
     *  ```
     */
    inline fun <reified T> fromJson(jsonData: String): T {
        return Gson().genericFromJson<T>(jsonData)
    }

    /**
     * Json Generic type converter.
     *
     * Please do not call this method from anywhere.
     * It's actually inner method of this object
     * */
    inline fun <reified T> Gson.genericFromJson(jsonData: String): T {
        return fromJson(jsonData, object : TypeToken<T>() {}.type)
    }

}