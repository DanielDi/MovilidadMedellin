package punto

import movil._
import main.Simulacion

class Comparendo(vehiculo: Vehiculo, var vMax: Double) extends App {
  Simulacion.comparendos.append(this)
  
  val vel = vehiculo.vel.magnitud
}