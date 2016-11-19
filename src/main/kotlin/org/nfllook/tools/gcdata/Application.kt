package org.nfllook.tools.gcdata

import com.evalab.core.cli.Command
import com.evalab.core.cli.exception.OptionException

private val SEASON = "season"
private val WEEK = "week"
private val PATH = "path"

fun main(args: Array<String>) {
    // seasons: 2009 - now, weeks: 1 - 17
    // --season=2009 --week=1 --path=F:/Data/NFLGameData
    // --season=2009 --path=F:/Data/NFLGameData
    val command = Command("download", "Download nfl.com Game Center Data")
    command.addIntegerOption(SEASON, true, 's', "Sets season")
    command.addIntegerOption(WEEK, false, 'w', "Sets week")
    command.addStringOption(PATH, true, 'p', "Sets path to data directory")

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
    println("Season: $season, week: $week, path: $path")

    GameDataDownloader.download(path!!, season!!, week)
}