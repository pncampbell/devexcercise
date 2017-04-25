
import org.scalatest.{FlatSpec, Matchers}

class SetSpec extends FlatSpec with Matchers {

  "an empty bill" should "have no cost" in {
    Bill(items = Seq.empty).total shouldBe BigDecimal("0")
  }

  "single items" should "have correct single item cost" in {
    Bill(items = Seq(Cola)).total shouldBe BigDecimal("0.50")
    Bill(items = Seq(Coffee)).total shouldBe BigDecimal("1.00")
    Bill(items = Seq(CheeseSandwich)).total shouldBe BigDecimal("2.00")
    Bill(items = Seq(SteakSandwich)).total shouldBe BigDecimal("4.50")
  }

  "multiple items" should "have cost which is sum of item costs" in {
    Bill(items = Seq(Cola, Coffee, CheeseSandwich)).total shouldBe BigDecimal("3.50")
    Bill(items = Seq(Cola, Coffee, Coffee)).total shouldBe BigDecimal("2.50")
  }
}
