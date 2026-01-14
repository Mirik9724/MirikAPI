package net.Mirik9724.api

import java.io.File
import java.io.InputStream
import java.nio.file.Files

/**
 * Copies a file from the plugin's JAR resources to a target directory on disk.
 *
 * @param resourcePath The path to the resource inside the JAR, e.g., "config/default.yml"
 * @param targetDir The directory on disk where the file should be copied
 * @param newFileName Optional: the name for the copied file. Defaults to the resource's file name
 * @return The File object pointing to the copied (or existing) file on disk
 * @throws IllegalArgumentException If the resource is not found inside the JAR
 */
fun copyFileFromJar(
    resourcePath: String,
    targetDir: String,
    newFileName: String = File(resourcePath).name
): File {
    val targetFile = File(targetDir, newFileName)

    val targetDirFile = File(targetDir)
    if (!targetDirFile.exists()) {
        targetDirFile.mkdirs()
    }

    if (!targetFile.exists()) {
        val inputStream: InputStream = object {}.javaClass.classLoader.getResourceAsStream(resourcePath)
            ?: throw IllegalArgumentException("Resource '$resourcePath' not found in JAR")
        Files.copy(inputStream, targetFile.toPath())
        inputStream.close()
    }

    return targetFile
}