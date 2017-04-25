
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

  "cold food with any drinks bill" should "should incur the cold food service charge (10%)" in {
    Bill(items = Seq(Cola, CheeseSandwich)).total shouldBe BigDecimal("2.75")
  }

  "hot food with any other items" should "should incur the hot food service charge (20%)" in {
    Bill(items = Seq(Cola, SteakSandwich, CheeseSandwich)).total shouldBe BigDecimal("8.40")
  }

  "bill with hot food and service charge more than £20" should "have service charge capped at £20" in {
    val massiveBill = Seq.fill(23){SteakSandwich}
    val serviceCharge = Bill(items = massiveBill).total - Bill(items = massiveBill).grossTotal

    serviceCharge shouldBe BigDecimal("20.00")
  }

  "bill without hot food and service charge more than £20" should "have service charge not capped (*)" in {
    val massiveBill = Seq.fill(101){CheeseSandwich}
    val serviceCharge = Bill(items = massiveBill).total - Bill(items = massiveBill).grossTotal

    serviceCharge shouldBe BigDecimal("20.20")
  }

  /*
      (*) Ive takes the spec literally here (even though is seems illogical) - it only mentions the cap in the hot food / 20% rule,
      not in the food / 10% rule - so that is what I have implemented.

      I kept the original (excluding service charge) method public to facilitate a more incremental approach to testing
      and (consequently) implementation: I found it helps at times to be able to test the pure totting up separately from the
      service charge calculation.

      Also I could not think of any test data that would cause rounding when calculating the service charge so I have not
      implemented it. It would I think require adding an additional menu item priced something like 3.33 which is
      outside the spec.
   */
}
