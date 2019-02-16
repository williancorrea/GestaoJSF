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
                    .antMatchers("/Dashboard.xhtml", "/AcessoNegado.xhtml", "/dialogos/**").authenticated()

                    .antMatchers("/pages/**").hasRole("GAMBIARRA_PARA_BLOQUEAR_A_PORRA_TODA")

//                  CLASSE DE DESPESAS
                    .antMatchers("/pages/base/classe-despesa/classe-despesa-cadastro.xhtml").hasAnyRole("CLASSE_DESPESA_SALVAR")
                    .antMatchers("/pages/base/classe-despesa/classe-despesa-pesquisa.xhtml").hasAnyRole("CLASSE_DESPESA_PESQUISAR", "CLASSE_DESPESA_EXCLUIR")

//                  .antMatchers("/pedidos/**").hasAnyRole("VENDEDORES", "AUXILIARES", "ADMINISTRADORES")
//                  .antMatchers("/produtos/**", "/relatorios/**").hasRole("ADMINISTRADORES")

                    .and()

                .formLogin()
                    .loginPage("/Login.xhtml")
                    .failureUrl("/Login.xhtml?invalid=true")
                    .and()

                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .invalidateHttpSession(true)
                    .and()

                .exceptionHandling()
                    .accessDeniedPage("/AcessoNegado.xhtml")
                    .authenticationEntryPoint(jsfLoginEntry)
                    .accessDeniedHandler(jsfDeniedEntry);
    }
}
