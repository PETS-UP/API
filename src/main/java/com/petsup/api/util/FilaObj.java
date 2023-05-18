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

    // 07) Método poll - remove e retorna o primeiro elemento da fila
    public T poll() {
        T aux = fila[0];
        for (int i = 0; i < tamanho -1; i++) {
            fila[i] = fila[i+1];
        }
        fila[tamanho - 1] = null;
        tamanho--;
        return aux;
    }

    // 08) Método exibe() - exibe o conteúdo da fila
    public void exibe() {
        for (int i = 0; i <= tamanho; i++) {
            System.out.println("Fila["+ i + "] "+ fila[i]);
        }
    }

    // Getters and Setters necessários
    public int getTamanho(){
        return this.tamanho;
    }
}
