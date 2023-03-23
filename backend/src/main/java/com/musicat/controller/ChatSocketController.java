package com.musicat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicat.data.dto.ChatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChatSocketController {

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @MessageMapping("/chat")
  @SendTo("/topic/public")
  public ChatDto chat(ChatDto message) throws JsonProcessingException {
    String jsonMessage = objectMapper.writeValueAsString(message);
    kafkaTemplate.send("chat", jsonMessage);
//    if (true) {
//      messagingTemplate.convertAndSendToUser(
//        message.getSender(),
//        "/queue/warning",
//        "나쁜 단어가 포함되어 있어요"
//      );
//      return null;
//    }
    return message;
  }


}
