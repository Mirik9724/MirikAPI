package net.Mirik9724.api

import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.nio.file.Files

object Config {

    fun cloneConfigFromJar(
        resourcePath: String,
        targetDir: File,
        newFileName: String = File(resourcePath).name
    ): File {
        val inputStream: InputStream = object {}.javaClass.classLoader.getResourceAsStream(resourcePath)
            ?: throw IllegalArgumentException("Resource '$resourcePath' not found in JAR")

        if (!targetDir.exists()) {
            targetDir.mkdirs()
        }

        val targetFile = File(targetDir, newFileName)
        Files.copy(inputStream, targetFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING)
        inputStream.close()
        return targetFile
    }

    fun loadYamlConfig(file: File): Any? {
        if (!file.exists()) {
            throw IllegalArgumentException("Cann`t found file '${file.path}'")
        }

        val yaml = Yaml()
        FileInputStream(file).use { input ->
            return yaml.load(input)
        }
    }
}
