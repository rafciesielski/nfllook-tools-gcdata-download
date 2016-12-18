package org.nfllook.tools.gcdata

import com.mongodb.MongoClientURI
import com.mongodb.client.MongoCollection
import org.litote.kmongo.KMongo
import org.litote.kmongo.findOneById
import org.litote.kmongo.getCollection
import org.nfllook.tools.generated.wsch.Schedule

data class WeekSchedule(val season: Int, val week: Int, val gamesIds: List<String>)

class ScheduleService(val season: Int, uri: String) {

    val collection: MongoCollection<Schedule>

    init {
        val client = KMongo.createClient(MongoClientURI(uri))
        val database = client.getDatabase("nfllookdb")
        collection = database.getCollection<Schedule>()
    }

    fun getSchedule(week: Int?): List<WeekSchedule> {
        if (week != null) {
            return listOf(getWeekSchedule(week))
        } else {
            return getFullSeason()
        }
    }

    private fun getFullSeason(): List<WeekSchedule> {
        val gameWeeks = mutableListOf<WeekSchedule>()
        for (week in 1..17) {
            gameWeeks.add(getWeekSchedule(week))
        }
        return gameWeeks
    }

    private fun getWeekSchedule(week: Int): WeekSchedule {
        val schedule = collection.findOneById("${season}_$week")
        if(schedule == null || schedule.games == null || schedule.games.isEmpty()) {
            throw Exception("Could not find schedule for: $week/$season!")
        } else {
            val gameIds = schedule.games.map { it -> it.id }
            return WeekSchedule(season, week, gameIds)
        }
    }
}