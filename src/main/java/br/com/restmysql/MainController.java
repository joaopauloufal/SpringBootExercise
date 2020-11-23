package br.com.restmysql;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/users")
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "")
    public ResponseEntity<User> addNewUser(@RequestParam String name, @RequestParam String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        userRepository.save(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);

    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Integer id, @RequestParam String name, @RequestParam String email) {
        Optional<User> searchedUser = userRepository.findById(id);
        if (searchedUser.isPresent()){
            User user = searchedUser.get();
            user.setEmail(email);
            user.setName(name);
            userRepository.save(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") Integer id) {
        Optional<User> searchedUser = userRepository.findById(id);
        if (searchedUser.isPresent()) {
            User user = searchedUser.get();
            userRepository.deleteById(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping(path = "")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
    
}
