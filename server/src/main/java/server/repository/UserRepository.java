package server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import server.mapper.UserMapper;
import server.model.Person;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate template;

    MessageDigest digest;
    {
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public Person findUser(String name, String password) {
        template.execute("USE persons");

        return template.query("SELECT * FROM allPersons WHERE login = ? AND pass = ?", new UserMapper(), name, password)
                .stream().findFirst().filter(person -> person.getName().equals(name) &&
                        person.getPassword().equals(password)).orElse(null);
    }

    public Integer addUser(String name, String pass) {
        template.execute("USE persons");

        return template.update("INSERT INTO allPersons (login, pass) "+
                "VALUES (?, ?) ", name, pass);
    }
}
