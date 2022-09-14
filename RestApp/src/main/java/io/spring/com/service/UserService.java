package io.spring.com.service;

import io.spring.com.entity.User;
import io.spring.com.entity.enums.Role;
import io.spring.com.respository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getOne(int id){
        return userRepository.findById(id).get();
    }

    public boolean save(User user){

        User userFromDb = userRepository.findByUsername(user.getUsername());

        if(userFromDb == null) {
            userRepository.save(user);
            user.setRoles(Set.of(Role.USER));
            return true;
        }
        else
            return false;
    }

    public boolean update(User user, int id) {

        User userFromDb = userRepository.findById(id).get();
        System.out.println(userFromDb);

        if(userFromDb != null){
            userFromDb.setUsername(user.getUsername());
            userFromDb.setPassword(passwordEncoder.encode(user.getPassword()));
            userFromDb.setEmail(user.getEmail());
            userFromDb.setRoles(user.getRoles());
            System.out.println(userFromDb);

            userRepository.save(userFromDb);

            return true;
        }

        else {
            return false;
        }

    }
    public boolean delete(int id){

        if(userRepository.findById(id).get() != null) {
            userRepository.deleteById(id);
            return true;
        }

        else {
            return false;
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFromDb = userRepository.findByUsername(username);

        if(userFromDb == null){
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }

        return userRepository.findByUsername(username);
    }
}
