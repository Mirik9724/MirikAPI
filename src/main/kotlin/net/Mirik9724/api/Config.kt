package net.Mirik9724.api

import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.nio.file.Files
import org.yaml.snakeyaml.DumperOptions

object Config {
    val yaml = Yaml(DumperOptions().apply {
        defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
    })

    fun cloneConfigFromJar(
        resourcePath: String,
        targetDir: File,
        newFileName: String = File(resourcePath).name
    ): File {
        val targetFile = File(targetDir, newFileName)

        val resourceInputStream: InputStream = object {}.javaClass.classLoader.getResourceAsStream(resourcePath)
            ?: throw IllegalArgumentException("Resource '$resourcePath' not found in JAR")
        val defaultConfigMap = yaml.load<Map<String, Any>>(resourceInputStream)
        resourceInputStream.close()

        if (!targetDir.exists()) {
            targetDir.mkdirs()
        }

        if (!targetFile.exists()) {
            val inputStream: InputStream = object {}.javaClass.classLoader.getResourceAsStream(resourcePath)
                ?: throw IllegalArgumentException("Resource '$resourcePath' not found in JAR")
            Files.copy(inputStream, targetFile.toPath())
            inputStream.close()
            return targetFile
        } else {
            val existingConfigMap = yaml.load<Map<String, Any>>(targetFile.inputStream()) ?: emptyMap()
            val mergedConfigMap = mergeMaps(defaultConfigMap, existingConfigMap)

            // Записываем ТОЛЬКО если были изменения
            if (mergedConfigMap != existingConfigMap) {
                targetFile.writeText(yaml.dump(mergedConfigMap))
            }

            return targetFile
        }
    }

    fun mergeMaps(defaultMap: Map<String, Any>, existingMap: Map<String, Any>): Map<String, Any> {
        val result = existingMap.toMutableMap()

        for ((key, defaultValue) in defaultMap) {
            val existingValue = existingMap[key]

            if (existingValue == null) {
                result[key] = defaultValue
            } else if (defaultValue is Map<*, *> && existingValue is Map<*, *>) {
                @Suppress("UNCHECKED_CAST")
                result[key] = mergeMaps(
                    defaultValue as Map<String, Any>,
                    existingValue as Map<String, Any>
                )
            }
        }

        return result
    }


    fun loadYamlConfig(file: File): Any? {
        if (!file.exists()) {
            throw IllegalArgumentException("Cann`t found file '${file.path}'")
        }

        FileInputStream(file).use { input ->
            return yaml.load(input)
        }
    }

    fun parseMapString(str: String): Map<String, String> {
        return str
            .removePrefix("{")
            .removeSuffix("}")
            .split(", ")
            .map { it.split("=", limit = 2) }
            .associate { (key, value) -> key to value }
    }

    fun getData(configFile: File, key: String): String {
        val data = loadYamlConfig(configFile) ?: return "Not key"
        val keys = key.split(".")
        var current: Any? = data

        for (k in keys) {
            if (current is Map<*, *>) {
                current = current[k]
            } else {
                return "Not key"
            }
        }
        return current?.toString() ?: "Not key"
    }


    fun saveYamlToFile(file: File, data: Map<String, Any>) {
        file.parentFile?.mkdirs()
        file.writer(Charsets.UTF_8).use {
            yaml.dump(data, it)
        }
    }

}
