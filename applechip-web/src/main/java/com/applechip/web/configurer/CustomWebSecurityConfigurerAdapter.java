package com.applechip.web.configurer;

// @Configuration
// @EnableWebSecurity
public class CustomWebSecurityConfigurerAdapter {
  
  
//  <port-mappings>
//  <port-mapping http="${web.port}" https="${web.httpsPort}"/>
//</port-mappings>
//
//<intercept-url pattern="/" requires-channel="${web.requiresChannel}"/>



  // public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
  //
  // @Autowired
  // private UserRepository userRepository;
  //
  // @Override
  // public void configure(WebSecurity web) throws Exception {
  // web.ignoring().antMatchers("/static/**");
  // }
  //
  // @Override
  // protected void configure(HttpSecurity http) throws Exception {
  // http.formLogin().loginPage("/login").loginProcessingUrl("/login/authenticate").failureUrl("/login?error=bad_credentials").and().logout().deleteCookies("JSESSIONID").logoutUrl("/logout")
  // .logoutSuccessUrl("/login").and().authorizeRequests().antMatchers("/auth/**", "/login",
  // "/signin/**", "/signup/**",
  // "/user/register/**").permitAll().antMatchers("/**").hasRole("USER").and()
  // .apply(new SpringSocialConfigurer());
  // }
  //
  // @Override
  // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  // auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
  // }
  //
  // @Bean
  // public PasswordEncoder passwordEncoder() {
  // return new BCryptPasswordEncoder(10);
  // }
  //
  // @Bean
  // public SocialUserDetailsService socialUserDetailsService() {
  // return new SimpleSocialUserDetailsService(userDetailsService());
  // }
  //
  // @Bean
  // public UserDetailsService userDetailsService() {
  // return new RepositoryUserDetailsService(userRepository);
  // }
}
