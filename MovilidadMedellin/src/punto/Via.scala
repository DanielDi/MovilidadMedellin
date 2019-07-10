package punto

class Via(var interO: Interseccion,var interF: Interseccion,var velocidad: Int, 
    var tipoVia: TipoVia, var sentido: Boolean,var num: String, var nom: String)
extends Recta{
  
  var origen= interO
  var fin= interF
}