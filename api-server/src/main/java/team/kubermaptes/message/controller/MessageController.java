package team.kubermaptes.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.kubermaptes.message.service.MessageService;

@RestController
@RequestMapping("/hyeonJunTest")
public class MessageController {

    @Autowired
    private MessageService messageService;


    @PostMapping("/message")
    public ResponseEntity<?> sendMessage(@RequestBody String phoneNumber){

        messageService.sendSMS(phoneNumber);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
