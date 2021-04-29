package server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.model.ServerModel;
import server.repository.StatsRepository;

import java.util.List;

@Service
public class ServerModelService {
    @Autowired
    StatsRepository repository;

    public Integer putModels(ServerModel... models){
        if (models != null) return repository.putModelsInBD(models);
        else return null;
    }

    public List<ServerModel> getModelByUserId(Long id, Integer type){
        if (id != null && type != null){
            return repository.getModelsByUserId(id, type);
        } else {
            return null;
        }
    }
}
