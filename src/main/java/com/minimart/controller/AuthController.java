// controller/AuthController.java
package com.minimart.controller;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

 @PostMapping("/login")
 public Map<String, Object> login(@RequestBody Map<String, String> credentials) {
  String username = credentials.get("username");
  String password = credentials.get("password");

  // In production, validate against database
  if (("admin".equals(username) && "admin123".equals(password)) ||
          ("cashier".equals(username) && "cashier123".equals(password))) {

   Map<String, Object> response = new HashMap<>();
   response.put("token", "dummy-token-" + System.currentTimeMillis());

   Map<String, String> user = new HashMap<>();
   user.put("username", username);
   user.put("role", "admin".equals(username) ? "admin" : "cashier");
   response.put("user", user);

   return response;
  }

  throw new RuntimeException("Invalid credentials");
 }
}