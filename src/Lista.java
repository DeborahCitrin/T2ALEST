//Adicionar pedido no fim da fila, remover pedido do inicio da fila, remover pedido por index (cancelado)
public class Lista
{    
    private class nodo
    {
        private nodo referencia;
        private Pedido p;
        
        public nodo(Pedido p)
        {
            this.referencia = null;
            this.p = p;
        }

        public int getQtd()
        {
            return p.getQtd();
        }

        public nodo getReferencia()
        {
            return referencia;
        }

        public void setReferencia(nodo ref)
        {
            this.referencia = ref;
        }

        public void addRodada()
        {
            p.setRodadas();
        }

        public int getRodadas()
        {
            return p.getRodadas();
        }

        public Pedido getPedido(){
            return p;
        }

        public int getCodigo(){
            return p.getCodigo();
        }
    }

    private nodo inicio, fim;
    private int tamanho;
    
    public Lista(){
        clear();
    }

    public void clear(){
        inicio = fim = null;
        tamanho = 0;
    }

    public int getTamanho(){
        return tamanho;
    }

    public Pedido retornaPedido(){
        if(inicio==null)
            return null;
        return inicio.getPedido();
    }

    public nodo retornaInicio(){
        return inicio;
    }

    //adiciona ao fim da fila
    public void adicionar(Pedido p)
    {
        if(isEmpty())
        {
            fim = inicio = new nodo(p);
        }
        else
        {
            nodo aux = new nodo(p);
            fim.setReferencia(aux);
            fim = aux;
        }        
        tamanho++;
    }

    //remove do inicio da fila
    public void remover()
    {
        if(!isEmpty()){
            nodo nodoEliminado = inicio;
            inicio = inicio.getReferencia();
            nodoEliminado.setReferencia(null);
            nodoEliminado = null;

            if(tamanho == 1) {fim = inicio;}

            tamanho--;
        }
    }

    //remove pedido de uma determinada posicao da lista
    public void pedidoCancelado(int index){
        if(index >= 0 && index < tamanho){
            if(index==0){
                //acao de eliminacao do primeiro
                remover();
            }
            else{
            //     nodo nAnterior, nPosterior;
            //     nAnterior = inicio; //pos 0
            //    while(index-1 > 0){
            //      nAnterior = nAnterior.getReferencia(); 
            //      index--;
            //    }
            //    nPosterior = nAnterior.getReferencia();
            //    nAnterior.setReferencia(nPosterior.getReferencia());

            //    nPosterior.setReferencia(null);
            //    nPosterior = null;
            //    tamanho--;

                nodo eliminar = inicio;
                for(int i=0; i<index-1; i++)
                {
                    eliminar = eliminar.getReferencia();
                }

                nodo anterior=eliminar;
                eliminar=eliminar.getReferencia();

                if (index == tamanho-1)
                {
                    anterior.setReferencia(null);
                }
                else
                {
                    anterior.setReferencia(eliminar.getReferencia());
                }

                eliminar.setReferencia(null);
                tamanho--;
            }
            
            
            
            // nodo nodoASerEliminado = inicio;

            // if(index < tamanho){
            //     for(int i=0; i<index-1; i++)
            //         nodoASerEliminado=nodoASerEliminado.getReferencia();

            //     nodo anterior=nodoASerEliminado;                
            //     nodoASerEliminado=nodoASerEliminado.getReferencia();

            //     anterior.setReferencia(nodoASerEliminado.getReferencia());
            //     nodoASerEliminado.setReferencia(null);
            // }
        }
        // else{
        //     remover();
        // }
        // tamanho--;
    }

    public boolean isEmpty(){
        return (tamanho == 0);
    }

    public int getQtdInicio(){
        if(inicio!=null)
            return inicio.getQtd();     
        return -1;
    }

    //adiciona uma rodada a todos componentes da lista
    public void addRodadas()
    {
        if(!isEmpty()){
            int i = tamanho; //10
            
            if(tamanho==1) 
            {
                inicio.addRodada(); //inicio nulo pq?
            }
            else{
                nodo aux = inicio;
                while(i > 0){
                    aux.addRodada(); System.out.println("addRodada");
                    aux = aux.getReferencia(); System.out.println("getReferencia");
                    System.out.println("");
                    //System.out.println(aux.getCodigo());
                    System.out.println("i: " +i);
                    i--;
                    System.out.println("i2: " +i);
                    System.out.println("");
                }
            }
        }
    }

    //retorna numero de rodadas de uma posição específica da lista (o que levou mais tempo para ser atendido)
    public int getRodadasPosicao(int index){
        nodo aux = inicio;
        while(index>0){
            aux = aux.getReferencia();
            index--;
        }
        if(aux!=null)
            return aux.getRodadas();
        return -1;
    }

    public int getCodigo(nodo n){
        if(n!=null)
            return n.getCodigo();
        return -1;
    }

    public nodo getNodoPos(int i){
        if((i>=0)&&(i<tamanho)){
            nodo aux = inicio;
            while(i>0){
                aux = aux.getReferencia();
                i--;
            }
            return aux;
        }
        return null;
    }


    //encontra quantas rodadas levou o pedido que ficou mais tempo em filas
    public int getMaiorTempo(){
        nodo aux = inicio;
        int maior = -1;
        int i = tamanho;
        while(i>0){
            if(aux.getRodadas() > maior){
                maior = aux.getRodadas();
            }
            aux = aux.getReferencia();
            i--;
        }
        return maior;
    }
}