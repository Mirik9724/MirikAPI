package inits

import net.Mirik9724.api.isAvailableNewVersion
import net.Mirik9724.api.logInit

const val updateUrl = "https://raw.githubusercontent.com/Mirik9724/MirikAPI/refs/heads/main/V"
const val version = "0.1.5.9"
fun init(){
    if(isAvailableNewVersion(updateUrl, version)){
        val log = logInit("New version is available")
    }
}
