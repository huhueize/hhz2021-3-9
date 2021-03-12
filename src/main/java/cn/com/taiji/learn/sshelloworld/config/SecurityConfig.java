package cn.com.taiji.learn.sshelloworld.config;


import cn.com.taiji.learn.sshelloworld.extend.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${sshelloworld.maximumSessions}")
    private Integer maximumSessions;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
//                .withUser("hhz")
//                .password(passwordEncoder().encode("123"))
//                .roles("USER");
//        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
//                .withUser("hhz")
//                .password(passwordEncoder().encode("abc"))
//                .roles("ADMIN");
        auth.userDetailsService(customUserDetailsService());
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService(){
        return new CustomUserDetailsService();
    }



//   密码加密
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws  Exception{
        http.authorizeRequests()
//                .antMatchers("/user/test/bar").permitAll()
//                .antMatchers("/simpleMailMessage").permitAll()
                .antMatchers("/signup","/user/register").permitAll()
                .antMatchers("/sendEmail","/user/verify/**").permitAll()
//                .antMatchers("/user/test/only-user").hasRole("USER")
//                .antMatchers("/user/test/foo").hasAnyRole("ADMIN","USER")
//                .antMatchers("/user/test/ac").access("hasRole('ROLE_USER')")
//                .antMatchers("/user/test/ac").access("@customAccess.hhz(request,authentication)")
//                .anyRequest().authenticated();
                .anyRequest().access("@customAccess.hhz(request,authentication)");


//        http.httpBasic();

//        http.formLogin()
//                .loginPage("/login").permitAll()
//                .loginProcessingUrl("/doLogin");
//
//        http.oauth2Login().loginPage("/login");

        http.oauth2Login();

        http.cors();

        http.logout().permitAll().logoutUrl("/logout").permitAll()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID","hhz");


        http.rememberMe()
                .rememberMeCookieName("hhz")
                .tokenValiditySeconds(3600*24*10);

        //        http.httpBasic();
        //basic认证和logout不是能配合的：https://stackoverflow.com/questions/25191918/spring-web-security-logout-not-working-with-httpbasic-authentication

        //sesion策略和logout退出的矛盾问题

//        http.sessionManagement()
//                //指定最大的session并发数量---即一个用户只能同时在一处登陆（腾讯视频的账号好像就只能同时允许2-3个手机同时登陆）
//                .maximumSessions(1)
//                //当超过指定的最大session并发数量时，阻止后面的登陆（感觉貌似很少会用到这种策略）
//                .maxSessionsPreventsLogin(true);


        http.csrf().disable();

        http.cors();
    }

    @Override
    public void configure(WebSecurity web) throws  Exception {
        web.ignoring().antMatchers("/h2-console/**");
        web.ignoring().antMatchers("/images/**","/css/**","js/**","favicon.ico");
        // TODO 此处忽略的URL过多，可能不太安全,建议根据需求情况适当开启
        web.ignoring().antMatchers("/**/*.js", "/lang/*.json", "/**/*.css", "/**/*.js", "/**/*.map", "/**/*.html", "/**/*.png"
                , "/**/*.ttf", "/**/*.svg", "/**/*.woff");

        // swagger start TODO
        web.ignoring().antMatchers("/doc.html");
        web.ignoring().antMatchers("/swagger-ui.html");
        web.ignoring().antMatchers("/swagger-resources/**");
        web.ignoring().antMatchers("/images/**");
        web.ignoring().antMatchers("/webjars/**");
        web.ignoring().antMatchers("/v2/api-docs");
        web.ignoring().antMatchers("/v2/api-docs-ext");
        web.ignoring().antMatchers("/configuration/ui");
        web.ignoring().antMatchers("/configuration/security");
        // swagger end TODO
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1
        corsConfiguration.addAllowedHeader("*"); // 2
        // 允许所有方法包括"GET", "POST", "DELETE", "PUT"等等
        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.setMaxAge(1800l);//30分钟
        // 设为true则Cookie可以包含在CORS请求中一起发给服务器。
        corsConfiguration.setAllowCredentials(true);
        // CORS请求时。XMLHttpRequest对象的getResponseHeader()方法只能拿到6个基本字段：
        // Cache-Control、Content-Language、Content-Type、Expires、Last-Modified、Pragma。
        // 如果想拿到其他字段，
        //允许clienHeaderWriterFiltert-site取得自定义得header值
        corsConfiguration.addExposedHeader(HttpHeaders.AUTHORIZATION);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }



}
