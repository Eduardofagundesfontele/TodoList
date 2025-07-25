package br.com.eduardofagundes.todoList.Filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.eduardofagundes.todoList.user.IUserRepository;
import jakarta.servlet.FilterChain;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
    @Autowired
    private IUserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();
        if (servletPath.startsWith("/tasks/")) {//pegar autenticação (Usuário senha)
            var authorization = request.getHeader("Authorization");

            if (authorization == null || !authorization.startsWith("Basic ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            // Extrai a parte codificada em Base64 da string de autorização.
            var authEncoded = authorization.substring("Basic".length()).trim();

            // Decodifica a string Base64 de volta para bytes originais.
            byte[] authDecode = Base64.getDecoder().decode(authEncoded);
            // Converte os bytes decodificados em uma string legível ("usuario:senha").
            var authString = new String(authDecode);

            // Divide a string "usuario:senha" em um array de credenciais.
            String[] credentials = authString.split(":");


            // Extrai o nome de usuário do array de credenciais.
            String username = credentials[0];
            // Extrai a senha do array de credenciais.
            String password = credentials[1];


            //Validar Usuário
            var user = userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401);
            } else {
                //validar senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified) {
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
            }


        }else{  filterChain.doFilter(request, response);}


    }

}
