package sukumaar.stats

import java.io.File

import sukumaar.dto.MeasurementDTO
import sukumaar.{HEADER, MISSING_DATA_ERR_MSG, PATH_EXISTS_ERR_MSG}

import scala.collection.mutable
import scala.io.BufferedSource

/**
  * This class read CSV files into [[MeasurementDTO]]
  *
  * @author sukumaar
  */
class CSVRead {
  private val NaNSensors: mutable.Set[String] = mutable.Set[String]()
  private var fileProcessed: Int = 0
  private var measurementProcessed: Int = 0
  private var failedMeasurements: Int = 0

  def getFileProcessed: Int = fileProcessed

  def getMeasurementProcessed: Int = measurementProcessed

  def getFailedMeasurements: Int = failedMeasurements

  def getNaNSensors: mutable.Set[String] = NaNSensors

  /**
    *
    * @param directoryPath absolute path of directory having CSV files created by group leader
    * @return List[MeasurementDTO] which represents list of sensor-id,humidity
    */
  def directoryToMeasurementDTO(directoryPath: String): List[MeasurementDTO] = {
    val csvDirectory: File = new File(directoryPath)
    require(csvDirectory.isDirectory && csvDirectory.exists(), PATH_EXISTS_ERR_MSG)
    val csvFileList: List[File] = csvDirectory.listFiles().toList.filter(_.getName.endsWith(".csv"))
    val measurements: List[MeasurementDTO] = csvFileList.flatMap(csvFile => csvFileToMeasurementDTO(csvFile))
    fileProcessed = csvFileList.length
    measurements
  }

  /**
    *
    * @param csvFile java.io.File pointing to physical parent directory of csv files
    * @return List[MeasurementDTO] which represents list of sensor-id,humidity
    */
  def csvFileToMeasurementDTO(csvFile: File): List[MeasurementDTO] = {
    val bufferedSource: BufferedSource = scala.io.Source
      .fromFile(csvFile)
    val totalReceivedMeasurements: List[MeasurementDTO] = bufferedSource
      .getLines()
      .toList
      .map { record =>
        val arr: Array[String] = record.split(",")
        require(arr.length == 2, MISSING_DATA_ERR_MSG)
        MeasurementDTO(arr(0), arr(1))
      }
      .filterNot(_ == HEADER)
    bufferedSource.close()
    val filtered: List[MeasurementDTO] = totalReceivedMeasurements.filterNot {
      measurementProcessed =>
        isHumidityInvalid(measurementProcessed)
    }
    measurementProcessed += totalReceivedMeasurements.length
    failedMeasurements += totalReceivedMeasurements.length - filtered.length
    filtered
  }

  private def isHumidityInvalid(measurementProcessed: MeasurementDTO): Boolean = {
    val result = measurementProcessed.humidity.equals("NaN") ||
      (100 < measurementProcessed.humidity.toInt && measurementProcessed.humidity.toInt < 0)

    if (result) {
      NaNSensors += measurementProcessed.sensorId
    }
    result
  }
}
