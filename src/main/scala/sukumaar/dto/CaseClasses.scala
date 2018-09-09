package sukumaar.dto

/**
  * Measurements are getting read into this class
  *
  * @param sensorId
  * @param humidity
  */
case class MeasurementDTO(sensorId: String, humidity: String)

/**
  * Final Stats are represented via this class
  *
  * @param sensorId
  * @param min
  * @param avg
  * @param max
  */
case class StatsDTO(sensorId: String, min: String, avg: String, max: String) extends Ordered[StatsDTO] {

  def this(sensorId: String) {
    this(sensorId, "NaN", "NaN", "NaN")
  }

  //import scala.math.Ordered.orderingToOrdered

  def compare(that: StatsDTO): Int = that.avg compare this.avg

  override def toString: String = s"$sensorId,$min,$avg,$max"
}

