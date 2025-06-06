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
        val targetFile = File(targetDir, newFileName)

        // Загружаем YAML из ресурсов (шаблон)
        val yaml = Yaml()
        val resourceInputStream: InputStream = object {}.javaClass.classLoader.getResourceAsStream(resourcePath)
            ?: throw IllegalArgumentException("Resource '$resourcePath' not found in JAR")

        val defaultConfigMap = yaml.load<Map<String, Any>>(resourceInputStream)
        resourceInputStream.close()

        if (!targetDir.exists()) {
            targetDir.mkdirs()
        }

        if (!targetFile.exists()) {
            // Файла нет — просто копируем из ресурсов
            val inputStream: InputStream = object {}.javaClass.classLoader.getResourceAsStream(resourcePath)
                ?: throw IllegalArgumentException("Resource '$resourcePath' not found in JAR")
            Files.copy(inputStream, targetFile.toPath())
            inputStream.close()
            return targetFile
        } else {
            // Файл есть — читаем его
            val existingConfigMap = yaml.load<Map<String, Any>>(targetFile.inputStream()) ?: emptyMap()

            // Проверяем, есть ли ключи из defaultConfigMap в существующем конфиге
            val mergedConfigMap = mergeMaps(defaultConfigMap, existingConfigMap)

            // Записываем обратно обновленный конфиг
            targetFile.writeText(yaml.dump(mergedConfigMap))
            return targetFile
        }
    }

    // Рекурсивно сливаем defaultMap в existingMap — добавляем недостающие ключи
    fun mergeMaps(defaultMap: Map<String, Any>, existingMap: Map<String, Any>): Map<String, Any> {
        val result = existingMap.toMutableMap()

        for ((key, defaultValue) in defaultMap) {
            val existingValue = existingMap[key]

            if (existingValue == null) {
                // Ключ отсутствует, добавляем из дефолта
                result[key] = defaultValue
            } else if (defaultValue is Map<*, *> && existingValue is Map<*, *>) {
                // Оба значения — карты, сливаем рекурсивно
                @Suppress("UNCHECKED_CAST")
                result[key] = mergeMaps(
                    defaultValue as Map<String, Any>,
                    existingValue as Map<String, Any>
                )
            }
            // Если ключ есть и не map — оставляем как есть (существующий)
        }

        return result
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
