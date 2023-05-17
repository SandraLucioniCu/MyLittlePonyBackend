package com.sandra.game.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@NoArgsConstructor
@Document(collection = "history")
@Getter
@Setter
public class HistoryText{

        @Id
        private String id;

        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private int index;

        private String question;
        private String answerOne;
        private String answerTwo;
        private String answerThree;
}
