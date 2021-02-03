package mx.uady.appbusqueda.model.response;
import java.util.List;

public class ResponseMessage {
  private String message;
  //private List<String> messages;
  public ResponseMessage(String message) {
    //this.messages.add(message);
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }

  /*public void setMessage(String message) {
    this.messages.add(message);
  }*/


}
