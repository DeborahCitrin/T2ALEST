public class Pedido 
{   // tempo medio que os pedidos passaram no processo inteiro - FEITO
    // pedido que levou mais tempo para ser atendido - FEITO

    private static int codigo = 1;
    private int cod;
    private int rodadas;
    private int qtd;
    //criar boolean cancelado

    public Pedido(int qtd){
        this.rodadas = this.qtd = qtd; //recebe o tanto de rodadas que vai ficar com o separador 
        cod = codigo;
        codigo += 1;
    }

    public void setRodadas(){
        this.rodadas += 1;
    }

    //se o pedido for cancelado: receber info do separador 
    public void setRodadas(int i){
        this.rodadas = rodadas - i;  
    }

    public int getRodadas(){
        return rodadas;
    }

    public int getCodigo(){
        return cod;
    }

    public int getQtd(){
        return qtd;
    }

    private static Lista lstTotal = new Lista();
    
    public static void addRodada(){ // precisa chamar ainda
        lstTotal.addRodadas();
    }

    public static void addPedido(Pedido p){
        lstTotal.adicionar(p);
    }
    
    public static double MediaRodadas(){
        double totalRodadas = 0;
        double deBaixo = lstTotal.getTamanho();

        for(int i=0; i<lstTotal.getTamanho(); i++)
        {
            totalRodadas += lstTotal.getRodadasPosicao(i);
            System.out.println("Rodadas do pedido de codigo[" +lstTotal.getCodigo(lstTotal.getNodoPos(i)) + "]: " +lstTotal.getRodadasPosicao(i));
        }

        return totalRodadas/deBaixo;
    }

    public static int getMaiorTempo()
    {
        return lstTotal.getMaiorTempo();
    }

    
}