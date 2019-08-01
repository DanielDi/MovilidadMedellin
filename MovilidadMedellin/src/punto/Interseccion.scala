package punto

import main.Simulacion

case class Interseccion(var xI: Double, var yI: Double, var nombre: String) 
extends Punto(xI,yI) {
  
  Simulacion.arrayDeIntersecciones += this
  
  def this(x:Double,y:Double){
    this(x,y,"")
  }
  
  override
  def toString()= this.nombre+this.xI+this.yI
}