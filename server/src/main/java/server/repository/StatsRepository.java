package server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import server.mapper.ServerModelMapper;
import server.model.ServerModel;

import java.util.List;

@Repository
public class StatsRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Integer putModelsInBD(ServerModel... models) {
        /*jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS content");
        jdbcTemplate.execute("USE content");
        jdbcTemplate.execute("DROP TABLE IF EXISTS stats");
        jdbcTemplate.execute("CREATE TABLE IF EXISTS stats " +
                " (id INT AUTO_INCREMENT PRIMARY KEY, " +
                "authorID INT, " +
                "photo VARCHAR(200), " +
                "name VARCHAR(1000), " +
                "info VARCHAR(20000))");*/
        jdbcTemplate.execute("USE content");
        for (ServerModel model : models) {
            jdbcTemplate.update("INSERT INTO stats (authorID, photo, name, info, position, type, mainName) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)", model.getAuthorID(), model.getPhoto(), model.getName(), model.getInfo(), model.getPosition(),
                    model.getType(), models[0].getName());
        }

        return 1;
    }

    public List<ServerModel> getModelsByUserId(Long id, Integer type) {
        jdbcTemplate.execute("USE content");
        return jdbcTemplate.query("SELECT * FROM stats WHERE authorID = ? AND type = ? AND position = ?",
                new ServerModelMapper(), id, type, 1);
    }
}
