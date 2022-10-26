import java.util.Random;
//como armazenar as rodadas, sendo que a gente tá tirando os nodos da lista, então o número de rodadas que tava armazenado deixa de existir - armazenei os pedidos dentro dos nodos!!


public class Separador
{
    private static Separador[] separadores = new Separador[3];
    private static Lista lstSeparador = new Lista();
    private static int cancelados = 0;
    private static Random rand = new Random();
    private static int totalPedidos = 0;
    //private static int codigo = 0;

    private boolean primeiraRodada;
    private boolean livre;
    private int rodadas; // produtos a serem coletados no pedido
    private int rodadasPedido = 0;
    private int nPedidos;
    private Pedido p = null;

    public static void criaSeparadores()
    {
        for(int i=0; i<3; i++)
        {
            separadores[i] = new Separador();
        }
    }

    public Separador()
    {
        livre = true;
        rodadas = 0;
        nPedidos = 0;
    }

    //retorna se o separador esta disponivel para atender o pedido ou nao
    public boolean getLiberdade(){
        return livre;
    }

    public int getNumPedidos()
    {
        return nPedidos;
    }

    public Pedido getPedidoInicio(){
        return lstSeparador.retornaPedido();
    }

    //cancelamento do pedido enquanto o separador coleta
    //rodadas zeradas
    //não há acréscimo aos pedidos coletados
    //acréscimo ao número de pedidos cancelados
    public void cancelaDuranteColeta() //chamado dentro de separador coleta
    {
        rodadas = 1;
        cancelados++;
        livre = true;
    }

    //tirar pedido da lista 
    //separador ocupado
    //se terminar de coletar -> separador livre
    //chamado todas as vezes
    public void separadorColeta(){ //chamado dentro do separador livre
        if(lstSeparador.getTamanho()>0)
        {
            if(livre == true) // se for a primeira iteracao
            {
                rodadas = lstSeparador.getQtdInicio(); 
                this.p = getPedidoInicio();
                lstSeparador.remover();
                livre = false;
                primeiraRodada = true;
            }
        }
        
        if((primeiraRodada == false) && (livre == false))
        {
            if(rodadas>0)
            {
                rodadas--; 
                // pouca chance de cancelamento
                //if(rodadas%2 == 0){
                //    int ran = rand.nextInt(100);
                //    System.out.println("Aleatório separador (cancela): " + ran);
                    // if (ran < 15)
                    // {
                    //     System.out.println(this.p.getCodigo() + " foi cancelado.");
                    //     p.setRodadas(rodadas);
                    //     cancelaDuranteColeta();
                    //     System.out.println("cancelou.");
                    // }
                //}
                
                
                if (rodadas==0) {
                    livre = true;
                    nPedidos++; //conta quantos pedidos o separador separou
                    //Pedido p = new Pedido(cancelados)
                    Entregador.entraPedido(this.p);
                    System.out.println("o pedido " +this.p.getCodigo()+ " foi coletado.");
                    //manda rodadinhas pra pedido
                }
            }
        }
        primeiraRodada = false;
    }

   //METODOS DA CLASSE:
    public static int getCancelados()
    {
        return cancelados;
    }

    public static int getTotalPedidos(){
        return totalPedidos;
    }

    //adiciona mais uma rodada a cada nodo da lista 
    public static void addRodada(){
        if(lstSeparador.getTamanho()>0)
            lstSeparador.addRodadas();
    }

    //cancela enquanto o pedido está na fila do separador - pedido sai da fila
    public static void cancelaPedidoFila(int pos)
    {
        if (lstSeparador.getTamanho()>0 && pos < lstSeparador.getTamanho())
        {
            int ran = rand.nextInt(100);
            if (ran<30)
            {
                lstSeparador.pedidoCancelado(pos);
                cancelados++;
                System.out.println("Cancela fila sep. Aleatorio: " + ran);
            }
        }
    }

    //pedido de determinado tamanho e adicionado a fila para ser separado
    //cada produto leva 1 rodada para ser coletado
    public static void entraPedido(int qtd){
        Pedido p1 = new Pedido(qtd);
        lstSeparador.adicionar(p1);
        Pedido.addPedido(p1); //REVISAR
        totalPedidos++;
    }

    public static void fazRodada()
    {
        // System.out.println("Cancelados: " + cancelados);
        System.out.println("inicio antes: " +lstSeparador.retornaInicio());
        separadores[0].separadorColeta();  //tirar parametro
        System.out.println("Lib 0: " + separadores[0].getLiberdade());
        System.out.println("inicio sep0: " +lstSeparador.retornaInicio());

        separadores[1].separadorColeta();
        System.out.println("Lib 1: " + separadores[1].getLiberdade());
        System.out.println("inicio sep1: " +lstSeparador.retornaInicio());

        separadores[2].separadorColeta();
        System.out.println("Lib 2: " + separadores[2].getLiberdade());
        System.out.println("inicio sep2: " +lstSeparador.retornaInicio());

        System.out.println("tamanho lista separador: " +lstSeparador.getTamanho());
        System.out.println(1);
        if(lstSeparador.getTamanho()>0){
            System.out.println("inicio if lista>0: " +lstSeparador.retornaInicio());

            int ran = rand.nextInt(lstSeparador.getTamanho());
            System.out.println("Aleatorio cancela fila (posicao): " + ran);
            cancelaPedidoFila(ran);

            System.out.println("inicio pos cancelaPedido: " +lstSeparador.retornaInicio());

        }
        System.out.println(2);
        System.out.println("tamanho lista separador: " +lstSeparador.getTamanho());
        System.out.println(3);
        System.out.println("inicio depois: " +lstSeparador.retornaInicio());
        System.out.println(4);
        
    }

    public static void MelhorSeparador()
    {   
        int melhor = 0;
        int i = 0;
        
        while(i<2){
            if(separadores[i+1].getNumPedidos() > separadores[i].getNumPedidos()){
                melhor = i+1;
            }
            i++;
        }

        System.out.println("O melhor separador foi o de número " + melhor + 
        ", com " +separadores[melhor].getNumPedidos() + " pedidos separados.");
    }

    public static void imprime()
    {
        System.out.println("S0: " + separadores[0].getNumPedidos());
        System.out.println("S1: " + separadores[1].getNumPedidos());
        System.out.println("S2: " + separadores[2].getNumPedidos());
    }

    public static void zeraTudo()
    {
        separadores[0] = new Separador();
        separadores[1] = new Separador();
        separadores[2] = new Separador();
        lstSeparador.clear();
        cancelados = 0;
        totalPedidos = 0;
    }
}

// public static void separadorLivre()
    // {
    //     int i=0;
    //     while (separadores[i].getLiberdade() == false)
    //     {
    //         if(separadores[i].getLiberdade() == true)
    //         {
    //             separadores[i].separadorColeta();
    //             break;
    //         }

    //         if (i==2) break;
    //         i++;
    //     }
    // }