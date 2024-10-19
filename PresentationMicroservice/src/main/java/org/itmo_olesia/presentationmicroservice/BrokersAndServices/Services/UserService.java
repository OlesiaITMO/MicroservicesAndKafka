package org.itmo_olesia.presentationmicroservice.BrokersAndServices.Services;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.itmo_olesia.dto.DTO.UserDTO;
import org.itmo_olesia.dto.Role.Role;
import org.itmo_olesia.presentationmicroservice.Repositories.IOwnerRepository;
import org.itmo_olesia.presentationmicroservice.Repositories.IUserRepository;
import org.itmo_olesia.presentationmicroservice.jpa.Owner;
import org.itmo_olesia.presentationmicroservice.jpa.User;
import org.itmo_olesia.presentationmicroservice.utils.OwnerMapper;
import org.itmo_olesia.presentationmicroservice.utils.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IOwnerRepository ownerRepository;

    private UserMapper userMapper = new UserMapper();
    private OwnerMapper ownerMapper = new OwnerMapper();

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Async
    public void saveUserAsync(User user) {
        userRepository.save(user);
    }



    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        // Получаем имя текущего аутентифицированного пользователя
        log.info("createUser");
        // Создаем нового пользователя с информацией из DTO
        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        //newUser.setRole(Role.valueOf(userDTO.getRole()));
        newUser.setBirthday(userDTO.getBirthday());
        //


        // Сохраняем нового пользователя
        User savedUser = userRepository.save(newUser);

        // Возвращаем сохраненного пользователя
        return userMapper.UserMapping(savedUser);
    }



    /*@Transactional
    public UserDTO createUser(String username, String password, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole( Role.valueOf(role));

        userRepository.save(user);

        return userMapper.UserMapping(user);
    }*/
    @Transactional
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


    @Transactional
    public UserDTO getByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(username);
        return userMapper.UserMapping(user);
    }

    @Transactional
    public Long getIdByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(username);
        return user.getId();
    }

//    @Transactional
//    public UserDTO updateUsername(String  username, String newUsername) {
//        User user = userRepository.findByUsername(username);
//        if (user == null)
//            throw new UsernameNotFoundException(username);
//        user.setUsername(newUsername);
//        userRepository.save(user);
//        return UserMapper.UserMapping(user);
//    }

    @Transactional
    public UserDTO updateBirthday(String username, LocalDate birthday) {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(username);
        user.setBirthday(birthday);
        userRepository.save(user);
        return userMapper.UserMapping(user);
    }

    @Transactional
    public UserDTO updateUsername(String username, String newName) {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException(username);
        user.setUsername(newName);
        userRepository.save(user);
        return userMapper.UserMapping(user);
    }



    @Transactional
    public UserDTO getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return UserMapper.UserMapping(user);
    }

    @Override
    @Transactional
    public UserDTO loadUserByUsername(String username) {
        UserDTO user = userMapper.UserMapping(userRepository.findByUsername(username));
        return user;
    }




    @Transactional
    public List<Long> getCats(String username){
        User user = userRepository.findByUsername(username);
        Owner owner = user.getOwner();
        List<Long> cats = ownerMapper.OwnerMapping(owner).getCats();
        return cats;
    }



    /*
    @Transactional
    public List<Long> getUserCats(Long id) {
        User User = UserRepository.getById(id);
        List<Cat> cats = User.getCats();
        List<Long> cats_long = new ArrayList<>();
        for (Cat cat : cats) {
            cats_long.add(cat.getId());
        }
        return cats_long;
    }*/
}

