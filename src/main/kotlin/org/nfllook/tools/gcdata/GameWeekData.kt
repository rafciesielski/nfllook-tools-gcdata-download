package org.nfllook.tools.gcdata

import org.jdom2.Document
import org.jdom2.input.SAXBuilder
import org.jdom2.output.XMLOutputter
import org.jonnyzzz.kotlin.xml.bind.XAttribute
import org.jonnyzzz.kotlin.xml.bind.XElements
import org.jonnyzzz.kotlin.xml.bind.jdom.JDOM
import org.jonnyzzz.kotlin.xml.bind.jdom.JXML
import java.io.File

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

class GameWeekData(val path: String, val season: Int) {

    fun getGameWeeks(week: Int?): List<GameWeek> {
        if (week != null) {
            return getOneWeek(week)
        } else {
            return getFullSeason()
        }
    }

    private fun getFullSeason(): List<GameWeek> {
        val gameWeeks = mutableListOf<GameWeek>()
        for (week in 1..17) {
            val weekSchedule = getWeekSchedule(week)
            gameWeeks.add(GameWeek(season, week, weekSchedule.getGamesIds()))
        }
        return gameWeeks
    }

    private fun getOneWeek(week: Int): List<GameWeek> {
        val weekSchedule = getWeekSchedule(week)
        return listOf(GameWeek(season, week, weekSchedule.getGamesIds()))
    }

    private fun getWeekSchedule(week: Int): WeekSchedule {
        val url = "http://www.nfl.com/ajax/scorestrip?season=$season&seasonType=REG&week=$week"
        val doc = SAXBuilder().build(url)
        saveWeekSchedule(week, doc)
        return JDOM.load(doc.rootElement, WeekSchedule::class.java)
    }

    private fun saveWeekSchedule(week: Int, doc: Document?) {
        val dir = "$path/$season/$week"
        File(dir).mkdirs()
        File("$dir/schedule.xml").writeText(XMLOutputter().outputString(doc))
    }
}