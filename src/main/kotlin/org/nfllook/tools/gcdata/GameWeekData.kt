package org.nfllook.tools.gcdata

import org.jdom2.input.SAXBuilder
import org.jonnyzzz.kotlin.xml.bind.XAttribute
import org.jonnyzzz.kotlin.xml.bind.XElements
import org.jonnyzzz.kotlin.xml.bind.jdom.JDOM
import org.jonnyzzz.kotlin.xml.bind.jdom.JXML

data class GameWeek(val season: Int, val week: Int, val gamesIds: List<String>)

class WeekSchedule {
    val attrGamesIds by JXML / "gms" / XElements("g") / XAttribute("eid")
    fun getGamesIds() = if(attrGamesIds != null) {
        attrGamesIds!!
    } else {
        println("No data! Check xml: http://www.nfl.com/ajax/scorestrip?season=2016&seasonType=REG&week=1")
        emptyList()
    }
}

object GameWeekData {

    fun getGameWeeks(season: Int, week: Int?): List<GameWeek> {
        if (week != null) {
            return getOneWeek(season, week)
        } else {
            return getFullSeason(season)
        }
    }

    private fun getFullSeason(season: Int): List<GameWeek> {
        val gameWeeks = mutableListOf<GameWeek>()
        for (week in 1..17) {
            val weekSchedule = getWeekSchedule(season, week)
            gameWeeks.add(GameWeek(season, week, weekSchedule.getGamesIds()))
        }
        return gameWeeks
    }

    private fun getOneWeek(season: Int, week: Int): List<GameWeek> {
        val weekSchedule = getWeekSchedule(season, week)
        return listOf(GameWeek(season, week, weekSchedule.getGamesIds()))
    }

    private fun getWeekSchedule(season: Int, week: Int): WeekSchedule {
        val url = "http://www.nfl.com/ajax/scorestrip?season=$season&seasonType=REG&week=$week"
        val doc = SAXBuilder().build(url)
        return JDOM.load(doc.rootElement, WeekSchedule::class.java)
    }
}