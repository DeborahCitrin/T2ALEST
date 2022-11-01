import java.util.Random;

public class Entregador
{
    private static Entregador[] entregadores = new Entregador[3];
    private static Random rand = new Random();
    private static Lista lstEntregador = new Lista();
    private static int cancelados = 0;
    private static int quaseCancelados = 0;
    private static int pedidosEntregues = 0;

    private boolean primeiraRodada;
    private boolean livre;
    private int rodadas;
    private int nEntregas;
    private boolean quaseCancelado;
    private Pedido pedido;

    public static void criaEntregadores()
    {
        for(int i=0; i<3; i++)
        {
            entregadores[i] = new Entregador();
        }
    }
    

    public Entregador()
    {
        livre = true;
        rodadas = 0;
        nEntregas = 0;
        quaseCancelado = false;
    }

    //Retorna true se o entregador está livre e false se está ocupado
    public boolean getLiberdade()
    {
        return livre;
    }

    public int getEntregas()
    {
        return nEntregas;
    }

    public void entrega()
    {
        if(lstEntregador.getTamanho()!=0)
        {
            if(livre == true) // se for a primeira iteracao
            {
                rodadas = rand.nextInt(5) + 4;
                this.pedido = lstEntregador.retornaPedido();
                lstEntregador.dequeue();
                livre = false;
                System.out.println("número de rodadas para entregar o pedido: " + rodadas);
                primeiraRodada = true;
            }
        }
        
        if((primeiraRodada == false) && (livre==false))
        {
            
            if(quaseCancelado==false){
                if (rand.nextInt(100)> 85)
                {
                    quaseCancelados++;
                    quaseCancelado = true;
                }
                
            }
            
            if (rodadas>0)
            {
                pedido.setRodadas();
                rodadas--;
                if (rodadas==0) {
                    Pedido.addPedido(pedido); //copia o pedido concluido para a fila de Pedido
                    livre = true;
                    pedidosEntregues++; //conta o total de pedidos entregues
                    nEntregas++; //conta quantas entregas o entregador fez
                    
                }
            }
        }
        primeiraRodada = false;
    }

    //METODOS DE CLASSE:

    public static int getCancelados()
    {
        return cancelados;
    }

    public static int getQuaseCancelados(){
        return quaseCancelados;
    }

    public static int getPedidosEntregues(){
        return pedidosEntregues;
    }

    //adiciona mais uma rodada a cada nodo da lista
    public static void addRodada(){
        lstEntregador.addRodadas();
    }

    //chamado da main em todas iterações
    public static void cancelaPedidoFila()
    {
        if (lstEntregador.getTamanho()>0)
        {
            // System.out.print("lista quando entregador entra em cancela pedido: ");
            // lstEntregador.devolveLista();
            //System.out.println("\nfim da lista pre cancela pedido: " +lstSeparador.getFim());
            int ran = rand.nextInt(100);
            if(ran%5 == 0){
                int pos = rand.nextInt(lstEntregador.getTamanho());
                System.out.println("Pedido cancelado: " +lstEntregador.getPedidoPos(pos));
                // Pedido.addPedido(lstEntregador.getPedidoPos(pos)); //copia o pedido cancelado para a fila de Pedido
                lstEntregador.removeAt(pos);
                // System.out.print("lista depois do pedido ser removido entregador: ");
                // lstEntregador.devolveLista();
                //System.out.println("fim da lista pos cancela pedido: " +lstSeparador.getFim());
                cancelados++;
                //System.out.println("Cancela fila sep. Aleatorio: " + pos);
            }
        }
    }

    //pedido é adicionado a fila para ser entregado
    //valor não importa, o que importa é a ordem que o pedido entra na fila
    public static void entraPedido(Pedido p){
        lstEntregador.enqueue(p);
    }    

    public static void fazRodada()
    {
        System.out.println("Liberdade antes: 0: " +entregadores[0].getLiberdade()+ " // 1: " +entregadores[1].getLiberdade()+ 
        " // 2: " +entregadores[2].getLiberdade());
        entregadores[0].entrega(); 
        entregadores[1].entrega();
        entregadores[2].entrega();
        System.out.println("Liberdade depois: 0: " +entregadores[0].getLiberdade()+ " // 1: " +entregadores[1].getLiberdade()+ 
        " // 2: " +entregadores[2].getLiberdade());
    }

    //Qual dos entregadores entregou mais + número de pedidos entregues
    public static void MelhorEntregador()
    {   
        int melhor = 0;
        int i = 0;
        int iguais = 0;
        String entIguais = "";
        

        while(i<2){
            if(entregadores[i+1].getEntregas() > entregadores[i].getEntregas()){
                melhor = i+1;
            }
            i++;
        }

        for (i=0; i<3; i++)
        {
            if (entregadores[melhor].getEntregas() == entregadores[i].getEntregas())
            {
                iguais++;

                if (i>0)
                {
                    if ((i==1) && (entregadores[i].getEntregas() == entregadores[i+1].getEntregas()))
                    {
                        entIguais += ", 1 e 2";
                        break;
                    }

                    entIguais += " e ";
                }

                entIguais += i;
                
                
            }
        }

        if (iguais > 1)
        {
            System.out.println("Os entregadores " +entIguais+ " tiveram " +entregadores[melhor].getEntregas()+ " entregas.");
        }
        else
        {
            System.out.println("O melhor entregador foi o de número " + melhor + 
        ", com " +entregadores[melhor].getEntregas() + " pedidos entregues.");
        }

        
    }

    public static void imprime()
    {
        System.out.println("E0: " + entregadores[0].getEntregas());
        System.out.println("E1: " + entregadores[1].getEntregas());
        System.out.println("E2: " + entregadores[2].getEntregas());
    }

    // public static void addTudo()
    // {
    //     for (int i=0; i<lstEntregador.getTamanho(); i++)
    //     {
    //         Pedido.addPedido(lstEntregador.getPedidoPos(i));
    //     }
    // }

    public static void zeraTudo()
    {
        entregadores[0] = new Entregador();
        entregadores[1] = new Entregador();
        entregadores[2] = new Entregador();
        lstEntregador.clear();
        cancelados = 0;
        pedidosEntregues = 0;
        quaseCancelados = 0;
    }
}