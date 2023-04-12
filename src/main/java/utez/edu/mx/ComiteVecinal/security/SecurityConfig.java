package utez.edu.mx.ComiteVecinal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((requests) -> {
            requests.requestMatchers("/static/**", "/css/**", "/js/**", "/images/**").permitAll();

            requests.requestMatchers("/usuario/registrar","/usuario-presidente/registrar","/usuario-presi/registrar",
                    "/enlaces","/enlace/registrar","/enlace/actualizar/{id}","/enlace/{id}","/municipios","/municipio/registrar",
                    "/municipio/actualizar/{id}","/municipio/{id}","/categorias","/categoria/registrar","/categoria/actualizar/{id}",
                    "/categoria/{id}"
            ).hasAuthority("ROLE_ADMINISTRADOR");

            requests.requestMatchers("/colonias","/colonia/registrar","/colonia/actualizar/{id}","/colonia/{id}",
                    "/comites","/comite/registrar","/comite/actualizar/{id}","/comite/{id}",
                    "/integrantes","/integrante/registrar","/agregar-integrantes/{id}","/editar-integrantes/{id}",
                    "/guardar-integrante","/eliminar-integrante/{id}","/cargar-integrante/{id}","/presidente/registrar/{id}",
                    "/comite/integrantes","/comite/integrantes/{id}","/integrante/usuario/registrar","/integrante/registrar/{id}",
                    "/integrante/actualizar/{id}"
            ).hasAuthority("ROLE_ENLACE");

            requests.requestMatchers("/incidencias",
                    "/incidencia/comentar/{id}").hasAuthority("ROLE_PRESIDENTE");

            requests.requestMatchers("/default")
                    .hasAnyAuthority("ROLE_ADMINISTRADOR","ROLE_ENLACE","ROLE_PRESIDENTE");

            requests.anyRequest().authenticated();
        });
        http.formLogin((login) -> {
            login.permitAll().loginPage("/login").defaultSuccessUrl("/default").usernameParameter("username").passwordParameter("password")
                    .failureForwardUrl("/403");
        });
        http.logout((logout) ->{
            logout.permitAll();
        });
        return http.build();
    }

    public static void main(String[] args) {
        System.out.println("pass: " + new BCryptPasswordEncoder().encode("1234"));
    }
}

