package net.Mirik9724.api

import java.net.HttpURLConnection
import java.net.URL

/**
 * Checks if there is a new version.
 * @param urlWithTxtFile — URL to the version file, for example, "https://example.com/latest-version.txt"
 * @param thisVersion — Current version of the plugin or library, for example, "1.4.2"
 * @return true if the version on the site differs from the current one (there is a newer version)
 */
fun isAvailableNewVersion(urlWithTxtFile: String, thisVersion: String): Boolean {
    return try {
        val connection = URL(urlWithTxtFile).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = 5000
        connection.readTimeout = 5000

        val foundVersion = connection.inputStream.bufferedReader().use { it.readText().trim() }

        if (foundVersion == thisVersion) return false
        else return true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}