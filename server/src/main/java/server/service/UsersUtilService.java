package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.model.Person;
import server.repository.UserRepository;

@Service
public class UsersUtilService {

    @Autowired
    UserRepository repository;

    public Person findUser(String name, String password){
        return repository.findUser(name, password);
    }

    public Integer addUser(String name, String pass) {
        return repository.addUser(name, pass);
    }

}
