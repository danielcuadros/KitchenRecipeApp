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

import co.edu.unab.cuadros.juan.kitchenrecipeapp.models.Comentario;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.models.Recipe;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.service.CallBackRecipeApp;

public class CommentRepository {

    private  final String COLECCION_COMMENT = "comment";
    private final FirebaseFirestore firestore;


    public CommentRepository(Context miContexto) {
        firestore = FirebaseFirestore.getInstance();
    }


    public void obtenerTodosFS(final CallBackRecipeApp<List<Comentario>> respuesta) {
        final List<Comentario> lista = new ArrayList<>();
        firestore.collection(COLECCION_COMMENT).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot documento : task.getResult()) {
                        final Comentario miComentario = documento.toObject(Comentario.class);
                        miComentario.setId(documento.getId());
                        lista.add(miComentario);
                    }

                    respuesta.correct(lista);

                } else {

                    respuesta.error(task.getException());
                }
            }
        });

    }

    public void agregarFS(Comentario miComentario, final CallBackRecipeApp<Boolean> respuesta){
        firestore.collection(COLECCION_COMMENT).add(miComentario).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()){
                    respuesta.correct(true);
                }else {
                    respuesta.error(task.getException());
                }
            }
        });
    }

    public void eliminarFS(Comentario miComentario, final CallBackRecipeApp<Boolean> respuesta){
        firestore.collection(COLECCION_COMMENT).document(miComentario.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    respuesta.correct(true);
                }else{
                    respuesta.error(task.getException());
                }
            }
        });
    }

    public void actualizarFS(Comentario miComment, final CallBackRecipeApp<Boolean> respuesta){
        firestore.collection(COLECCION_COMMENT).document(miComment.getId()).set(miComment).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    respuesta.correct(true);
                }
                else{
                    respuesta.error(task.getException());
                }
            }
        });
    }

    public void obtenerById(String id, final CallBackRecipeApp<Comentario> respuesta){
        firestore.collection(COLECCION_COMMENT).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if (task.getResult()!=null) {
                        Comentario miComentario = task.getResult().toObject(Comentario.class);
                        miComentario.setId(task.getResult().getId());
                        respuesta.correct(miComentario);
                    }else{
                        respuesta.correct(null);
                    }
                }else{
                    respuesta.error(task.getException());
                }
            }
        });
    }
}
