package com.petsup.api.entities.usuario;

public interface ClienteObserver {
    public void atualiza(String emailRemetente, String emailDestinatario, String host);
}
