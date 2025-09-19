package com.mapbox.maps.testapp.examples.datajoin

import android.content.Context
import com.mapbox.maps.logE
import org.json.JSONArray
import java.io.IOException

data class Country(val code: String, val hdi: Double)

object CountriesData {

    /**
     * Loads a list of countries and their HDI values from a JSON file in the assets folder.
     * @param context The Android context used to access assets.
     * @return List of Country objects, or an empty list if loading/parsing fails.
     */
    fun loadCountriesFromJSON(context: Context): List<Country> {
        return try {
            val jsonString =
                context.assets.open("countries_hdi.json").bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(jsonString)

            return (0 until jsonArray.length())
                .map(jsonArray::getJSONObject)
                .map { jsonCountry ->
                    Country(
                        jsonCountry.getString("code"),
                        jsonCountry.getDouble("hdi"),
                    )
                }
        } catch (e: IOException) {
            logE("CountriesData", "Failed to read JSON file: ${e.message}")
            emptyList()
        } catch (e: Exception) {
            logE("CountriesData", "Failed to parse JSON: ${e.message}")
            emptyList()
        }
    }
}