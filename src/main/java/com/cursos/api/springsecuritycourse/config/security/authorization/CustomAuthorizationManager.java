package com.cursos.api.springsecuritycourse.config.security.authorization;

import com.cursos.api.springsecuritycourse.exception.ObjectNotFoundException;
import com.cursos.api.springsecuritycourse.persistence.entity.Security.GrantedPermission;
import com.cursos.api.springsecuritycourse.persistence.entity.Security.Operation;
import com.cursos.api.springsecuritycourse.persistence.entity.Security.User;
import com.cursos.api.springsecuritycourse.persistence.repository.OperationRepository;
import com.cursos.api.springsecuritycourse.persistence.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private OperationRepository operationRep;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication,
                                       RequestAuthorizationContext object) {
        HttpServletRequest request= object.getRequest();
        String url=extractURL(request); //la url queda /auth/authenticate
        String httpMethod=request.getMethod(); //el metodo es POST, se extrae de la peticion enviada en POSTMAN o en el cliente.
        System.out.println(url);
        System.out.println(httpMethod);
        boolean isPublic=isPublic(url, httpMethod); //para verificar que el metodo es publico se llama al metodo isPublic enviandole esa url y ese metodo guardados arriba.
        if (isPublic){  //si el endpoint es publico, deja pasar
            return new AuthorizationDecision(true);
        }
        //si no es publico, entonces se procede a verificar sus permisos
        boolean isGranted=isGranted(url, httpMethod, authentication.get()); //se llama al metodo inGranted creado abajo enviandole la url, el metodo y el objeto authentication
        return new AuthorizationDecision(isGranted); //se retorna la decision de que si puede o no acceder a la peticion.

    }

    private boolean isGranted(String url, String httpMethod, Authentication authentication) {
        if (authentication==null || !(authentication instanceof
                JwtAuthenticationToken)){ //si el authentication es nulo o no es una instancia de UsernamePasswordAuthenticationToken quiere decir que el usuario no se ha autenticado
            throw new AuthenticationCredentialsNotFoundException("Usuario no logeado");
        }

        List<Operation> operations=obtainedOperations(authentication); //se extraen las operaciones del rol del usuario
        boolean isGranted=operations.stream().anyMatch
                (getOperationPredicate(url, httpMethod)); //finalmente se recorren esas operaciones del usuario para verificar si alguna coincide con la url y con el método enviados, en tal caso devuelve true, sino, quiere decir que el usuario no esta autorizado para realizar dicha accion.
        System.out.println("IS GRANTED : " + isGranted);
        return isGranted;
    }

    private static Predicate<Operation> getOperationPredicate(String url, String httpMethod) {
        return endpoint -> { //recorre esos registros publicos encontrados para saber cual de todos coincidirá con la url enviada y el método enviado.
            String basePath = endpoint.getModule().getBase_path(); //extrae el valor del campo basePath dentro del atributo module de la clase Operation
            System.out.println("basePath dentro del método isGranted " + basePath);
            Pattern pattern = Pattern.compile(basePath.concat(endpoint.getPath())); //concatena de todos los registros de la lista ese basePath con el valor del campo Path de la clase Operation para comparar la url completa
            Matcher matcher = pattern.matcher(url);//verifica si la url creada en la linea anterior coincide con la url enviada por parametro
            return matcher.matches() && endpoint.getHttpMethod().equals(httpMethod); //si ambas url coinciden y el metodo Http enviado coincide con el metodo guardado en el campo HttpMethod de la clase Operation, entonces devuelve True
            //si la url es igual a la enviada y el metodo http es igual al enviado
        };
    }

    private List<Operation> obtainedOperations(Authentication authentication) {
        JwtAuthenticationToken authToken=
                (JwtAuthenticationToken) authentication; //del objeto authentication lo pasea
        Jwt jwt=authToken.getToken();
        String username=jwt.getSubject(); //luego se le extrae el principal que es el Username del usuario
        User user=userRepository.findByUsername(username).orElseThrow(
                ()->new ObjectNotFoundException("Usuario no encontrado"));  //a partir de ese username o principal se busca al usuario en base de datos
        List<Operation> operations= user.getRole().getOperations(); //se retorna las operaciones de su rol o sus permisos. tales como READ_ALL_PRODUCTS, ETC, ETC.
        List<String> scopes=extractScopes(jwt);

        if (!scopes.contains("ALL")){
            operations=operations.stream().filter(op->
                    scopes.contains(op.getName()))
                    .collect(Collectors.toList());

        }

        return operations;

    }

    private List<String> extractScopes(Jwt jwt) {
        List<String> scopes=new ArrayList<>();
        try{
            scopes=(List<String>)jwt.getClaims().get("scope");
        }catch(Exception e){
            System.out.println("Hubo un problema al extraer los scopes del cliente");
        }

        return scopes;

    }

    private boolean isPublic(String url, String httpMethod) {
        List<Operation> publicAccessEndpoints=operationRep.findByPublicAccess(); //en el repositorio de operation se crea un metodo para extraer solo los registros que tengan el campo permitAll en true, o en 1
        boolean isPublic=publicAccessEndpoints.stream().anyMatch(getOperationPredicate(url, httpMethod));
        System.out.println("EL METODO EL PUBLICO= " + isPublic);
        return isPublic;  //en este caso, es true debido a que un registro o metodo publico si coincidio con la url y el metodo Http enviado, que era /auth/authenticate y POST

    }

    private String extractURL(HttpServletRequest request){
        String contextPath=request.getContextPath();  // de la peticion extrae el contextPath que es el definido en el application.properties: /api/v1
        System.out.println("contextPath= " + contextPath);
        String url=request.getRequestURI();  //extrae la uri, es decir, lo que sigue despues del host, en este caso por ejemplo: /api/v1/auth/authenticate
        System.out.println("url antes de quitarle el contextPath= " + url);
        url=url.replace(contextPath, ""); //le quita el contextPath a la url para despues comparar con los datos guardados en la BD. Le quita el /api/v1
        System.out.println("url despues de quitarle el contextPath= " + url);
        return url; //la url queda: /auth/authenticate
    }


}
