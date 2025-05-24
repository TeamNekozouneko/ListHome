package com.nekozouneko.listHome

import com.nekozouneko.listHome.commands.ListHomeCommand
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class ListHome : JavaPlugin() {
    companion object{
        lateinit var instance: JavaPlugin
        var isSetHomeEnabled: Boolean = false
    }

    override fun onEnable() {
        instance = this
        isSetHomeEnabled = Bukkit.getPluginManager().isPluginEnabled("SetHome")

        if(!isSetHomeEnabled){
            logger.severe("SetHomeプラグインが有効ではないようです。")
            logger.severe("プラグインを停止します...")
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }

        getCommand("listhome")?.setExecutor(ListHomeCommand())
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

}
