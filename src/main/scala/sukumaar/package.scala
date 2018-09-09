import sukumaar.dto.MeasurementDTO

package object sukumaar {
  val HEADER: MeasurementDTO = MeasurementDTO("sensor-id", "humidity")
  val PATH_EXISTS_ERR_MSG: String = "path does not have valid csv directory"
  val MISSING_DATA_ERR_MSG: String = "csv file must have both column as sensor-id,humidity"
  val NO_ARGS_ERR = "please provide the absolute directory path of csv files as an argument"
}
