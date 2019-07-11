package punto

class Sentido(var unavia: Boolean, var doblevia: Boolean){

}

object Sentido {
  
  private def dobleVia(){
    new Sentido(false,true)
  }
  
  private def unaVia(){
    new Sentido(true,false)
  }
}