package punto

class Via(var interO: Interseccion,var interF: Interseccion,var velocidad: Int, 
    var tipoVia: TipoVia, var sentido: Sentido,var num: String, var nom: String)
extends Recta{
  
  var origen= interO
  var fin= interF
  var distancia = math.pow((math.pow((fin.xI - origen.xI),2) + math.pow((fin.yI - origen.yI),2)),0.5)
  var Y = (fin.yI - origen.yI)
  var X = (fin.xI - origen.xI)
  var pendiente = Y/X
  var anguloVia = {
      if(X == 0) math.Pi/2
      else math.atan(pendiente)
  }
}  