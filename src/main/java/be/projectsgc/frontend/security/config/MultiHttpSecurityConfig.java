package be.projectsgc.frontend.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * Configures web security and rest api security aspects.
 */

@Configuration
@EnableWebSecurity
//@ComponentScan(basePackageClasses = UserServiceImpl.class)
public class MultiHttpSecurityConfig {

    @Configuration
    @Order(1)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.requestMatcher(request -> {
                final String url = request.getServletPath() + request.getPathInfo();
                return !(url.startsWith("/api/"));
            });

            http.authorizeRequests()
                    .antMatchers("/resources/**", "/api/logout").permitAll()
                    .antMatchers("/client/**").access("hasRole('CLIENT')")
                    //.anyRequest().authenticated()      // remaining URL's require authentication
                    .and()
                    .formLogin()
                    .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout"))
                    .logoutSuccessUrl("/")
                    .and()
                    .csrf();
        }
    }

    /*@Configuration
    @Order(2)
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private UserService userService;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // disable caching
            http.headers().cacheControl();

            http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/users/").permitAll()
                    .antMatchers(HttpMethod.POST, "/api*//**").authenticated()
                    .antMatchers(HttpMethod.PUT, "/api*//**").authenticated()
                    .antMatchers(HttpMethod.DELETE, "/api*//**").authenticated()
                    .antMatchers(HttpMethod.GET, "/api/logout").authenticated()
                    .antMatchers(HttpMethod.GET, "/api/users/").authenticated()
                    .anyRequest().permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/api/logout")
                    .and()
                    .headers()
                    .addHeaderWriter(new StaticHeadersWriter("Access-Control-Expose-Headers", "Authorization"))
                    .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*"))
                    .and()
                    // We filter the api/login requests
                    .addFilterBefore(new JWTLoginFilter("/api/login", authenticationManager(), userService, passwordEncoder), UsernamePasswordAuthenticationFilter.class)
                    // And filter other requests to check the presence of JWT in header
                    .addFilterBefore(new JWTAuthenticationFilter(userService), UsernamePasswordAuthenticationFilter.class);
        }
    }*/
}
