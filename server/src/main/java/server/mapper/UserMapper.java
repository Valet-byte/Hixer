package server.mapper;

import org.springframework.jdbc.core.RowMapper;
import server.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Person(resultSet.getLong("id"),
                resultSet.getString("login"), resultSet.getString("pass"));
    }
}
