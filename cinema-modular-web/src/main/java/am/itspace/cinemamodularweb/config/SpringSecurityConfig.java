package am.itspace.cinemamodularweb.config;

import am.itspace.cinemamodularcommon.entity.userdetail.Role;
import am.itspace.cinemamodularweb.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig  extends WebSecurityConfigurerAdapter {

    private final UserDetailsImpl userDetails;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/user/profile")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/user/profile").authenticated()
                .antMatchers("/cinema/**").authenticated()
                .antMatchers("/add/film").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers("/add/film-in/cinema").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers("/add/cinema/page").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers("/catalog/films").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers("/films/premiere").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers("/get/films/rating").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers("/users/all").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers("/add/actor").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers("/film/delete/{id}").hasAnyAuthority(Role.ADMIN.name())
                .antMatchers("/send/comment").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                .antMatchers("/editUser").hasAnyAuthority(Role.USER.name())
                .antMatchers("/editUser/{id").hasAnyAuthority(Role.USER.name())
                .antMatchers("/user/comments").hasAnyAuthority(Role.USER.name())
                .antMatchers("/user/delete/{id}").hasAnyAuthority(Role.USER.name())
                .antMatchers("/buy/ticket/{filmId}/{cinemaId}").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                .antMatchers("/cinema/page/{id}").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                .antMatchers("/get/choose/film/{id}/{cinemaId}").hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
                .anyRequest().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails)
                .passwordEncoder(passwordEncoder);
    }

}
