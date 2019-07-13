package movil

import punto.Velocidad

class Carro(placa: String, posc: Any, velocidad: Velocidad) extends Vehiculo(placa, posc, velocidad) with MovimientoUniforme{
  
}