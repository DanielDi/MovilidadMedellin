package movil

import punto.Velocidad
import main.Simulacion
import punto.Interseccion
import punto.Angulo
import main.GrafoVia
import scala.collection.mutable.Queue


abstract case class Vehiculo(private var _vel: Velocidad) extends Movil with MovimientoUniforme {
  
  val placa: String
  var radioLimite = vel.magnitud*Simulacion.dt
  
  def aumentarPosc(dt: Int, posInicial: Interseccion, path: Queue[Interseccion]): (Interseccion, Queue[Interseccion]) = {
    var auxPosIni = posInicial
    var path2 = path
    
    println(auxPosIni)
    println(path2)
    
    if(!path2.isEmpty){
    	var d= distEntreIntersec(auxPosIni, path2.front)
			if(d>radioLimite){
				var tup = formaAumentoPosicion(this.vel, dt)
						auxPosIni.xI += tup._1
						auxPosIni.yI += tup._2
			}else{
				auxPosIni = path2.front.copy()
				path2.dequeue()
				if(!path2.isEmpty) this.vel.direccion.grado = this.direccionAngulo(auxPosIni, path2) 
				println(this.vel.direccion.grado)
			}
    }
    
    (auxPosIni.copy(), path2)
  }
  
  
  //Accesor
  def vel = _vel
  
  //Mutator
  def vel_= (vel: Velocidad): Unit = _vel = vel
  
  def aumentarPosc(posc: Any) ={}

  def distEntreIntersec(actual: Interseccion, fin: Interseccion)= {
    math.pow((math.pow((fin.xI - actual.xI),2) + math.pow((fin.yI - actual.yI),2)),0.5)
  }
}