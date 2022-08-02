package com.hanghaecloneproject.chat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//    private final StompHandler stompHandler; //JWT 인증..  일단 써둠

    //상속메서드 2) registerStompEndpoints
    //: 메시지의 도착지점(endpoint)을 URL로 등록해주는 메서드
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat") //Controller의 @Messagemapping으로 할당하여, SimpleMessagingTemplate를 통해 약속된 경로나 유저게 메세지 전달
                .setAllowedOriginPatterns("*") //프론트 배포주소
                .withSockJS() //오류가 발생했을 때 발생 이전 상태로 되돌리거나 대체해주는 fallback 기능을 하는 sockJS를 할당
                .setHeartbeatTime(25000); //STOMP에서 TCP 연결이 잘 되어있는지 체킹하는 것. HTTP header를 통해 연결 상태를 주기적으로 확인. 권장타임 25초
    }


    //상속메서드 1) configureMessageBroker
    //: 유저가 메시지를 전송하거나 받을 수 있도록 중간에서 URL prefix(접두어)를 인식하여 올바르게 전송/전달(publish/subscribe)를 중계해주는 중개자(Broker) 역할
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue");  // 구독용(sub) -URL 설정해주기.
        registry.setApplicationDestinationPrefixes("/app");  // prefix 다른걸로 바꿔도되낭?. 발행용(pub) @MassegeMapping으로 처리

//        registry.enableSimpleBroker("/chatroom","/user");
//        registry.setUserDestinationPrefix("/user"); //사용자 접두사를 애플리케이션 접두사로도 정의하면 컨트롤러에서 구독을 매핑할 수 있습니다
    }


    // jwt 토큰 검증을 위해 생성한 stompHandler를 인터셉터로 지정해준다.
    // 메세지를 받았을때 최초에 stompHandler 가 인터셉트 하도록 설정
    // StompHandler 가 Websocket 앞단에서 token 및 메시지 TYPE 등을 체크할 수 있도록 다음과 같이 인터셉터로 설정한다

//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(stompHandler);
//    }



}
