package uz.businesshub.product.security;

import org.jspecify.annotations.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import uz.businesshub.product.exception.MissingTenantException;

import java.util.UUID;

public class CurrentCompanyIdResolver implements HandlerMethodArgumentResolver {

    public static final String CLAIM = "company_id";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentCompanyId.class)
                && UUID.class.equals(parameter.getParameterType());
    }

    @Override
    public UUID resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mav,
                                @NonNull NativeWebRequest request, WebDataBinderFactory binderFactory) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof JwtAuthenticationToken jwtAuth)) {
            throw new MissingTenantException();
        }
        String value = jwtAuth.getToken().getClaimAsString(CLAIM);
        if (value == null || value.isBlank()) {
            throw new MissingTenantException();
        }
        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new MissingTenantException();
        }
    }
}