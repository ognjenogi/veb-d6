package raf.edu.rs.vebd6.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.apache.commons.codec.digest.DigestUtils;
import raf.edu.rs.vebd6.entities.User;
import raf.edu.rs.vebd6.repository.user.UserRepository;


import javax.inject.Inject;
import java.util.Date;

public class UserService {

    @Inject
    UserRepository userRepository;

    public String login(String username, String password)
    {
        String hashedPassword = DigestUtils.sha256Hex(password);

        User user = this.userRepository.findUser(username);
        if (user == null || !user.getHashedPassword().equals(hashedPassword)) {
            return null;
        }

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 24*60*60*1000); // One day

        Algorithm algorithm = Algorithm.HMAC256("secret");

        return JWT.create()
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withSubject(username)
                .withClaim("role", user.getRole())
                .sign(algorithm);
    }

    public boolean isAuthorized(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        String username = jwt.getSubject();

        User user = this.userRepository.findUser(username);

        if (user == null){
            return false;
        }

        return true;
    }
    public String getUsername(String token){
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        return jwt.getSubject();
    }
}
