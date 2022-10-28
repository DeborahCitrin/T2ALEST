import java.util.Random;
import java.util.Scanner;

//ADICIONAR RODADAS DA CLASSE PEDIDO - contar addRodadas do Separador + do Entregador?

public class App
{
    public static void main(String[] args)
    {
        Random rand = new Random();

        Separador.criaSeparadores();
        Entregador.criaEntregadores();

        int opcao = menu();
        
        while (opcao!=0)
        {
            System.out.println("Quantas rodadas você deseja testar?");
            Scanner usr = new Scanner(System.in);
            int x = usr.nextInt();

            if(x > 0){
                for (int i=0; i<x; i++) // x rodadas, 1 pedido adicionado por rodada. Ao final, ainda há pedidos não entregues.
                {
                    int qtd = rand.nextInt(9) + 1;
                    System.out.println("rodada: " +(i+1));
                    System.out.println("aleatorio: " +qtd);

                    Separador.entraPedido(qtd); //o pedido entra na fila de separadores
                    Separador.fazRodada(); // se houver um livre, ele faz a coleta do pedido e manda ao entregador. Senão, o pedido continua na fila
                    Separador.cancelaPedidoFila();
                    Entregador.fazRodada(); //se houver um livre, ele sai para entregar um pedido. Senão, o pedido continua na fila para o entregador pegar
                    Entregador.cancelaPedidoFila();
                    Separador.addRodada(); //adiciona rodada a fila do separador
                    Entregador.addRodada(); //adiciona rodada a fila do entregador
                    
                    System.out.println("");

                    if (i==x-1)
                    {
                        Separador.addTudo();
                        Entregador.addTudo();
                    }
                }
            }

            Separador.imprime();
            Entregador.imprime();
        
            Separador.MelhorSeparador(); //Separador que mais fez coletas
            Entregador.MelhorEntregador(); //Entregador que mais fez entregas


            System.out.println("Tempo médio dos pedidos na fila: " + Pedido.MediaRodadas() + " rodadas.");
            System.out.println("O maior tempo de atendimento foi de " + Pedido.getMaiorTempo() + " rodadas.");
            
            Separador.zeraTudo();
            Entregador.zeraTudo();
            Pedido.zeraTudo();
            opcao = menu();
        }
    }

    public static int menu()
    {
        Scanner inputUsr = new Scanner(System.in);
        int opcao;
        do{
           System.out.println("Menu Principal");
           System.out.println("0. Sair");
           System.out.println("1. Inserir número de rodadas");
           opcao = inputUsr.nextInt(); 
        }while(opcao<0||opcao>1);
        return opcao;
    }
    
}