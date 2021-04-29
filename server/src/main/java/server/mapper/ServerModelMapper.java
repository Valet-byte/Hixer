package server.mapper;

import org.springframework.jdbc.core.RowMapper;
import server.model.ServerModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerModelMapper implements RowMapper<ServerModel> {
    @Override
    public ServerModel mapRow(ResultSet resultSet, int i) throws SQLException {
        return new ServerModel(resultSet.getString("name"),
                resultSet.getString("info"),
                resultSet.getLong("authorID"),
                resultSet.getString("photo"),
                resultSet.getInt("type"));
    }
}
