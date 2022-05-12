package com.example.springwebfluxavro.web.route;


import com.example.springwebfluxavro.web.handler.MonitoringHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class MonitoringRoute {
    
    @Bean
   public RouterFunction<ServerResponse> monitoringRoutes( MonitoringHandler handler ) {
       return RouterFunctions.route(GET("/monitoring/{id}"), handler::stream);
    }
}
