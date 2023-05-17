package com.sandra.game.services;

import com.sandra.game.entities.Pony;
import com.sandra.game.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@AllArgsConstructor
public class PonyService {

    private PonyRepository ponyRepository;
    private StatisticsRepository statisticsRepository;
    private DecisionsRepository decisionsRepository;
    private GameStatusRepository gameStatusRepository;
    private UserRepository userRepository;

    public Pony searchPonies(int position) { return ponyRepository.findByPosition(position); }

    public Pony yourBestScore() {
        var pony = ponyRepository.findAll().stream().max(Comparator.comparingInt(Pony::getFriendshipBar));
        return pony.get();
    }

    public void updateStatistics(String userId, int selection) {
        var user = userRepository.findById(userId);
        var gameStatus = user.get().getGameStatuses().stream().filter(status -> !status.isComplete()).findFirst();
        var statistics = gameStatus.get().getStatistics();
        if (selection == 1) {
            statistics.setFun(statistics.getFun() + 3);
            statistics.setKnowledge(statistics.getKnowledge() - 1);
        }
        if (selection == 2) {
            statistics.setKnowledge(statistics.getKnowledge() + 2);
            statistics.setPopularity(statistics.getPopularity() - 1);
        } else {
            statistics.setPopularity(statistics.getPopularity() + 1);
            statistics.setFun(statistics.getPopularity() - 1);
        }

        statisticsRepository.save(statistics);
    }

    public void updateChosenSelection(String userId, String question, String selection) {
        var user = userRepository.findById(userId);
        var gameStatus = user.get().getGameStatuses().stream().filter(status -> !status.isComplete()).findFirst();
        var decisions = gameStatus.get().getDecisions();
        decisions.setQuestion(question);
        decisions.setOptionText(selection);
        decisionsRepository.save(decisions);
    }

    //public Statistics myStatistics(String id){ return statisticsRepository.findBy(id);}
    //https://www.codegrepper.com/code-examples/java/Get+Max+Value+Element+From+List+With+Objects


}
