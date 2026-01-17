package inits

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin

@Plugin(
    id = "mirikapi",
    name = "MirikAPI",
    version = "Unknown",
    authors = ["Mirik9724"]
)
class velocity(){
    @Subscribe
    fun onProxy(event: ProxyInitializeEvent){
        init()
    }
}