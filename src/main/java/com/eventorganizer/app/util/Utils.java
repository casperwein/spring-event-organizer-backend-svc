package com.eventorganizer.app.util;

import com.eventorganizer.app.payload.CustomeResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class Utils {
    @Bean
    public UUID generateUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid;
    }

    @Bean
    public CustomeResponse customeResponses(){
        CustomeResponse customeResponse = new CustomeResponse();
        Utils utils = new Utils();

        customeResponse.setTask_id(String.valueOf(utils.generateUUID()));
        customeResponse.setError(false);
        customeResponse.setMessage("Success");
        customeResponse.setStatusCode(HttpServletResponse.SC_OK);

        return customeResponse;
    }
}
