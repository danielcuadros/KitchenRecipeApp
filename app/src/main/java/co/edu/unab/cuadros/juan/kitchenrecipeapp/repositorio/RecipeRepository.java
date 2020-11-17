package co.edu.unab.cuadros.juan.kitchenrecipeapp.repositorio;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import co.edu.unab.cuadros.juan.kitchenrecipeapp.models.Recipe;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.service.CallBackRecipeApp;

public class RecipeRepository {

    private FirebaseFirestore firestore;

    final String COLECCION_RECIPE = "recipes";


    public RecipeRepository(Context miContext) {
        firestore = FirebaseFirestore.getInstance();
    }

    public void obtenerTodosFS(final CallBackRecipeApp<List<Recipe>> respuest){
        final List<Recipe> list = new ArrayList<>();
        firestore.collection(COLECCION_RECIPE).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    for(QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())){
                        final Recipe myProduct = document.toObject(Recipe.class);
                        myProduct.setId(document.getId());
                        list.add(myProduct);
                    }
                    respuest.correct(list);
                }else{
                    respuest.error(task.getException());
                }
            }
        });

    }

    public void agregarFS(Recipe myRecipe, final CallBackRecipeApp<Boolean> respuest){
        firestore.collection(COLECCION_RECIPE).add(myRecipe).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    respuest.correct(true);
                }else{
                    respuest.error(task.getException());
                }
            }
        });
    }

    public void eliminarFS(Recipe miRecipe, final CallBackRecipeApp<Boolean> respuesta) {
        firestore.collection(COLECCION_RECIPE).document(miRecipe.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    respuesta.correct(true);
                } else {
                    respuesta.error(task.getException());
                }
            }
        });
    }

    public void actualizarFS(Recipe miRecipe, final CallBackRecipeApp<Boolean> respuesta) {
        firestore.collection(COLECCION_RECIPE).document(miRecipe.getId()).set(miRecipe).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    respuesta.correct(true);
                } else {
                    respuesta.error(task.getException());
                }
            }
        });
    }

    public void obtenerById(String id, final CallBackRecipeApp<Recipe> respuesta) {
        firestore.collection(COLECCION_RECIPE).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() != null) {
                        Recipe miRecipe = task.getResult().toObject(Recipe.class);
                        miRecipe.setId(task.getResult().getId());
                        respuesta.correct(miRecipe);
                    } else {
                        respuesta.correct(null);
                    }
                } else {
                    respuesta.error(task.getException());
                }
            }
        });

    }
}
