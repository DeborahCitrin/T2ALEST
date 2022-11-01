import java.util.Random;

public class Separador
{
    private static Separador[] separadores = new Separador[3];
    private static Lista lstSeparador = new Lista();
    private static int cancelados = 0;
    private static Random rand = new Random();
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
        if(lstSeparador.getTamanho()!=0)
        {
            if(livre == true) // se for a primeira iteracao
            {
                rodadas = lstSeparador.getQtdInicio(); 
                this.p = lstSeparador.retornaPedido();
                System.out.println("Coleta iniciada: " +p.getCodigo());
                lstSeparador.dequeue();
                livre = false;
                primeiraRodada = true;
            }
        }
        
        if((primeiraRodada == false) && (livre == false))
        {
            if(rodadas>0)
            {
                rodadas--; 
                //pouca chance de cancelamento
                if(rodadas%2 == 0){
                   int ran = rand.nextInt(100);
                //    System.out.println("Aleatório separador (cancela): " + ran);
                    if (ran < 15)
                    {
                        System.out.println("Cancelado durante coleta: " +this.p.getCodigo());
                        p.setRodadas(rodadas);
                        cancelaDuranteColeta();
                        // System.out.println("cancelou.");
                    }
                }
                   
                if (rodadas==0) {
                    livre = true;
                    nPedidos++; //conta quantos pedidos o separador separou
                    Entregador.entraPedido(this.p);
                    System.out.println("Pedido coletado: " +this.p.getCodigo());
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

    //adiciona mais uma rodada a cada nodo da lista 
    public static void addRodada(){
        if(lstSeparador.getTamanho()>0)
            lstSeparador.addRodadas();
    }

    //cancela enquanto o pedido está na fila do separador - pedido sai da fila
    public static void cancelaPedidoFila()
    {
        if (lstSeparador.getTamanho()>0)
        {
            //System.out.print("lista quando entra em cancela pedido: ");
            //lstSeparador.devolveLista();
            //System.out.println("\nfim da lista pre cancela pedido: " +lstSeparador.getFim());
            int ran = rand.nextInt(100);
            if(ran%5 == 0){
                int pos = rand.nextInt(lstSeparador.getTamanho());
                System.out.println("Pedido cancelado: " +lstSeparador.getPedidoPos(pos));
                // Pedido.addPedido(lstSeparador.getPedidoPos(pos)); //copia o pedido cancelado para a fila de Pedido
                lstSeparador.removeAt(pos);
                cancelados++;
            }
        }
    }

    //pedido de determinado tamanho e adicionado a fila para ser separado
    //cada produto leva 1 rodada para ser coletado
    public static void entraPedido(int qtd){
        Pedido p1 = new Pedido(qtd);
        // System.out.println("fim da lista pre add novo pedido: " +lstSeparador.getFim());
        lstSeparador.enqueue(p1);
        System.out.println("Novo pedido: " + p1.getCodigo());
        // Pedido.addPedido(p1); //REVISAR
        // System.out.println("novo fim da lista(entra pedido): " +lstSeparador.getFim());
    }

    public static void fazRodada()
    {
        System.out.println("Tam. lista sep: " +lstSeparador.getTamanho());
        System.out.println("Liberdade antes: 0: " +separadores[0].getLiberdade()+ " // 1: " +separadores[1].getLiberdade()+ 
        " // 2: " +separadores[2].getLiberdade());

        separadores[0].separadorColeta(); 
        separadores[1].separadorColeta();
        separadores[2].separadorColeta();

        System.out.println("Liberdade depois: 0: " +separadores[0].getLiberdade()+ " // 1: " +separadores[1].getLiberdade()+ 
        " // 2: " +separadores[2].getLiberdade());

        // System.out.println("\ntamanho lista separador: " +lstSeparador.getTamanho());

        // System.out.println("tamanho lista separador: " +lstSeparador.getTamanho());
        // System.out.println("\ninicio depois: " +lstSeparador.head());        
    }

    public static void ProbCancelarFila(){
        //System.out.println("\ninicio antes cancela pedido: " +lstSeparador.head());
        cancelaPedidoFila();
        // System.out.println("\ninicio depois cancela pedido fila: " +lstSeparador.head());
        // System.out.println("tamanho lista separador pos cancela fila: " +lstSeparador.getTamanho());
        // System.out.println("");
    }

    public static void MelhorSeparador()
    {   
        int melhor = 0;
        int i = 0;
        int iguais = 0;
        String entIguais = "";
        
        while(i<2){
            if(separadores[i+1].getNumPedidos() > separadores[i].getNumPedidos()){
                melhor = i+1;
            }
            i++;
        }

        for (i=0; i<3; i++)
        {
            if (separadores[melhor].getNumPedidos() == separadores[i].getNumPedidos())
            {
                iguais++;

                if (i>0)
                {
                    if ((i==1) && (separadores[i].getNumPedidos() == separadores[i+1].getNumPedidos()))
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
            System.out.println("Os separadores " +entIguais+ " tiveram " +separadores[melhor].getNumPedidos()+ " coletas.");
        }
        else
        {
            System.out.println("O melhor separador foi o de número " + melhor + 
        ", com " +separadores[melhor].getNumPedidos() + " pedidos coletados.");
        }
    }

    public static void imprime()
    {
        System.out.println("S0: " + separadores[0].getNumPedidos());
        System.out.println("S1: " + separadores[1].getNumPedidos());
        System.out.println("S2: " + separadores[2].getNumPedidos());
    }

    public static void addTudo()
    {
        for (int i=0; i<lstSeparador.getTamanho(); i++)
        {
            Pedido.addPedido(lstSeparador.getPedidoPos(i));
        }
    }

    public static void zeraTudo()
    {
        separadores[0] = new Separador();
        separadores[1] = new Separador();
        separadores[2] = new Separador();
        lstSeparador.clear();
        cancelados = 0;
    }
}