package miaad.sgrh.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/api/account/create",
            "/api/auth/login",
            "/api/auth/validate",
            "/api/stage/offers",
//            "/api/employees",  // create admin or Employee User
            "/manage/user",
            "/api/absences/allDemandes",
            "/api/account/passwordRecovre",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}