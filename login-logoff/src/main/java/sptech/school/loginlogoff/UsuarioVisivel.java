package sptech.school.loginlogoff;

public class UsuarioVisivel{
    private String usuario;
    private String nome;
    private boolean autenticado;

    public UsuarioVisivel(String usuario, String nome, boolean autenticado) {
        this.usuario = usuario;
        this.nome = nome;
        this.autenticado = autenticado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAutenticado() {
        return autenticado;
    }

    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }
}
