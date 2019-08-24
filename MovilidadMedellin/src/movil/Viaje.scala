package movil

import main.{Simulacion=> s}
import punto.Interseccion
import main.GrafoVia
import semaforo._

import scala.collection.mutable.Queue

case class Viaje(var vehiculo: Vehiculo)(var posInicial: Interseccion, var posFinal: Interseccion) extends MovimientoUniforme {
  
  var path = Queue(GrafoVia.menorCamino(posInicial, posFinal).map(_.toOuter).toSeq : _*) //Convierte nodos a intersecciones
  var origen = posInicial.copy()
  var auxpos=this.path.dequeue() 
  var radioLimite = vehiculo.vel.magnitud*s.dt
  
  def aumentarPosc(dt: Int) = {
    
    if(!path.isEmpty){
      var nodo= s.arrayDeNodoSema.filter(_.inter == path.front)(0)
      var viaac = s.arrayDeVias.filter(v => (v.interO == auxpos) && (v.interF == path.front)) //viaac = via_actual
      if(viaac.size == 0) viaac= s.arrayDeVias.filter(v => (v.interO == path.front) && (v.interF == auxpos)) 
		  var sema = nodo.arraySemaforo.filter(_.via == viaac(0))(0)
    	var d= distEntreIntersec(posInicial,path.front)
    	if(((d<s.XSemaforoF && d> s.XSemaAmaC) && (sema.Estado =="Rojo" || sema.Estado =="Amarillo")) || (d<s.XSemaAmaC && sema.Estado == "Rojo")){
    	  vehiculo.vel.magnitud= vehiculo.vel.magnitud - (math.pow(vehiculo.vel.magnitud,2)/d)*dt
    	  var tup = formaAumentoPosicion(vehiculo.vel, dt)
						posInicial.xI += tup._1
						posInicial.yI += tup._2
//    	  posInicial = path.front.copy()
//				auxpos=path.dequeue()
//				if(!path.isEmpty) vehiculo.vel.direccion.grado = vehiculo.direccionAngulo(posInicial, path) 
			}else{
				  var auxvel= vehiculo.vel.magnitud + vehiculo.tasaAc*dt
					vehiculo.vel.magnitud = if(auxvel > vehiculo.velCru) vehiculo.velCru else auxvel 			  
			    var tup = formaAumentoPosicion(vehiculo.vel, dt)
				  posInicial.xI += tup._1
				  posInicial.yI += tup._2
//			  if(d> s.XSemaforoF || sema.Estado == "Verde" || (sema.Estado == "Amarillo" && d<s.XSemaAmaC)){
//				
//			  }
			}
		}
  }
  
  def distEntreIntersec(actual: Interseccion, fin: Interseccion)= {
    math.pow((math.pow((fin.xI - actual.xI),2) + math.pow((fin.yI - actual.yI),2)),0.5)
  }
  
}