package miaad.sgrh.servicemessagerie.chat;

import lombok.RequiredArgsConstructor;
import miaad.sgrh.servicemessagerie.attachement.AttachementService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final AttachementService attachementService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        ChatMessage savedMsg = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(), "/queue/messages",
                new ChatNotification(
                        savedMsg.getId(),
                        savedMsg.getSenderId(),
                        savedMsg.getRecipientId(),
                        savedMsg.getContent()
                )
        );
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(@PathVariable String senderId,
                                                              @PathVariable String recipientId) {
        return ResponseEntity
                .ok(chatMessageService.findChatMessages(senderId, recipientId));
    }

    @DeleteMapping("/deleteMessage/{id}")
    public ResponseEntity<String> deleteChatMessage(@PathVariable("id") String id){
        Optional<ChatMessage> chatMessageOptional = chatMessageService.findChatMessageById(id);
        if (chatMessageOptional.isPresent()) {
            ChatMessage chatMessage = chatMessageOptional.get();
            String attachementId = chatMessage.getAttachementId();
            if (attachementId != null) {
                attachementService.deleteAttachement(attachementId);
            }
            chatMessageService.deleteChatMessage(id);
            return ResponseEntity.ok("Message supprimé avec succès");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
