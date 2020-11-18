package co.edu.unab.cuadros.juan.kitchenrecipeapp.models;

public class Comentario {

    String id;
    String mensaje;

    public Comentario(){

    }
    public Comentario(String id, String mensaje) {
        this.id = id;
        this.mensaje = mensaje;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
