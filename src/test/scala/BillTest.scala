
import org.scalatest.{FlatSpec, Matchers}

class SetSpec extends FlatSpec with Matchers {

  "an empty bill" should "have no cost" in {
    Bill(items = Seq.empty).grossTotal shouldBe BigDecimal("0")
  }

  "single items" should "have correct single item cost" in {
    Bill(items = Seq(Cola)).grossTotal shouldBe BigDecimal("0.50")
    Bill(items = Seq(Coffee)).grossTotal shouldBe BigDecimal("1.00")
    Bill(items = Seq(CheeseSandwich)).grossTotal shouldBe BigDecimal("2.00")
    Bill(items = Seq(SteakSandwich)).grossTotal shouldBe BigDecimal("4.50")
  }

  "multiple items" should "have cost which is sum of item costs" in {
    Bill(items = Seq(Cola, Coffee, CheeseSandwich)).grossTotal shouldBe BigDecimal("3.50")
    Bill(items = Seq(Cola, Coffee, Coffee)).grossTotal shouldBe BigDecimal("2.50")
  }

  "an empty bill" should "should have no cost including service charge" in {
    Bill(items = Seq.empty).total shouldBe BigDecimal("0")
  }

  "all drinks bill" should "not incur service charge" in {
    Bill(items = Seq(Cola, Coffee)).total shouldBe BigDecimal("1.50")
  }

  "cold food single item" should "should incur service charge of 10%" in {
    Bill(items = Seq(CheeseSandwich)).total shouldBe BigDecimal("2.20")
  }

  "hot food single item" should "should incur service charge of 20%" in {
    Bill(items = Seq(SteakSandwich)).total shouldBe BigDecimal("5.40")
  }
}
