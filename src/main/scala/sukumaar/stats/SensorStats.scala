package sukumaar.stats

import sukumaar.NO_ARGS_ERR
import sukumaar.dto.{MeasurementDTO, StatsDTO}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  * This class actually does calculation of stats
  *
  * @author sukumaar
  */
class SensorStats {
  val csvRead: CSVRead = new CSVRead
  private val measurementMap: mutable.Map[String, ArrayBuffer[Int]] =
    mutable.Map[String, ArrayBuffer[Int]]()

  private def printStats(directoryPath: String): Unit = {
    val data: List[StatsDTO] = statsDataGenerator(directoryPath)
    println(
      s"""Num of processed files: ${csvRead.getFileProcessed}
Num of processed measurements: ${csvRead.getMeasurementProcessed}
Num of failed measurements: ${csvRead.getFailedMeasurements} \n
Sensors with highest avg humidity:\n
sensor-id,min,avg,max""")
    data.foreach(println)
    println()
  }

  def statsDataGenerator(directoryPath: String): List[StatsDTO] = {
    val measurements: List[MeasurementDTO] = csvRead.directoryToMeasurementDTO(
      directoryPath)
    measurements.foreach { m =>
      measurementMap.get(m.sensorId) match {
        case Some(humidityArr) => humidityArr += m.humidity.toInt
        case None =>
          measurementMap.put(m.sensorId, ArrayBuffer(m.humidity.toInt))
      }
    }
    val statsDTOList: List[StatsDTO] = measurementMap
      .map {
        case (key, value) =>
          StatsDTO(key,
            s"${value.min}",
            s"${value.sum / value.size}",
            s"${value.max}")
      }.toList.sorted
    appendNaNRecordsToEnd(statsDTOList)
  }

  private def appendNaNRecordsToEnd(statsDTOList: List[StatsDTO]): List[StatsDTO] = {
    val allNaNSensors: List[String] =
      csvRead.getNaNSensors.toList.sorted diff measurementMap.keys.toList

    statsDTOList ::: allNaNSensors.map(sensorId => new StatsDTO(sensorId))
  }
}

/**
  * Companion object for SensorStats
  */
object SensorStats {
  def main(args: Array[String]): Unit = {
    if (args.isEmpty) {
      throw new IllegalArgumentException(NO_ARGS_ERR)
    }
    val path = args.head
    new SensorStats().printStats(path)
  }
}