package punto

import movil._
import main.Simulacion

class Comparendo(vehiculo: Vehiculo, val vMax: Double) extends App {
  val vel = vehiculo.vel
  Simulacion.comparendos.append(this)
}