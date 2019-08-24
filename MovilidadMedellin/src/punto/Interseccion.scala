package punto

import main.Simulacion

case class Interseccion(var xI: Double, var yI: Double, var nombre: Option[String]) 
extends Punto(xI,yI) {
  
  Simulacion.arrayDeIntersecciones.append(this)
  Simulacion.arrayDeIntersecciones.distinct
  
  def this(x:Double,y:Double){
    this(x,y,None)
  }
  
  override
  def toString()= s"${nombre.getOrElse("Sin nombre")} $xI $yI"
}