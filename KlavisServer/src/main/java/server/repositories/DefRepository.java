package server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DefRepository {

    @Autowired
    JdbcTemplate template;

    public String getStat(Integer numChapter, Integer numSubchapter) {

        /*template.execute("CREATE SCHEMA IF NOT EXISTS stats");
        template.execute("USE stats");
        template.execute("DROP TABLE IF EXISTS allStats");
        template.execute("CREATE TABLE IF NOT EXISTS allStats " +
                " (id INT AUTO_INCREMENT PRIMARY KEY, " +
                "numChapters INT NOT NULL, " +
                "numSubchapters INT NOT NULL, " +
                "info VARCHAR(10000) NOT NULL)");*/
        return "creat";
    }

    public Integer putStats(Integer numChapter, Integer numSubchapter, String str) {
        template.execute("USE stats");
        template.update("INSERT INTO allStats (numChapters, numSubchapters, info) " +
                "VALUES(?, ?, ?)", numChapter, numSubchapter, str);
        return 1;
    }
}
