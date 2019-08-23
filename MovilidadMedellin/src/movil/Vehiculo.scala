package movil

import punto.Velocidad
import main.Simulacion
import punto.Interseccion
import punto.Angulo
import main.GrafoVia
import scala.collection.mutable.Queue


abstract case class Vehiculo(var posInicial: Interseccion, var posFinal: Interseccion, private var _vel: Velocidad) extends Movil with MovimientoUniforme {
  
  val placa: String

  var path = Queue( GrafoVia.menorCamino(posInicial, posFinal).map(_.toOuter).toSeq : _*) //Convierte nodos a intersecciones
  var origen = posInicial.copy()
  this.path.dequeue()
  var radioLimite = vel.magnitud * Simulacion.dt

  def aumentarPosc(dt: Int) = {
    if(!this.path.isEmpty){
    	var d= distEntreIntersec(posInicial, path.front)
			if(d>radioLimite){
				var tup = formaAumentoPosicion(this.vel, dt)
						this.posInicial.xI += tup._1
						this.posInicial.yI += tup._2
			}else{
				this.posInicial = this.path.front.copy()
				this.path.dequeue()
				if(!this.path.isEmpty) this.vel.direccion.grado = this.direccionAngulo(this.posInicial ,this.path) 
			}
    }
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