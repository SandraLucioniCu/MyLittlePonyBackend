package com.sandra.game.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandra.game.entities.Decision;
import com.sandra.game.entities.GameStatus;
import com.sandra.game.entities.Statistics;
import com.sandra.game.exceptions.NotFoundException;
import com.sandra.game.exceptions.SceneNotFoundException;
import com.sandra.game.repositories.GameStatusRepository;
import com.sandra.game.responses.DecisionDto;
import com.sandra.game.responses.FinishedGameDto;
import com.sandra.game.responses.GameHistoryDto;
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
import java.util.*;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameStatusRepository gameStatusRepository;

    public GameStatusDto getGameStatus(String userId, String storyId) throws FileNotFoundException, ParseException {

        // busca en la base de datos si existen partidas empezadas
        List<GameStatus> games = gameStatusRepository.findByUserIdAndStoryId(userId, storyId);
        Optional<GameStatus> optionalGame = games.stream().filter(game -> !game.isFinished()).findFirst();

        Story story = getStoryFromResources(storyId);

        // si existen
        if(optionalGame.isPresent()){

            GameStatus game = optionalGame.get();

            // buscar en el json de escenas el nombre de la escena
            Scene currentScene = story.getSceneById(game.getCurrentScene());

            // devolver el texto, las respuestas y los stats
            return new GameStatusDto(
                    game.getStatistics().toDto(),
                    currentScene.getText(),
                    currentScene.getAnswersAsDto(),
                    false);
        }

        // si no existe
        String initScene = story.getSettings().getInit();

        // coger los stats y el nombre de la escena de la propiedad settings en el json
        Scene currentScene = story.getSceneById(initScene);
        Stats initStats = story.getSettings().getStats();

        // insertar el nuevo game status
        GameStatus newGameStatus = new GameStatus(
                userId,
                storyId,
                initScene,
                initStats.ToMongoDbData()
        );
        gameStatusRepository.save(newGameStatus);

        // devolver el texto, las respuestas y los stats
        return new GameStatusDto(
                newGameStatus.getStatistics().toDto(),
                currentScene.getText(),
                currentScene.getAnswersAsDto(),
                false);
    }

    public GameStatusDto updateGameStatus(String userId,  String storyId, int option) throws FileNotFoundException, ParseException {
        // busca en la base de datos si existen partidas empezadas
        List<GameStatus> games = gameStatusRepository.findByUserIdAndStoryId(userId, storyId);
        Optional<GameStatus> optionalGame = games.stream().filter(game -> !game.isFinished()).findFirst();

        // si existen
        if(optionalGame.isEmpty()){
            throw new NotFoundException();
        }

        GameStatus gameStatus = optionalGame.get();

        // coger nombre de la scena del objeto que devuelve la base de datos y los stats
        Story story = getStoryFromResources(storyId);
        String currentSceneId = gameStatus.getCurrentScene();

        // buscar en el json de escenas el nombre de la escena
        Scene currentScene = story.getSceneById(currentSceneId);

        // si la escena es la ultima marcamos el game status como finalizado
        if(currentScene.isEnd()){

            gameStatus.setFinished(true);
            gameStatus.setDate(new Date());
            gameStatusRepository.save(gameStatus);

            return new GameStatusDto(
                    gameStatus.getStatistics().toDto(),
                    "",
                    Collections.emptyList(),
                    true
            );
        }

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
                nextScene.getAnswersAsDto(),
                false
        );
    }

    public GameHistoryDto getFinishedGames(String userId) throws FileNotFoundException, ParseException {
        // cogemos todos los juegos del usuario
        List<GameStatus> games = gameStatusRepository.findByUserId(userId);

        // buscamos los juegos ya finalizados y creamos un mapa de storyId:Story para acceder al json de manera sencilla
        List<GameStatus> finishedGames = new ArrayList<>();
        Map<String, Story> storiesById = new HashMap<>();
        for (GameStatus game: games) {
            if(game.isFinished()){
                finishedGames.add(game);
                // add el story al map si no existe
                if(!storiesById.containsKey(game.getStoryId())){
                    storiesById.put(game.getStoryId(), getStoryFromResources(game.getStoryId()));
                }
            }
        }

        List<FinishedGameDto> finishedGameDtos = new ArrayList<>();
        for (GameStatus game: finishedGames) {

            if(!storiesById.containsKey(game.getStoryId())){
                System.out.println("La story " + game.getStoryId() + " no se ha encontrado en el mapa de historias. Se ignorara");
                continue;
            }

            Story story = storiesById.get(game.getStoryId());
            finishedGameDtos.add(createFinishedGameDto(game, story));
        }

        return new GameHistoryDto(finishedGameDtos);
    }

    private FinishedGameDto createFinishedGameDto(GameStatus game, Story story) {

        List<DecisionDto> decisionDtos = new ArrayList<>();
        for (Decision decision: game.getDecisions()) {

            // creamos un optional de scene por que puede que sea nulo
            Optional<Scene> sceneOptional = Optional.empty();

            // getSceneById puede lanzar un SceneNotFoundException y en ese caso lo que queremos es ignorar el resultado
            try{
                sceneOptional = Optional.of(story.getSceneById(decision.sceneId));
            }catch (SceneNotFoundException e){
                System.out.println("Scene with id " + decision.sceneId + " not found in " + story.getId());
            }

            // si el Optional esta vacio significa que la escena no ha sido encontrada asique continuamos
            if(sceneOptional.isEmpty())
            {
                continue;
            }

            Scene scene = sceneOptional.get();
            Answer answer = scene.getAnswers().get(decision.getAnswer());
            decisionDtos.add(new DecisionDto(
                    scene.getText(),
                    answer.getText(),
                    decision.statsFrom.toDto(),
                    decision.statsTo.toDto()
            ));
        }

        return new FinishedGameDto(
                game.getStoryId(),
                story.getTitle(),
                story.getDescription(),
                game.getDate(),
                game.getEndDate(),
                decisionDtos);
    }

    private Story getStoryFromResources(String storyId) throws FileNotFoundException, ParseException {
        // carga la historia de la carpeta resources
        File file = ResourceUtils.getFile("classpath:stories/" + storyId);

        // transforma el archivo json en un objeto de Java (generalmente es un LinkedHashMap
        Object json = new JSONParser(new FileInputStream(file)).parse();

        // transforma el resultado de la operacion anterior en nuestra clase Java: Story
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(json, Story.class);
    }
}

// https://stackoverflow.com/questions/58703834/how-to-read-json-file-from-resources-in-spring-boot
// https://dzone.com/articles/how-can-we-read-a-json-file-in-java