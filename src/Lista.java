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

        public void setReferencia(nodo referencia)
        {
            this.referencia = referencia;
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

    public nodo head(){
        return inicio;
    }

    //adiciona ao fim da fila
    public void enqueue(Pedido p)
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

    //remove do inicio da fila, não retorna o valor, como deveria o método dequeue
    public void dequeue()
    {
        if(!isEmpty()){
            nodo nodoEliminado = inicio;
            inicio = inicio.getReferencia();
            nodoEliminado.setReferencia(null);
            nodoEliminado = null;

            if(tamanho == 1) fim = inicio;
            tamanho--;
        }
    }

    //remove pedido de uma determinada posicao da lista
    public boolean removeAt(int index){
        if(isEmpty() || (index < 0) || (index >= tamanho))
            return false;
        else{
            if(index == 0){
                dequeue();
            }
            else if(index < tamanho){ 
                nodo eliminar = inicio;
                for(int i=0; i<index-1; i++)
                    eliminar = eliminar.getReferencia();

                nodo anterior = eliminar;                
                eliminar = eliminar.getReferencia(); 
                anterior.setReferencia(eliminar.getReferencia()); 
                eliminar.setReferencia(null);

                if(index == tamanho-1){
                    fim = anterior;
                }
                tamanho--;
            }
            return true;
        }
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
            int i = tamanho; 
            
            if(tamanho==1) 
            {
                inicio.addRodada(); //inicio nulo pq?
            }
            else{
                nodo aux = inicio;
                while(i > 0) {
                    aux.addRodada(); 
                    aux = aux.getReferencia(); 
                    i--;
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






    public void devolveLista(){
        nodo noAtual = this.inicio;
        int i = 0;
            while(i < tamanho){
                System.out.println(noAtual);
                noAtual = noAtual.getReferencia();
                i++;
            } 
    }

    public nodo getFim(){
        return fim;
    }
}