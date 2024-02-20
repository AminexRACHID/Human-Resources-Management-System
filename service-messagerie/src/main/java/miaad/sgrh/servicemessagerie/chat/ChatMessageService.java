package miaad.sgrh.servicemessagerie.chat;

import lombok.RequiredArgsConstructor;
import miaad.sgrh.servicemessagerie.attachement.AttachementService;
import miaad.sgrh.servicemessagerie.chatroom.ChatRoomService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        var chatId = chatRoomService
                .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
                .orElseThrow();
        chatMessage.setChatId(chatId);
        repository.save(chatMessage);
        return chatMessage;
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        var chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
        return chatId.map(repository::findByChatId).orElse(new ArrayList<>());
    }

    public Optional<ChatMessage> findChatMessageById(String messageId) {
        return repository.findById(messageId);
    }

    public void deleteChatMessage(String chatMessageId){
        repository.deleteById(chatMessageId);
    }
}

