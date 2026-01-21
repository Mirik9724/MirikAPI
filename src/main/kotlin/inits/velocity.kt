package inits

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import net.Mirik9724.api.BuildConstants

@Plugin(
    id = "mirikapi",
    name = "MirikAPI",
    version = BuildConstants.VERSION,
    authors = ["Mirik9724"]
)
class velocity(){
    @Subscribe
    fun onProxy(event: ProxyInitializeEvent){
        init()
    }
}