package br.com.vendas.controller;

import br.com.vendas.exception.SenhaInvalidaException;
import br.com.vendas.model.dto.CrendenciaisDTO;
import br.com.vendas.model.dto.TokenDTO;
import br.com.vendas.model.entity.Usuario;
import br.com.vendas.security.jwt.JwtService;
import br.com.vendas.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody @Valid Usuario usuario) {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return  usuarioService.salvar(usuario);
    }

    @PostMapping(path = "/auth")
    public TokenDTO autenticar(@RequestBody CrendenciaisDTO crendenciaisDTO) {
        try {
            Usuario usuario =
                    Usuario.builder()
                            .login(crendenciaisDTO.getLogin())
                            .senha(crendenciaisDTO.getSenha())
                            .build();

            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);

        } catch (UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
