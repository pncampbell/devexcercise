
sealed abstract class Item (description: String, val price: BigDecimal)
// although description isn't used yet it provides if nothing else a cut and dried mapping to the stated problem domain

case object Cola extends Item("Cola", BigDecimal("0.50"))
case object Coffee extends Item("Coffee", BigDecimal("1.00"))
case object CheeseSandwich extends Item("Cheese Sandwich", BigDecimal("2.00"))
case object SteakSandwich extends Item("Steak Sandwich", BigDecimal("4.50"))

case class Bill(items: Seq[Item]) {
  def total : BigDecimal = items.map(_.price).fold(BigDecimal(0)){ (p1, p2) =>  p1 + p2}
}

