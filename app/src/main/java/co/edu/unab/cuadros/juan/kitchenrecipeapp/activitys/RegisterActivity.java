package co.edu.unab.cuadros.juan.kitchenrecipeapp.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import co.edu.unab.cuadros.juan.kitchenrecipeapp.R;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.models.User;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.repositorio.UserRepository;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.service.CallBackRecipeApp;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextNombreDeUsuario;
    EditText editTextCorreo;
    EditText editTextPassword;
    Button button;
    UserRepository userRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        editTextNombreDeUsuario = findViewById(R.id.editText_nombredeusuario);
        editTextCorreo = findViewById(R.id.editText_correoelectronico);
        editTextPassword = findViewById(R.id.editText_contraseña);
        button = findViewById(R.id.button_registrar);
        userRepository = new UserRepository(RegisterActivity.this);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User myUser = new User();
                myUser.setEmail(editTextCorreo.getText().toString());
                myUser.setName(editTextNombreDeUsuario.getText().toString());
                myUser.setUrlImage("no hay");

                userRepository.create(myUser, editTextPassword.getText().toString(), new CallBackRecipeApp<Boolean>() {
                    @Override
                    public void correct(Boolean respuest) {
                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void error(Exception e) {
                        Log.e("Error Usuario", e.getMessage());
                    }
                });
            }
        });

    }
}