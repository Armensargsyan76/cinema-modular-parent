package am.itspace.cinemamodularrest.config;

import am.itspace.cinemamodularcommon.entity.userdetail.Role;
import am.itspace.cinemamodularrest.security.CurrentUserDetailServiceImpl;
import am.itspace.cinemamodularrest.security.JWTAuthenticationTokenFilter;
import am.itspace.cinemamodularrest.security.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CurrentUserDetailServiceImpl userDetailService;
    private final PasswordEncoder passwordEncoder;
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users/*").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/users/*").permitAll()
                .antMatchers(HttpMethod.POST, "/users/auth").hasAnyAuthority(Role.USER.name())
                .antMatchers(HttpMethod.DELETE, "/users/*").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/getImage").permitAll()
                .antMatchers(HttpMethod.GET, "/actors/*").permitAll()
                .antMatchers(HttpMethod.POST, "/actors/*").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/actors/*").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/actors/*").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/director/*").permitAll()
                .antMatchers(HttpMethod.POST, "/director/*").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/director/*").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/director/*").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/films/*").permitAll()
                .antMatchers(HttpMethod.POST, "/films/*").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/films/*").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/films/*").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/genre/*").permitAll()
                .antMatchers(HttpMethod.POST, "/genre/*").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/genre/*").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/time/since/*").permitAll()
                .antMatchers(HttpMethod.POST, "/time/since/*").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/time/since/*").hasAnyAuthority(Role.ADMIN.name())
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        http.headers().cacheControl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public JWTAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JWTAuthenticationTokenFilter();
    }

}
