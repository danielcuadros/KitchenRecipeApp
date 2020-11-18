package co.edu.unab.cuadros.juan.kitchenrecipeapp.models;

public class Recipe {
    String id;
    String name;
    String user;
    String urlImage;
    String description;

    public Recipe() {
    }

    public Recipe(String name, String user, String urlImage, String description) {
        this.id = "";
        this.name = name;
        this.user = user;
        this.urlImage = urlImage;
        this.description = description;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
