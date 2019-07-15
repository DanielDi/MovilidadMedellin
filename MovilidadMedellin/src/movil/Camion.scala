package movil

import punto.Velocidad
import punto.Interseccion
import scala.util.Random

class Camion(posInicial: Interseccion, posFinal: Interseccion, velocidad: Velocidad) 
extends Vehiculo(posInicial, posFinal, velocidad) with MovimientoUniforme{
  
  for (i <- 0 to 5) {
    if (i == 0) {
      this.placa += "R"
    }
    
    else {
      this.placa += (65 + Random.nextInt(26)).toChar
    }
  }
  
}