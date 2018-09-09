package sukumaar.stats

import sukumaar.dto.StatsDTO

/**
  * Simple test utils class for [[SensorStatsTest]]
  */
object SensorStatsTestUtils {
  /**
    *
    * @param path
    * @return
    */
  def getPath(path: String): String = getClass.getResource(path).getFile

  /**
    *
    * @param ss                   instance of SensorStats class
    * @param data                 List of StatsDTO produced by SensorStats (i.e. Statistics Result)
    * @param statsList            List of StatsDTO provided to compare with result
    * @param fileProcessed        count of files processed to compare with result
    * @param measurementProcessed count of measurement processed to compare with result
    * @param failedMeasurements   count of failed measurement processed to compare with result
    * @return
    */
  def checkResult(ss: SensorStats, data: List[StatsDTO], statsList: List[StatsDTO],
                  fileProcessed: Int, measurementProcessed: Int, failedMeasurements: Int): Boolean = {
    assert(ss.csvRead.getFileProcessed == fileProcessed)
    assert(ss.csvRead.getMeasurementProcessed == measurementProcessed)
    assert(ss.csvRead.getFailedMeasurements == failedMeasurements)
    assert(data == statsList)
    true
  }
}
