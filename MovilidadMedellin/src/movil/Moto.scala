package movil

import punto.Velocidad
import scala.util.Random

class Moto(posc: Any, velocidad: Velocidad) extends Vehiculo(posc, velocidad) with MovimientoUniforme{
  
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