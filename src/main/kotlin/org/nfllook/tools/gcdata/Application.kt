package org.nfllook.tools.gcdata

import com.evalab.core.cli.Command
import com.evalab.core.cli.exception.OptionException

private val SEASON = "season"
private val WEEK = "week"
private val PATH = "path"
private val URI = "uri"

fun main(args: Array<String>) {

    val command = Command("download", "Download nfl.com Game Center Data")
    command.addIntegerOption(SEASON, true, 's', "Sets season")
    command.addIntegerOption(WEEK, false, 'w', "Sets week")
    command.addStringOption(PATH, true, 'p', "Sets path to data directory")
    command.addStringOption(URI, true, 'u', "Sets MongoDB connection string")

    try {
        command.parse(args)
    } catch (ex: OptionException) {
        println(ex.message)
        println(command.getHelp())
        System.exit(2)
    }

    val season = command.getIntegerValue(SEASON)
    val week = command.getIntegerValue(WEEK)
    val path = command.getStringValue(PATH)
    val uri = command.getStringValue(URI)
    println("Season: $season, week: $week, path: $path, uri: ${uri!!.replace(Regex("//.*?@"), "//<user>:<pass>@")}")

    GameDataDownloader.download(path!!, season!!, week, uri)
}