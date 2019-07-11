package movil


class Vehiculo(placa: String, pos: Any, vel: Any) extends Movil(pos,vel) with MovimientoUniforme {
  def aumentarPosc(posc: Any)  
  
  def direccionAngulo
  
  def formaAumentoPosicion
}