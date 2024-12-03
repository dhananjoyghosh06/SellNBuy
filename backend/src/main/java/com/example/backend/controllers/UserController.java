package com.example.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.LoginRequest;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.backend.entity.User;
import com.example.backend.services.JwtService;
import com.example.backend.services.OrderService;
import com.example.backend.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @Autowired
    OrderService orderService;
    // private BcryptPasswordEncoder

    @PostMapping("/signup")
    public ResponseEntity<?> postMethodName(@RequestBody User user) {
        try {
            String token = userService.signup(user);
            return new ResponseEntity<>(token, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> postMethodName(@RequestBody LoginRequest credentials) {
        try {
            String token = userService.handleLogin(credentials);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/getUsername")
    public ResponseEntity<?> getMethodName(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Missing or invalid Authorization header");
        }

        // Extract the token
        String token = authorizationHeader.substring(7);
        String email = jwtService.extractUsername(token);
       
        return new ResponseEntity<>(userService.getUsername(email),HttpStatus.OK);
    }
    
    @GetMapping("/authCheck")
    public ResponseEntity<?> getMethodName() {
        try{
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(false, HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/addTocart/{productId}")
    public ResponseEntity<?> putMethodName(HttpServletRequest request, @PathVariable Long productId) {
        
         try{
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body("Missing or invalid Authorization header");
            }
             String token = authHeader.substring(7); // userid
             String email = jwtService.extractUsername(token); //email
             String username = userService.getUsername(email); // username
             Long id = userService.getId(email); // userId
             Long quantity = 1L; // quantity 
             boolean result = orderService.createOrder(id,productId,username,quantity);
             if(result == false) throw new RuntimeException("user not saved");
             else return new ResponseEntity<>("Order saved Successfully", HttpStatus.OK);
         }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
         }

        
    }
    
}
