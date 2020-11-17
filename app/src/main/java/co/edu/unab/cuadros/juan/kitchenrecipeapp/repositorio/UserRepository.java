package co.edu.unab.cuadros.juan.kitchenrecipeapp.repositorio;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import co.edu.unab.cuadros.juan.kitchenrecipeapp.models.User;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.service.CallBackRecipeApp;

public class UserRepository {
    private final FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    final String COLLECTION_USER = "users";

    public UserRepository(Context context){
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public void validate(String correo, String pass, final CallBackRecipeApp<User> result){
        mAuth.signInWithEmailAndPassword(correo,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    getById(user.getUid(), new CallBackRecipeApp<User>() {
                        @Override
                        public void correct(User respuest) {
                            result.correct(respuest);

                        }

                        @Override
                        public void error(Exception e) {
                            result.error(e);
                        }
                    });
                }else{
                    result.error(task.getException());
                }
            }
        });

    }

    public void getById(String id,final CallBackRecipeApp<User> respuest ){
        firestore.collection(COLLECTION_USER).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    User myUser = task.getResult().toObject(User.class);
                    myUser.setId(task.getResult().getId());
                    respuest.correct(myUser);
                }else{
                    respuest.error(task.getException());
                }
            }
        });
    }

    public void create(final User myUser, String pass, final CallBackRecipeApp<Boolean> respuest){
        mAuth.createUserWithEmailAndPassword(myUser.getEmail().toString(),pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    myUser.setId(user.getUid());
                    firestore.collection(COLLECTION_USER).document(user.getUid()).set(myUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                respuest.correct(true);
                            }else{
                                respuest.error(task.getException());
                            }
                        }
                    });
                }else{
                    respuest.error(task.getException());
                }
            }
        });
    }
}
