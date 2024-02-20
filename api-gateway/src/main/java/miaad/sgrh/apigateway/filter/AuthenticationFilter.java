package miaad.sgrh.apigateway.filter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import miaad.sgrh.apigateway.exception.UnauthorizedException;
import miaad.sgrh.apigateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    //    @Autowired
//    private RestTemplate template;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new UnauthorizedException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
//                    //REST call to AUTH service
//                    template.getForObject("http://IDENTITY-SERVICE//validate?token" + authHeader, String.class);
//                    jwtUtil.validateToken(authHeader);
                    // Extract roles from the token
                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(jwtUtil.getSignKey())
                            .deserializeJsonWith(new JacksonDeserializer(Map.of("roles", String.class)))
                            .build()
                            .parseClaimsJws(authHeader)
                            .getBody();

                    Object rolesObject = claims.get("roles");
                    // Check authorities and update route accordingly
                    if (rolesObject instanceof String) {
                        String role = (String) rolesObject;
                        URI originalUri = exchange.getRequest().getURI();
                        String path = originalUri.getPath();
                        System.out.println("----------------------------------------");

                        if ("Admin".equals(role)) {

                            // If Admin, allow access to /api/stagiaire/**, /api/employee/**, and /api/admin/**
//                            api/administration
                            if (path.startsWith("/api") || path.startsWith("/manage") ) {
                                System.out.println(path);
                                exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, originalUri);
                            } else {
                                throw new UnauthorizedException("Unauthorized 1 access to application");
                            }
                        } else if ("Employee".equals(role)) {
                            if (path.startsWith("/api/stagiaire")) {
                                exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, originalUri);
                            } else {
                                throw new UnauthorizedException("Unauthorized 2 access to application");
                            }
                        } else if ("Stagiaire".equals(role)) {
                            if (path.startsWith("/api/stage")) {
                                exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, originalUri);
                            } else {
                                throw new UnauthorizedException("Unauthorized 3 access to application");
                            }
                        }
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    throw new UnauthorizedException("Unauthorized 4 access to application");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
