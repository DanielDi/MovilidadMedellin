package punto

class Velocidad(var magnitud: Double, var direccion: Angulo) {
  
}

object Velocidad {
  
  def conversionAKmH(magnitud: Double): Double = {
    var magnitudKmH = magnitud * 3.6
    magnitudKmH
  }
  
  def conversionAms(magnitud: Double): Double = {
    var magnitudMs = magnitud / 3.6
    magnitudMs
  }
  
}