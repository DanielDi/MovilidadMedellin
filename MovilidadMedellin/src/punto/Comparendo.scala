package punto

import movil._
import main.Simulacion

class Comparendo(val vehiculo: Vehiculo, val vMax: Double){
  val vel = vehiculo.vel
  Simulacion.comparendos.append(this)
}