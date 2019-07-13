package movil

import punto.Velocidad // (magnitud, angulo)
import main.Simulacion
import punto.Velocidad
import punto.Angulo

class Vehiculo(pos: Any, vel: Velocidad) extends Movil(pos,vel) with MovimientoUniforme {
  
  var placa: String = ""
  
  def aumentarPosc(posc: Any) ={}
  
}

