package movil

import punto.Velocidad

class Moto(placa: String, posc: Any, velocidad: Velocidad) extends Vehiculo(placa, posc, velocidad) with MovimientoUniforme{
  
}