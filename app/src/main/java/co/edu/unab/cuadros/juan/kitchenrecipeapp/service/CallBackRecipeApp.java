package co.edu.unab.cuadros.juan.kitchenrecipeapp.service;

public interface CallBackRecipeApp<T> {
    void correct(T respuest);

    void error (Exception e);
}
