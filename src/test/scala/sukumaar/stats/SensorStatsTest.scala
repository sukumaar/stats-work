package sukumaar.stats

import org.scalatest.Matchers._
import org.scalatest.{FunSuite, GivenWhenThen}
import sukumaar.dto.StatsDTO

class SensorStatsTest extends FunSuite with GivenWhenThen {

  test("only one record is NaN") {
    Given("stats list to compare with result")
    val csvURL: String = SensorStatsTestUtils.getPath("/csv-data-1")
    val statsList: List[StatsDTO] = List(
      StatsDTO("s2", "78", "82", "88"),
      StatsDTO("s1", "10", "54", "98"),
      new StatsDTO("s3"))

    When("Extract stats")
    val ss: SensorStats = new SensorStats
    val data: List[StatsDTO] = ss.statsDataGenerator(csvURL)

    Then("Should match to given stats list")
    assert(SensorStatsTestUtils.checkResult(ss, data, statsList, 2, 7, 2))
  }

  test("all records are NaN") {
    Given("stats list to compare with result")
    val statsList: List[StatsDTO] = List(
      new StatsDTO("s1"),
      new StatsDTO("s2"),
      new StatsDTO("s3"))

    When("Extract stats")
    val ss: SensorStats = new SensorStats
    val data: List[StatsDTO] = ss.statsDataGenerator(SensorStatsTestUtils.getPath("/csv-data-2"))

    Then("Should match to given stats list")
    assert(SensorStatsTestUtils.checkResult(ss, data, statsList, 2, 7, 7))
  }

  test("no record is NaN") {
    Given("stats list to compare with result")
    val statsList: List[StatsDTO] = List(
      StatsDTO("s2", "78", "82", "88"),
      StatsDTO("s1", "10", "54", "98"),
      StatsDTO("s3", "10", "10", "10"))

    When("Extract stats")
    val ss: SensorStats = new SensorStats
    val data: List[StatsDTO] = ss.statsDataGenerator(SensorStatsTestUtils.getPath("/csv-data-3"))

    Then("Should match to given stats list")
    assert(SensorStatsTestUtils.checkResult(ss, data, statsList, 2, 6, 0))
  }

  test("-ve test: csv files having only header and no data") {
    When("Extract stats")
    val ss: SensorStats = new SensorStats
    val data: List[StatsDTO] = ss.statsDataGenerator(SensorStatsTestUtils.getPath("/csv-data-4"))

    Then("Should match to given stats list")
    assert(SensorStatsTestUtils.checkResult(ss, data, Nil, 2, 0, 0))
  }

  test("-ve test: not passing any argument to main") {
    the[IllegalArgumentException] thrownBy SensorStats.main(Array()) should have message sukumaar.NO_ARGS_ERR
  }
}
