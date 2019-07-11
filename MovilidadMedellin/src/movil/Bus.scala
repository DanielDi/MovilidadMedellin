package movil

import punto.Velocidad

class Bus(placa: String, posc: Any, velocidad: Velocidad) 
extends Vehiculo(placa,posc,velocidad) with MovimientoUniforme{
  
}