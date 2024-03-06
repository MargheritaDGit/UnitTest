package com.example.UnitTestdev;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api")
public class Controller {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public Users createUser (@RequestBody Users users) {
        return userRepository.save(users);
    }

    @GetMapping("/get/{id}")
    public Users getUser (@PathVariable Long id){
        return userRepository.findById(id).orElse(null);
    }

    @PutMapping("/put/{id}")
    public Users putUser (@PathVariable Long id, @RequestBody Users users){
        Users users2 = userRepository.findById(id).orElse(null);
        users2.setName(users.getName());
        users2.setSurname(users.getSurname());
        users2.setMail(users.getMail());
        return userRepository.save(users2);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById (@PathVariable Long id){
        userRepository.deleteById(id);
    }
}
