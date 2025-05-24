package com.nekozouneko.listHome.commands

import com.nekozouneko.listHome.ListHome
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileInputStream
import java.util.UUID

class ListHomeCommand : CommandExecutor {
    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        val player = p0 as? Player
        if(player == null){
            p0.sendMessage("§cプレイヤーのみが実行できるコマンドです。")
            return true
        }

        val homes = getHomes(player.uniqueId)
        if(homes == null){
            player.sendMessage("§cホーム一覧の取得中にエラーが発生しました。")
        }else{
            player.sendMessage("§7[§eListHome§7] §aあなたが登録しているホームは以下の通りです。")
            homes.forEach { player.sendMessage("§7・§f${it}") }
            player.sendMessage("§c※反映まで最大24時間かかります。")

        }
        return true
    }
    data class Home(
        val world: String,
        val x: Double,
        val y: Double,
        val z: Double,
        val yaw: Double,
        val pitch: Double
    )
    data class Homes(
        var homes: Map<String, List<Map<String, Any>>>
    )
    private fun getHomes(uuid: UUID): Set<String>?{
        try{
            val yaml = Yaml()
            val parentPath = ListHome.instance.server.worldContainer.canonicalPath
            val dataPath = parentPath + File.separator + "plugins" + File.separator + "SetHome" + File.separator + "homes.yml"
            val inputStream = FileInputStream(dataPath)
            val data = yaml.load(inputStream) as Map<String, Map<String, Home>>
            return data.get(uuid.toString())?.keys
        }catch (e: Exception){ e.printStackTrace() }
        return null
    }
}