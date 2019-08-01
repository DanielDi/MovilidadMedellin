package punto

class Via(var interO: Interseccion,var interF: Interseccion,var velocidad: Int, 
    var tipoVia: TipoVia, var sentido: Sentido,var num: String, var nom: String)
extends Recta{
  
  val origen= interO
  val fin= interF
  val distancia = math.pow((math.pow((fin.xI - origen.xI),2) + math.pow((fin.yI - origen.yI),2)),0.5)
  val Y = (fin.yI - origen.yI)
  val X = (fin.xI - origen.xI)
  val pendiente = Y/X
  val anguloVia = {
      if(X == 0) math.Pi/2
      else math.atan(pendiente)
  }
}  