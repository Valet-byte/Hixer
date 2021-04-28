package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.repositories.DefRepository;

@Service
public class DefService {

    @Autowired
    DefRepository repository;

    public String getStat(Integer numChapter, Integer numSubchapter) {
        if (numChapter >= 0 && numChapter <= 2){
            return repository.getStat(numChapter, numSubchapter);
        } else {
            return null;
        }
    }

    public Integer putStat(Integer numChapter, Integer numSubchapter, String str) {
        return repository.putStats(numChapter, numSubchapter, str);
    }
}
