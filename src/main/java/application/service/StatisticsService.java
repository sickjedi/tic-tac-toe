package application.service;

import application.repository.GameRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatisticsService {
    private GameRepository gameRepository;

    @Autowired
    public StatisticsService (GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public String getStats() {
        ObjectMapper mapper = new ObjectMapper();
        String result = "";

        try {
            result = mapper.writeValueAsString(GameRepository.players);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "{\"stats\":" + result + "}";
    }
}
