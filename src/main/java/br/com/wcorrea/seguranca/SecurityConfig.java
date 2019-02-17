package br.com.wcorrea.seguranca;


import br.com.wcorrea.seguranca.cookies.JsfAccessDeniedHandler;
import br.com.wcorrea.seguranca.cookies.JsfLoginUrlAuthenticationEntryPoint;
import br.com.wcorrea.seguranca.cookies.JsfRedirectStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public AppUserDetailsService userDetailsService() {
        return new AppUserDetailsService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JsfLoginUrlAuthenticationEntryPoint jsfLoginEntry = new JsfLoginUrlAuthenticationEntryPoint();
        jsfLoginEntry.setLoginFormUrl("/Login.xhtml");
        jsfLoginEntry.setRedirectStrategy(new JsfRedirectStrategy());

        JsfAccessDeniedHandler jsfDeniedEntry = new JsfAccessDeniedHandler();
        jsfDeniedEntry.setLoginPath("/AcessoNegado.xhtml");
        jsfDeniedEntry.setContextRelative(true);

        http
                .csrf()
                    .disable()
                .headers()
                    .frameOptions()
                    .sameOrigin()
                    .and()

                .authorizeRequests()
                    .antMatchers("/Login.html", "/Erro.xhtml", "/javax.faces.resource/**").permitAll()
                    .antMatchers("/Dashboard.xhtml", "/AcessoNegado.xhtml", "/NaoEncontrada.xhtml", "/dialogos/**").authenticated()

//                  CLASSE DE DESPESAS
                    .antMatchers("/pages/base/classe-despesa/classe-despesa-cadastro.xhtml").hasAnyRole("ADMINISTRADOR","CLASSE_DESPESA_SALVAR")
                    .antMatchers("/pages/base/classe-despesa/classe-despesa-pesquisa.xhtml").hasAnyRole("ADMINISTRADOR","CLASSE_DESPESA_PESQUISAR", "CLASSE_DESPESA_EXCLUIR")

//                  UNIVERSIDADE
                    .antMatchers("/pages/base/universidade/universidade-cadastro.xhtml").hasAnyRole("ADMINISTRADOR","UNIVERSIDADE_SALVAR")
                    .antMatchers("/pages/base/universidade/universidade-pesquisa.xhtml").hasAnyRole("ADMINISTRADOR","UNIVERSIDADE_PESQUISAR", "UNIVERSIDADE_EXCLUIR")




                    .antMatchers("/pages/**").hasRole("GAMBIARRA_PARA_BLOQUEAR_A_PORRA_TODA")
                    .and()

                .formLogin()
                    .loginPage("/Login.xhtml")
                    .failureUrl("/Login.xhtml?invalid=true")
                    .and()

                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl("/Login.xhtml")
                    .and()

                .exceptionHandling()
                    .accessDeniedPage("/AcessoNegado.xhtml")
                    .authenticationEntryPoint(jsfLoginEntry)
                    .accessDeniedHandler(jsfDeniedEntry);
    }
}
