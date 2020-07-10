package com.sayapatri.parasi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ParasiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParasiApplication.class, args);
        
    }
//    @Configuration
//    public class WebConfig implements WebMvcConfigurer {
//        @Override
//        public void addResourceHandlers(ResourceHandlerRegistry registry) {
//            registry.addResourceHandler("/**")
//                    .addResourceLocations("classpath:/static/","classpath:/images/")
//                    .setCachePeriod(0);
//        }
//    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
