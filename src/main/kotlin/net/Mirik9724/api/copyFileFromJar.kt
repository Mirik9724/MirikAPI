package net.Mirik9724.api

import java.io.File
import java.io.InputStream
import java.nio.file.Files

/**
 * Copies a file from the plugin's JAR resources to a target directory on disk.
 */
fun copyFileFromJar(
    resourcePath: String,
    targetDir: String,
    clazz: Class<*>
): File {
    val targetFile = File(targetDir, File(resourcePath).name)

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