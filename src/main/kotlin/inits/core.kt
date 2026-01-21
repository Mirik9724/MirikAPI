package inits

import net.Mirik9724.api.BuildConstants.VERSION
import net.Mirik9724.api.isAvailableNewVersion
import net.Mirik9724.api.logInit

const val updateUrl = "https://raw.githubusercontent.com/Mirik9724/MirikAPI/refs/heads/main/V"
fun init(){
    val log = logInit("MAPI")
    if(isAvailableNewVersion(updateUrl, VERSION)){
        log.info("New version is available")
    }

    log.info("MAPI v${VERSION} - ON")
}
