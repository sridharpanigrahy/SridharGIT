package com.example.SpringBootApp1.controller;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
// import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController
{
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    String admin(/*Authentication authentication */)
    {
        /* To get the user principle after authentication .

            import org.springframework.security.core.Authentication;
            import org.springframework.security.core.context.SecurityContextHolder;
            import org.springframework.security.core.userdetails.UserDetails;
            //check if user is login
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                 UserDetails userDetail = (UserDetails) auth.getPrincipal();
         */
      //  return "Welcome to : " + authentication.toString();
        return "security code has been commented in file : SecurityController.java and SpringSecurityConfigDemo.java";
    }

   /* @RequestMapping(value = "/user", method = RequestMethod.GET)
    String user(Authentication authentication)
    {
        return "Welcome to : " + authentication.toString();
    }

    @RequestMapping(value = "/dba", method = RequestMethod.GET)
    String dba(Authentication authentication)
    {
        return "Welcome to : " + authentication.toString();
    }*/
}
