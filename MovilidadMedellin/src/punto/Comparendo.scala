package punto

import movil._
import main.Simulacion

class Comparendo(vehiculo: Vehiculo, vel: Velocidad, vMax: Double) extends App {
  Simulacion.comparendos.append(this)
}