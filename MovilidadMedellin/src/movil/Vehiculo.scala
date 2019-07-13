package movil

import punto.Velocidad

class Vehiculo(placa: String, pos: Any, vel: Velocidad) extends Movil(pos,vel) with MovimientoUniforme {
  def aumentarPosc(posc: Any)  
  
  def direccionAngulo
  
  def formaAumentoPosicion
}