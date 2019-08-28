package punto

import movil._
import main.Simulacion

class Comparendo(val vehiculo: Vehiculo, val vMax: Double){
  var vel = vehiculo.vel.magnitud
  Simulacion.comparendos.append(this)
}