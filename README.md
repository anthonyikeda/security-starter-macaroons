# Overview

This is the inception of tryiong to create a spring-security module
that supports macaroons.

It's very early to tell if this will work, though if you have any input, contact
me and I can add you as a contributor.

More coming soon.

## Caveats (Yes that is a joke)
Since we are trying to retrofit a decentralized auth system to a system that assumes
a centralised authentication server there are some caveats (pun intended) we need to follow:

<ul>
    <li>The macaroon id is what we will map to the principal</li>
    <li>Credentials will be populated with the secret of the macaroon</li>
</ul>

## Configuration (eventually)

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/public/**")
    );

    private static final RequestMatcher PROTECTED_URLS = new NegatedRequestMatcher(PUBLIC_URLS);

    private MacaroonAuthenticationProvider authProvider;

    public SecurityConfig(MacaroonAuthenticationProvider _provider) {
        this.authProvider = _provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .and()
                .authenticationProvider(this.authProvider)
                .addFilterBefore(restAuthenticationFilter(), AnonymousAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable();
    }

    @Bean
    MacaroonAuthenticationFilter restAuthenticationFilter() throws Exception {
        final MacaroonAuthenticationFilter filter = new MacaroonAuthenticationFilter(PROTECTED_URLS);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(successHandler());
        return filter;
    }

    @Bean
    SimpleUrlAuthenticationSuccessHandler successHandler() {
        final SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
        successHandler.setRedirectStrategy(new NoRedirectStrategy());
        return successHandler;
    }

    @Bean
    FilterRegistrationBean disableAutoRegistration(final MacaroonAuthenticationFilter filter) {
        final FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    AuthenticationEntryPoint forbiddenEntryPoint() {
        return new HttpStatusEntryPoint(FORBIDDEN);
    }
}
```