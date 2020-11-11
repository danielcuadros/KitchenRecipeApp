package co.edu.unab.cuadros.juan.kitchenrecipeapp.models;

public class User {
    String id;
    String name;
    String email;
    String urlImage;

    public User() {
    }

    public User(String name, String email, String urlImage) {
        this.name = name;
        this.email = email;
        this.urlImage = urlImage;
    }

    public User(String id, String name, String email, String urlImage) {
        this.id = "";
        this.name = name;
        this.email = email;
        this.urlImage = urlImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
