package movil

import main.Simulacion
import punto.Interseccion
import main.GrafoVia
import punto.Via
import punto.Comparendo

import scala.collection.mutable.Queue

case class Viaje(var vehiculo: Vehiculo)(var posInicial: Interseccion, var posFinal: Interseccion) extends MovimientoUniforme {
  
  var path = Queue(GrafoVia.menorCamino(posInicial, posFinal).map(_.toOuter).toSeq : _*) //Convierte nodos a intersecciones
  var origen = posInicial.copy()
  var auxPos = this.path.dequeue().copy()

  var radioLimite = vehiculo.vel.magnitud*Simulacion.dt
   
  def aumentarPosc(dt: Int) = {
    if(!path.isEmpty){
      var viaAux = Simulacion.arrayDeVias.filter(p => p.interO == auxPos && p.interF == path.front)
      if(viaAux.isEmpty) viaAux = Simulacion.arrayDeVias.filter(p => p.interF == auxPos && p.interO == path.front)
    	var d= distEntreIntersec(posInicial,path.front)
    	var posAntX = posInicial.xI
    	var posAntY = posInicial.yI
			if(d>radioLimite){
				var tup = formaAumentoPosicion(vehiculo.vel, dt)
						posInicial.xI += tup._1
						posInicial.yI += tup._2
			}else{
				posInicial = path.front.copy()
				auxPos= path.dequeue()
				if(!path.isEmpty) vehiculo.vel.direccion.grado = vehiculo.direccionAngulo(posInicial, path) 
			}
      deteccionVelocidad(vehiculo, viaAux(0),posAntX,posAntY ,posInicial)
    }
    
  }
  
  def deteccionVelocidad(vehiculo: Vehiculo, via: Via, posX: Double, posY: Double, posF: Interseccion){
    if(via.camara.isDefined){
      var cam = via.camara.get
      println(s"posO ${posY} posF ${posF.yI} cam ${cam.y}")
      var disAB = math.pow((math.pow((posX - posF.xI),2) + math.pow((posY - posF.yI),2)),0.5)
      var disAC = math.pow((math.pow((cam.x - posX),2) + math.pow((cam.y - posY),2)),0.5)
      var disBC = math.pow((math.pow((cam.x - posF.xI),2) + math.pow((cam.y - posF.yI),2)),0.5)
      if(disAB>=disAC && disAB > disBC){
        if(vehiculo.vel.magnitud > via.velocidad){ new Comparendo(vehiculo,vehiculo.vel,via.velocidad)
          println(s"SE HIZO FOTO MULTA: vehiculo = ${vehiculo.vel.magnitud} via = ${via.velocidad}")}
        else{println(s"VA BIEN: vehiculo = ${vehiculo.vel.magnitud} via = ${via.velocidad}")}
        println()
      }
    }
  }
  
  def hallarVia(interO: Interseccion, interF: Interseccion){
    var viaAux = Simulacion.arrayDeVias.filter(p => p.interO == interO && p.interF == interF)
    if(viaAux.isEmpty) viaAux = Simulacion.arrayDeVias.filter(p => p.interF == interO && p.interO == interF)
    else viaAux = Simulacion.arrayDeVias
    viaAux(0)
  }
  
  def distEntreIntersec(actual: Interseccion, fin: Interseccion)= {
    math.pow((math.pow((fin.xI - actual.xI),2) + math.pow((fin.yI - actual.yI),2)),0.5)
  }
  
}