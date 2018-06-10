package cn.imiaomi.admin.api.util;

import cn.imiaomi.admin.api.dto.AuthTokenDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

public class AuthWebTokenUtil {

    private SignatureAlgorithm signatureAlgorithm;

    private Key secretKey;

    public AuthWebTokenUtil() {
        // 这里不是真正安全的实践
        // 为了简单，我们存储一个静态key在这里，
        // 在真正微服务环境，这个key将会被保留在配置服务器
        signatureAlgorithm = SignatureAlgorithm.HS512;
        String encodedKey = "L7A/6zARSkK1j7Vd5SDD9pSSqZlqF7mAhiOgRbgv9Smce6tf4cJnvKOjtKPxNNnWQj+2lQEScm3XIUjhW+YVZg==";
        secretKey = deserializeKey(encodedKey);
    }

    public String createJsonWebToken(AuthTokenDTO adminTokenDTO) {
        String token = Jwts.builder().setSubject(adminTokenDTO.getId()).claim("adminname", adminTokenDTO.getUsername())
                .setExpiration(adminTokenDTO.getExpirationDate()).signWith(getSignatureAlgorithm(), getSecretKey())
                .compact();
        return token;
    }

    private Key deserializeKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        Key key = new SecretKeySpec(decodedKey, getSignatureAlgorithm().getJcaName());
        return key;
    }

    private Key getSecretKey() {
        return secretKey;
    }

    public SignatureAlgorithm getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public AuthTokenDTO parseAndValidate(String token) {
        AuthTokenDTO authTokenDetailsDTO = null;
        try {
            Claims claims =	Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody();
            String id = claims.getSubject();
            String adminname = (String) claims.get("adminname");
            Date expirationDate = claims.getExpiration();
            authTokenDetailsDTO = new AuthTokenDTO();
            authTokenDetailsDTO.setId(id);
            authTokenDetailsDTO.setUsername(adminname);
            authTokenDetailsDTO.setExpirationDate(expirationDate);
        } catch (JwtException ex) {
            System.out.println(ex);
        }
        return authTokenDetailsDTO;
    }

    @SuppressWarnings("all")
    private String serializeKey(Key key) {
        String encodedKey =	Base64.getEncoder().encodeToString(key.getEncoded());
        return encodedKey;
    }
}
