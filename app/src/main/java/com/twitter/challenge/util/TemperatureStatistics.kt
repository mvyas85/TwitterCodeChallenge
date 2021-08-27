package com.twitter.challenge.util

import kotlin.math.pow
import kotlin.math.sqrt

/**
 * This class responsibility is to calculate the standard deviation from
 * the list of 5 days weather temperature
 */
class TemperatureStatistics {
    companion object {
        fun standardDeviation(data: List<Double>): Double {
            val average = data.sum().div(data.size)
            return sqrt(data.map { temp -> (temp - average).pow(2.0) }.sum()
                    .div
                    (data.size))
        }
    }

}