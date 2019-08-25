package movil

import main.{Simulacion=> s}
import punto.Interseccion
import main.GrafoVia
import semaforo._
import punto.Via
import punto.CamaraFotoDeteccion

import scala.collection.mutable.Queue

case class Viaje(var vehiculo: Vehiculo)(var posInicial: Interseccion, var posFinal: Interseccion) extends MovimientoUniforme {
  
  var path = Queue(GrafoVia.menorCamino(posInicial, posFinal).map(_.toOuter).toSeq : _*) //Convierte nodos a intersecciones
  var origen = posInicial.copy()
  var radioLimite = vehiculo.vel.magnitud*s.dt 
  var auxPos = this.path.dequeue().copy()
  
  def aumentarPosc(dt: Int) = {
     var posAntX = posInicial.xI
    var posAntY = posInicial.yI
    radioLimite = vehiculo.vel.magnitud*s.dt
    if(!path.isEmpty){
      var nodo= s.arrayDeNodoSema.filter(_.inter == path.front)(0)
      var viaac = s.arrayDeVias.filter(v => (v.interO == auxPos) && (v.interF == path.front)) //viaac = via_actual
      if(viaac.size == 0) viaac= s.arrayDeVias.filter(v => (v.interO == path.front) && (v.interF == auxPos)) 
		  var sema = nodo.arraySemaforo.filter(_.via == viaac(0))(0)
      var viaAux = s.arrayDeVias.filter(p => p.interO == auxPos && p.interF == path.front)
      if(viaAux.isEmpty) viaAux = s.arrayDeVias.filter(p => p.interF == auxPos && p.interO == path.front)
    	var d= distEntreIntersec(posInicial,path.front)
      
    	if(((d <= s.XSemaforoF && d > s.XSemaAmaC) && (sema.estado =="Rojo" || sema.estado =="Amarillo")) || 
    	    (d<=s.XSemaAmaC && sema.estado == "Rojo")){
    	  vehiculo.vel.magnitud= {
    	    if (d > 0) {
    	      if(vehiculo.vel.magnitud < d ) vehiculo.vel.magnitud - (math.pow(vehiculo.vel.magnitud,2)/d)*dt
    	      else {
    	    	  posInicial = path.front.copy()
    	    	  0      	        
    	      }
    	    }
    	    else 0
    	  }
    	  
    	  if (vehiculo.vel.magnitud < 1) {
    	    vehiculo.vel.magnitud = 0
    	    posInicial = path.front.copy()
    	  }
			var tup = formaAumentoPosicion(vehiculo.vel, dt)
						posInicial.xI += tup._1
						posInicial.yI += tup._2
			}else{
				if (d > radioLimite) {
				  var auxvel= vehiculo.vel.magnitud + vehiculo.tasaAc*dt
					vehiculo.vel.magnitud = if(auxvel > vehiculo.velCru) vehiculo.velCru else auxvel 			  
			    var tup = formaAumentoPosicion(vehiculo.vel, dt)
				  posInicial.xI += tup._1
				  posInicial.yI += tup._2
				}
			 
				else {
				  posInicial = path.front.copy()
				  auxPos=path.dequeue()
  				if(!path.isEmpty) vehiculo.vel.direccion.grado = vehiculo.direccionAngulo(posInicial, path)			  
				}
				  
		  }
      if(viaAux(0).camara.isDefined) viaAux(0).camara.get.deteccionVelocidad(vehiculo, viaAux(0),posAntX,posAntY ,posInicial)
    }
    
  }
  
  def distEntreIntersec(actual: Interseccion, fin: Interseccion)= {
    math.pow((math.pow((fin.xI - actual.xI),2) + math.pow((fin.yI - actual.yI),2)),0.5)
  }
  
}