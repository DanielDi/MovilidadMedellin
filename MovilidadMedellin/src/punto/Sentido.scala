package punto

class Sentido(private var _unavia: Boolean, private var _doblevia: Boolean){
  
  def unavia = _unavia
  
  def doblevia = _doblevia

}

object Sentido {
  
  def dobleVia() = new Sentido(false,true)

  
  def unaVia() = new Sentido(true,false)
  
}