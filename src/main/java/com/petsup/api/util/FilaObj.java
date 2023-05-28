package com.petsup.api.util;


public class FilaObj<T>{
    // 01) Atributos
    private int tamanho;
    private T[] fila;

    // 02) Construtor
    public FilaObj(int capacidade) {
        this.tamanho = 0;
        this.fila = (T[]) new Object[capacidade];
    }



    // 03) Método isEmpty() - retorna true se a fila está vazia e false caso contrário
    public boolean isEmpty() {
        if(tamanho>0){
            return false;
        }return true;
    }

    // 04) Método isFull() - retorna true se a fila está cheia e false caso contrário
    public boolean isFull() {
        if(tamanho < fila.length){
            return false;
        }return true;
    }

    // 05) Método insert - recebe um elemento e insere esse elemento no inicio da fila

    public void insert(T info) {
        if(isFull()){
            throw new IllegalStateException();
        }else{
            fila[tamanho++] = info;
        }
    }

    // 06) Método peek - retorna o primeiro elemento da fila, sem removê-lo
    public T peek() {
        return fila[0];
    }
    /* Método poll - remove e retorna o primeiro elemento da fila, se a fila não estiver
       vazia. Quando um elemento é removido, a fila "anda", e tamanho é decrementado
     */
    public T poll() {
        T primeiro = fila[0];

        if (!isEmpty()) { // se a fila não está vazia
            // tem que fazer a fila andar
            for (int i = 0; i < tamanho-1; i++) {
                fila[i] = fila[i+1];
            }
            fila[tamanho - 1] = null; // Limpa o último cara da fila (que já andou)
            tamanho--;
            // as 2 instruções acima poderiam ser substituidas pela instrução abaixo
            //fila[--tamanho] = null;
        }

        return primeiro;
    }

    /* Método exibe() - exibe o conteúdo da fila */
    public void exibe() {
        if (isEmpty()) {
            System.out.println("Fila vazia!");
        }
        else {
            System.out.println("Conteúdo da fila:");
            for (int i = 0; i < tamanho;i++) {
                System.out.println(fila[i]);
            }
        }

    }

    /* Usado nos testes  - complete para que fique certo */
    public int getTamanho(){
        return tamanho;
    }
}
