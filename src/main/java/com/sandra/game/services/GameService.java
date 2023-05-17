package com.sandra.game.services;

import com.sandra.game.entities.HistoryText;
import com.sandra.game.repositories.GameStatusRepository;
import com.sandra.game.repositories.HistoryRepository;
import com.sandra.game.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GameService {

    private HistoryRepository historyRepository;
    private GameStatusRepository gameStatusRepository;
    private UserRepository userRepository;


    private int userCurrentHistory(String userId){
        var user = userRepository.findById(userId);
        var gameStatus = user.get().getGameStatuses().stream().filter(status -> !status.isComplete()).findFirst();
        var index = gameStatus.get().getUserCurrentIndex();

        List<HistoryText> historyText = historyRepository.findAll();
        int counter = 0;
        for (int i = 0; i < historyText.size(); i++) {
            if (historyText.get(i).getIndex() == index)
                counter = historyText.get(i+1).getIndex();
        }

        return counter;
    }

    public String historyText(String userId){
        var history = historyRepository.findByIndex(userCurrentHistory(userId)).getQuestion();
        return history;
    }

    public List<String> answerText(String userId){
        List<String> history= new ArrayList<>();
        history.add(historyRepository.findByIndex(userCurrentHistory(userId)).getAnswerOne());
        history.add(historyRepository.findByIndex(userCurrentHistory(userId)).getAnswerTwo());
        history.add(historyRepository.findByIndex(userCurrentHistory(userId)).getAnswerThree());

        return history;
    }



}
