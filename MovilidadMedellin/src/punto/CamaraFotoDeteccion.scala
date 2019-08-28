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
  
  val x = via.interO.xI + distOrigen*Math.cos(angulo)*100
  val y = via.interO.yI + distOrigen*Math.sin(angulo)*100
  
  def deteccionVelocidad(vehiculo: Vehiculo, via: Via, posX: Double, posY: Double, posF: Interseccion){
      var disAB = math.pow((math.pow((posX - posF.xI),2) + math.pow((posY - posF.yI),2)),0.5)
      var disAC = math.pow((math.pow((x - posX),2) + math.pow((y - posY),2)),0.5)
      var disBC = math.pow((math.pow((x - posF.xI),2) + math.pow((y - posF.yI),2)),0.5)
      if(disAB>=disAC && disAB > disBC){
        if(vehiculo.vel.magnitud > via.velocidad) new Comparendo(vehiculo, via.velocidad)
      }
  }
}
