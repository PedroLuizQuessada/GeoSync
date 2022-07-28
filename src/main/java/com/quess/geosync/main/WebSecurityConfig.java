package com.quess.geosync.main;

import com.quess.geosync.usuario.CustomUsuarioDetailsService;
import com.quess.geosync.usuario.UsuarioRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UsuarioRepository repo;

    public WebSecurityConfig(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(new CustomUsuarioDetailsService(repo));
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/voltar/**").authenticated()
                .antMatchers("/pontos/**").authenticated()
                .antMatchers("/pontos/ativotoggle/**").authenticated()
                .antMatchers("/pontos/renomear/**").authenticated()
                .antMatchers("/pontos/adotarcentral/**").authenticated()
                .antMatchers("/usuarios").authenticated()
                .antMatchers("/usuarios/new").authenticated()
                .antMatchers("/usuarios/save").authenticated()
                .antMatchers("/usuarios/edit/**").authenticated()
                .antMatchers("/usuarios/delete/**").authenticated()
                .antMatchers("/usuarios/blocktoggle/**").authenticated()
                .antMatchers("/usuarios/admtoggle/**").authenticated()
                .anyRequest().permitAll()

                .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("email")
                    .defaultSuccessUrl("/pontos/true")
                    .permitAll()

                .and()
                .logout()
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .invalidateHttpSession(false);

        http.sessionManagement()
                .invalidSessionUrl("/login?sessaoexpirada");
    }
}
