package mx.uady.appbusqueda.model;
import java.util.List;

public class ResponseMessage {
  private String message;
  private List<Libro> libros;

  public ResponseMessage(String message) {
    this.message = message;
  }
  public ResponseMessage(List<Libro> libros) {
    this.libros = libros;
  }
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setLibros(List<Libro> libros) {
    this.libros = libros;
  }

  public List<Libro> getLibros() {
    return this.libros;
  }


}
