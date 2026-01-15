package com.example

import net.Mirik9724.api.copyFileFromJar
import net.Mirik9724.api.loadYmlFile
import net.Mirik9724.api.log
import net.Mirik9724.api.logInit
import net.Mirik9724.api.tryCreatePath
import org.junit.jupiter.api.Test
import java.io.File

class UsageTest {
    @Test
    fun setup() {
        logInit("U.T.")
        log.info("Init test")

        val data = loadYmlFile("src/test/resources/data.yml")
        log.info(data["key1"])
        log.info(data["k.key2"])

        copyFileFromJar("data.yml", "src/test/resources", "data2.yml")
        val data2 = loadYmlFile("src/test/resources/data.yml")
        log.info(data2["key3"])

        tryCreatePath(File("scr/test/resources/TEST!!!"))
    }
}