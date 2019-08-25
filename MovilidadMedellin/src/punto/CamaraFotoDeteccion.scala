package punto

import main.Simulacion
import movil.Vehiculo

class CamaraFotoDeteccion(via: Via, distOrigen: Double) {
  
  Simulacion.camaras.append(this)
  via.camara = Some(this)
  
  val angulo = {if(via.interO.xI > via.interF.xI) via.anguloVia + math.Pi
                else if (via.interO.xI == via.interF.xI && via.interO.yI > via.interF.yI) via.anguloVia + math.Pi
                else via.anguloVia
  }
  val x = via.interO.xI + distOrigen*Math.cos(angulo)
  val y = via.interO.yI + distOrigen*Math.sin(angulo)
  
  def deteccionVelocidad(vehiculo: Vehiculo, via: Via, posInicial: Interseccion){
    if(vehiculo.vel.magnitud > via.velocidad) new Comparendo(vehiculo,vehiculo.vel,via.velocidad)
    println("SE HIZO FOTO MULTA")
  }
}
