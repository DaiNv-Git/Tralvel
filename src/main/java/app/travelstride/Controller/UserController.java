package app.travelstride.Controller;

import app.travelstride.Model.Jpa.UserRepository;
import app.travelstride.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }
    @PostMapping("/create1")
    public ResponseEntity<User> createUser1(@RequestBody User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }

    // Update user
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User updateUser = optionalUser.get();
            updateUser.setName(user.getName());
            updateUser.setUserName(user.getUserName());
            updateUser.setPassword(user.getPassword());
            return ResponseEntity.ok(userRepository.save(updateUser));
        }
        return ResponseEntity.badRequest().body("User not found");
    }

    // Delete user
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted");
    }

    // List all users
    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String name) {
        List<User> users;
        if (name != null && !name.isEmpty()) {
            users = userRepository.findByNameContainingIgnoreCase(name);
        } else {
            users = userRepository.findAll();
        }
        return ResponseEntity.ok(users);
    }

    // Login (No Encryption)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String userName, @RequestParam String password) {
        Optional<User> user = userRepository.findByUserNameAndPassword(userName, password);
        if (user.isPresent()) {
            return ResponseEntity.ok("Login Success");
        }
        return ResponseEntity.status(401).body("Invalid Username or Password");
    }
}
