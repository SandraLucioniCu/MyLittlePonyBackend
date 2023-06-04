package com.sandra.game.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandra.game.entities.Decision;
import com.sandra.game.entities.GameStatus;
import com.sandra.game.entities.Statistics;
import com.sandra.game.exceptions.NotFoundException;
import com.sandra.game.repositories.GameStatusRepository;
import com.sandra.game.responses.GameStatusDto;
import com.sandra.game.stories.*;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameStatusRepository gameStatusRepository;

    public GameStatusDto getGameStatus(String userId) throws FileNotFoundException, ParseException {

        // busca en la base de datos si existen partidas empezadas
        List<GameStatus> games = gameStatusRepository.findByUserId(userId);
        Optional<GameStatus> optionalGame = games.stream().filter(game -> !game.isFinished()).findFirst();

        Story story = getStoryFromResources();

        // si existen
        if(optionalGame.isPresent()){

            GameStatus game = optionalGame.get();

            // buscar en el json de escenas el nombre de la escena
            Scene currentScene = story.getSceneById(game.getCurrentScene());

            // devolver el texto, las respuestas y los stats
            return new GameStatusDto(
                    game.getStatistics().toDto(),
                    currentScene.getText(),
                    currentScene.getAnswersAsDto());
        }

        // si no existe
        String initScene = story.getSettings().getInit();

        // coger los stats y el nombre de la escena de la propiedad settings en el json
        Scene currentScene = story.getSceneById(initScene);
        Stats initStats = story.getSettings().getStats();

        // insertar el nuevo game status
        GameStatus newGameStatus = new GameStatus(
                userId,
                initScene,
                initStats.ToMongoDbData()
        );
        gameStatusRepository.save(newGameStatus);

        // devolver el texto, las respuestas y los stats
        return new GameStatusDto(
                newGameStatus.getStatistics().toDto(),
                currentScene.getText(),
                currentScene.getAnswersAsDto());
    }

    public GameStatusDto updateGameStatus(String userId, int option) throws FileNotFoundException, ParseException {
        // busca en la base de datos si existen partidas empezadas
        List<GameStatus> games = gameStatusRepository.findByUserId(userId);
        Optional<GameStatus> optionalGame = games.stream().filter(game -> !game.isFinished()).findFirst();

        // si existen
        if(optionalGame.isEmpty()){

            throw new NotFoundException();
        }

        GameStatus gameStatus = optionalGame.get();

        // coger nombre de la scena del objeto que devuelve la base de datos y los stats
        Story story = getStoryFromResources();
        String currentSceneId = gameStatus.getCurrentScene();

        // buscar en el json de escenas el nombre de la escena
        Scene currentScene = story.getSceneById(currentSceneId);

        // devolver el texto, las respuestas y los stats
        Answer answerChosen = currentScene.getAnswers().get(option);
        Changes changesToApply = answerChosen.getChanges();

        // aplicar los cambios a las estadisticas;
        Statistics currentStats = gameStatus.getStatistics();
        Statistics newStats = currentStats.apply(changesToApply);
        String nextSceneId = answerChosen.getGo();

        // actualizar el game status con los valores actuales de escena y estadisticas
        gameStatus.getDecisions().add(new Decision(currentSceneId, option, currentStats, newStats));
        gameStatus.setStatistics(newStats);
        gameStatus.setCurrentScene(nextSceneId);
        gameStatusRepository.save(gameStatus);

        Scene nextScene = story.getSceneById(nextSceneId);

        // pillar los stats y el nombre de la escena de la propiedad settings en el json
        //aplicar los cambios, stats y guardado de historia

        // devolver el texto, las respuestas y los stats
        return new GameStatusDto(
                gameStatus.getStatistics().toDto(),
                nextScene.getText(),
                nextScene.getAnswersAsDto()
        );
    }

    private Story getStoryFromResources() throws FileNotFoundException, ParseException {
        // carga la historia de la carpeta resources
        File file = ResourceUtils.getFile("classpath:stories/story_01.json");

        // transforma el archivo json en un objeto de Java (generalmente es un LinkedHashMap
        Object json = new JSONParser(new FileInputStream(file)).parse();

        // transforma el resultado de la operacion anterior en nuestra clase Java: Story
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(json, Story.class);
    }
}

// https://stackoverflow.com/questions/58703834/how-to-read-json-file-from-resources-in-spring-boot
// https://dzone.com/articles/how-can-we-read-a-json-file-in-java