package sptech.school.loginlogoff.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sptech.school.loginlogoff.dto.UsuarioDTO;
import sptech.school.loginlogoff.objetos.Login;
import sptech.school.loginlogoff.objetos.Usuario;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private List<Usuario> users;

    public UsuarioController() {
        this.users = new ArrayList<>();
    }

    @PostMapping
    public Usuario cadastrar(@RequestBody Usuario newUser) {
        users.add(newUser);
        return newUser;
    }

    @PostMapping("/autenticacao")
    public UsuarioDTO autenticar(@RequestBody Login login) {
        for (int i = 0; i < users.size(); i++) {
            if (login.getUsuario().equals(users.get(i).getUsuario())) {
                if (login.getSenha().equals(users.get(i).getSenha())) {
                    users.get(i).setAutenticado(true);
                    UsuarioDTO userVisivel = new UsuarioDTO(users.get(i).getUsuario(),
                            users.get(i).getNome(),
                            users.get(i).isAutenticado());
                    return userVisivel;
                }
            }
        }
        return null;
    }

    @PostMapping("/autenticacao2")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String autenticar2(@RequestBody Login login) {

        for (int i = 0; i < users.size(); i++) {
            if (login.getUsuario().equals(users.get(i).getUsuario())) {
                if (login.getSenha().equals(users.get(i).getSenha())) {
                    users.get(i).setAutenticado(true);
                    return String.format("Usuário %s logado com sucesso!", users.get(i).getNome());
                }
            }
        }
        return null;
    }


    @GetMapping
    public List<UsuarioDTO> listar() {
        List<UsuarioDTO> usuariosVisiveis = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            UsuarioDTO userClone = new UsuarioDTO(users.get(i).getUsuario(),
                    users.get(i).getNome(),
                    users.get(i).isAutenticado());
            usuariosVisiveis.add(userClone);
        }
        return usuariosVisiveis;
    }

    @DeleteMapping("/autenticacao/{usuario}")
    public String logoff(@PathVariable String usuario) {
        for (int i = 0; i < users.size(); i++) {
            if (usuario.equals(users.get(i).getUsuario())) {
                if (users.get(i).isAutenticado()) {
                    users.get(i).setAutenticado(false);
                    return String.format("Logoff do usuário %s concluído", users.get(i).getNome());
                } else {
                    return String.format("Usuário %s NÃO está autenticado", users.get(i).getNome());
                }
            }
        }
        return String.format("Usuário %s não encontrado", usuario);
    }

    //Percorre a lista de usuarios em busca de um nome.
    // Retorna todos os resultados que contém o texto digitado.
    @GetMapping("/{nome}")
    public List<UsuarioDTO> buscarNome(@PathVariable String nome) {
        List<UsuarioDTO> usuariosVisiveis = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getNome().contains(nome)) {
                UsuarioDTO userClone = new UsuarioDTO(users.get(i).getUsuario(),
                        users.get(i).getNome(),
                        users.get(i).isAutenticado());
                usuariosVisiveis.add(userClone);
            }
        }
        return usuariosVisiveis;
    }
}
