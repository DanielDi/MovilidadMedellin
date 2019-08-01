package punto

class Sentido(private var _unavia: Boolean, private var _doblevia: Boolean){
  
  //Accesor 
  def unavia = _unavia
  
  def doblevia = _doblevia
  
  //Mutator
  def unavia_= (x: Int): Unit = _unavia = unavia
  
  def doblevia_= (x: Int): Unit = _doblevia = doblevia

}

object Sentido {
  
  def dobleVia() = new Sentido(false,true)

  
  def unaVia() = new Sentido(true,false)
  
}