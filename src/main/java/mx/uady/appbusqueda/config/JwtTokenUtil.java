package mx.uady.appbusqueda.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.ZonedDateTime;
import mx.uady.appbusqueda.model.Usuario;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * (long)50000;

	//retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	//retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
    //for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().parseClaimsJwt(token).getBody();
	}

	//check if the token has expired
	private Boolean isTokenExpired(DecodedToken token) {
		try {
			Date date = new Date(Long.parseLong(token.getExpiracion()));
			return date.before(new Date());
		}
		catch(Exception e){			
			e.printStackTrace();
			return true;
		}
	}

	//generate token for user
	public String generateToken(Usuario usuario) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("expiracion", ZonedDateTime.now().toInstant().toEpochMilli() + JWT_TOKEN_VALIDITY);
		return doGenerateToken(claims, usuario.getUsuario(), usuario.getSecret());
	}
	

	//while creating the token -
	//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
	//2. Sign the JWT using the HS512 algorithm and secret key.
	//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	//   compaction of the JWT to a URL-safe string 
	private String doGenerateToken(Map<String, Object> claims, String subject, String secret) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(ZonedDateTime.now().toInstant().toEpochMilli()))
				.setExpiration(new Date(ZonedDateTime.now().toInstant().toEpochMilli() + JWT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	} 



	//validate token
	public Boolean validateToken(DecodedToken token, Usuario usuario) {
		final String username = usuario.getUsuario();
		return (username.equals(usuario.getUsuario()) && !isTokenExpired(token));
	}
}