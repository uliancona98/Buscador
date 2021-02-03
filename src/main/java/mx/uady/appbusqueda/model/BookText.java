package mx.uady.appbusqueda.model;

import java.util.List;

public class BookText {
    private String id;
    private List<String> text;

    public BookText(String id, List<String> text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getText() {
        return this.text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }
}