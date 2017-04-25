
object ServiceChargeValues {
  val DefaultRate = 0
  val FoodRate = 10
  val HotFoodRate = 20
  val ServiceChargeCap = 20
}

import ServiceChargeValues._

// although description isn't used yet it provides if nothing else a cut and dried mapping to the stated problem domain
sealed abstract class Item (description: String, val price: BigDecimal) {
  def serviceChargeRate: Int = DefaultRate
}

sealed abstract class Food (description: String, price: BigDecimal, val isHot: Boolean) extends Item(description, price) {
  override def serviceChargeRate: Int = if(isHot) HotFoodRate else FoodRate
}

case object Cola extends Item("Cola", BigDecimal("0.50"))
case object Coffee extends Item("Coffee", BigDecimal("1.00"))
case object CheeseSandwich extends Food("Cheese Sandwich", BigDecimal("2.00"), isHot = false)
case object SteakSandwich extends Food("Steak Sandwich", BigDecimal("4.50"), isHot = true)

case class Bill(items: Seq[Item]) {


  def total: BigDecimal = grossTotal + calculatePercent(grossTotal, applicableServiceRate)

  def grossTotal: BigDecimal = items.map(_.price).fold(BigDecimal(0)){(p1, p2) =>  p1 + p2}

  private def applicableServiceRate: Int = {
    items.map(_.serviceChargeRate) match {
      case Seq() => DefaultRate
      case rates => rates.max
    }
  }

  private def calculatePercent(value: BigDecimal, percentage: Int): BigDecimal = value * (percentage / BigDecimal(100))
}

