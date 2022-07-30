package com.hanghaecloneproject.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghaecloneproject.config.xss.HtmlCharacterEscapes;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final ObjectMapper objectMapper;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
              .allowedOriginPatterns("*")
              // 현재는 OriginPattern을 모두 열어두었지만 이후 빌드를 완료하면 다음과 같은 링크로 변경하면 됩니다.
//              .allowedOriginPatterns("http://sparta-dhh.shop.s3-website.ap-northeast-2.amazonaws.com/", "http://54.180.105.24:8080/")
              .allowedMethods("*")
              .allowedHeaders("*")
              .exposedHeaders("*")
              .allowCredentials(true)
              .maxAge(1600);
    }

    @Bean
    public MappingJackson2HttpMessageConverter jsonEscapeConverter() {
        ObjectMapper copy = objectMapper.copy();
        copy.getFactory().setCharacterEscapes(new HtmlCharacterEscapes());
        return new MappingJackson2HttpMessageConverter(copy);
    }
}
