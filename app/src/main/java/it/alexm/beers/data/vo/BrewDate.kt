package it.alexm.beers.data.vo

import java.util.*

data class BrewDate(
    val month: Int,
    val year: Int
) {

    private val c by lazy { Calendar.getInstance() }

    override fun toString() = "${month + 1}-$year"

    fun inMillis() = c.run {
        set(year, month, 1)
        timeInMillis
    }

    companion object {
        operator fun invoke(millis: Long?): BrewDate {
            val c = Calendar.getInstance()
            c.timeInMillis = millis ?: 0
            return BrewDate(
                c.get(Calendar.MONTH),
                c.get(Calendar.YEAR)
            )
        }
    }
}