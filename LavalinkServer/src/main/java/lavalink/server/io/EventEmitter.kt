package lavalink.server.io

import dev.arbjerg.lavalink.api.IPlayer
import dev.arbjerg.lavalink.api.PluginEventHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class EventEmitter(private val context: SocketContext, private val listeners: Collection<PluginEventHandler>) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(EventEmitter::class.java)
    }

    fun onWebSocketOpen() = iterate { it.onWebSocketOpen(context) }
    fun onWebSocketClose() = iterate { it.onWebSocketClose(context) }
    fun onWebsocketMessageIn(message: String) = iterate { it.onWebsocketMessageIn(context, message) }
    fun onWebSocketMessageOut(message: String) = iterate { it.onWebSocketMessageOut(context, message) }
    fun onNewPlayer(player: IPlayer)  = iterate { it.onNewPlayer(context, player) }

    private fun iterate(func: (PluginEventHandler) -> Unit ) {
        listeners.forEach {
            try {
                func(it)
            } catch (e: Exception) {
                log.error("Error handling event", e)
            }
        }
    }

}