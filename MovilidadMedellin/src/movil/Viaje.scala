package movil

import main.Simulacion
import punto.Interseccion
import main.GrafoVia

import scala.collection.mutable.Queue

case class Viaje(var vehiculo: Vehiculo)(var posInicial: Interseccion, var posFinal: Interseccion) extends MovimientoUniforme {
  
  var path = Queue(GrafoVia.menorCamino(posInicial, posFinal).map(_.toOuter).toSeq : _*) //Convierte nodos a intersecciones
  var origen = posInicial.copy()
  this.path.dequeue()
   var radioLimite = vehiculo.vel.magnitud*Simulacion.dt
  
  def aumentarPosc(dt: Int) = {
    
    if(!path.isEmpty){
    	var d= distEntreIntersec(posInicial,path.front)
			if(d>radioLimite){
				var tup = formaAumentoPosicion(vehiculo.vel, dt)
						posInicial.xI += tup._1
						posInicial.yI += tup._2
			}else{
				posInicial = path.front.copy()
				path.dequeue()
				if(!path.isEmpty) vehiculo.vel.direccion.grado = vehiculo.direccionAngulo(posInicial, path) 
			}
    }
  }
  
  def distEntreIntersec(actual: Interseccion, fin: Interseccion)= {
    math.pow((math.pow((fin.xI - actual.xI),2) + math.pow((fin.yI - actual.yI),2)),0.5)
  }
  
}