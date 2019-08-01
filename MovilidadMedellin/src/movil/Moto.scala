package movil

import punto.Velocidad
import punto.Interseccion
import scala.util.Random

class Moto(posInicial: Interseccion, posFinal: Interseccion, velocidad: Velocidad) 
extends Vehiculo(posInicial, posFinal, velocidad) with MovimientoUniforme{
  
  for (i <- 0 to 5) {
    if (i < 3) {
      this.placa += (65 + Random.nextInt(26)).toChar
    }
    
    else if (i > 2 & i < 5) {
      this.placa += Random.nextInt(10)
    }
    
    else {
      this.placa += (65 + Random.nextInt(26)).toChar
    }
  }
  
}