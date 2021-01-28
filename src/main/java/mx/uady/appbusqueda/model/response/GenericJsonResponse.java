package mx.uady.appbusqueda.model.response;

import java.io.Serializable;

public class GenericJsonResponse implements Serializable{

    private static final long serialVersionUID = -8453115435277943890L;
    String message;

    public GenericJsonResponse() {
    }

    public GenericJsonResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
