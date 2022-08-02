package com.hanghaecloneproject.chat.controller;

import com.hanghaecloneproject.chat.service.ChatRoomJoinService;
import com.hanghaecloneproject.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatRoomController {

//    private final UserService userService;
    private final ChatRoomJoinService chatRoomJoinService;
    private final ChatRoomService chatRoomService;

    //바로 채팅버튼 눌렀을 때 -> roomdetail.html
    @PostMapping("/chat")
    public String newChat(@RequestParam("receiver") String receiverEmail, @RequestParam("sender") String senderEmail) {//, RedirectAttributes redirectAttributes
        Long chatRoomId = chatRoomJoinService.newRoom(receiverEmail, senderEmail);
//        redirectAttributes.addAttribute("email",user2);
        return "redirect:/personalChat/?chatRoomId=" + chatRoomId + "&email=" + senderEmail;
//        return "redirect:/personalChat/" + chatRoomId;
    }
}
