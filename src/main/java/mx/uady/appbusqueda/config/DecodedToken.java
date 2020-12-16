package mx.uady.appbusqueda.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

public class DecodedToken {

  private String sub;
  private String exp;
  private String iat;
  private String expiracion;

  public static DecodedToken getDecoded(String encodedToken) throws UnsupportedEncodingException {
      String[] pieces = encodedToken.split("\\.");
      String b64payload = pieces[1];
      String jsonString = new String(Base64.decodeBase64(b64payload), "UTF-8");
      System.out.println("PAYLOAD:"+ jsonString);

      return new Gson().fromJson(jsonString, DecodedToken.class);
  }

  public String toString() {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      return gson.toJson(this);
  }

  public String getSub(){
    return this.sub;
  }

  public String getExp(){
    return this.exp;
  }

  public String getIat(){
    return this.iat;
  }

  public String getExpiracion(){
    return this.expiracion;
  }

}