package punto

class Interseccion(var xI: Double, var yI: Double, var nombre: String) 
extends Punto(xI,yI) {
  
  def this(x:Double,y:Double){
    this(x,y,"")
  }
}