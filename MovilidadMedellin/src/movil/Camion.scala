package movil

import punto.Velocidad
import scala.util.Random

class Camion(posc: Any, velocidad: Velocidad) extends Vehiculo(posc, velocidad) with MovimientoUniforme{
  
  for (i <- 0 to 5) {
    if (i == 0) {
      this.placa += "R"
    }
    
    else {
      this.placa += (65 + Random.nextInt(26)).toChar
    }
  }
  
}