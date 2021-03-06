package org.nfllook.tools.gcdata

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.failure
import com.github.kittinunf.result.success
import java.io.File

object GameDataDownloader {

    fun download(path: String, season: Int, week: Int?, uri: String) {
        val schedule = ScheduleService(season, uri).getSchedule(week)
        println("Schedule: $schedule")
        downloadSeasonData(path, schedule)
        println("Done. Data downloaded to: $path")
    }

    private fun downloadSeasonData(path: String, schedule: List<WeekSchedule>) {
        for((season, week, gamesIds) in schedule) {
            println("Downloading season: $season, week: $week ...")
            for(gameId in gamesIds) {
                downloadWeekData(path, season, week, gameId)
            }
        }
    }

    private fun downloadWeekData(path: String, season: Int, week: Int, gameId: String) {
        val url = "http://www.nfl.com/liveupdate/game-center/$gameId/${gameId}_gtd.json"
        val (request, response, result) = url.httpGet().responseString()
        result.success {
            val dir = "$path/$season/$week"
            File(dir).mkdirs()
            File("$dir/$gameId.json").writeText(result.get())
        }
        result.failure {
            println("Failure: ")
            println("Request url: ${request.url}")
            println("Response: ${response.httpStatusCode}")
            println()
        }
    }
}