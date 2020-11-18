package co.edu.unab.cuadros.juan.kitchenrecipeapp.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.edu.unab.cuadros.juan.kitchenrecipeapp.R;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.models.User;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.repositorio.UserRepository;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.service.CallBackRecipeApp;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonLogin;
    Button buttonRegister;
    TextView textViewLogin;
    UserRepository userRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editText_email);
        editTextPassword = findViewById(R.id.editText_password);
        buttonLogin = findViewById(R.id.button_login);
        buttonRegister = findViewById(R.id.button_register);
        textViewLogin = findViewById(R.id.textView_login);
        userRepository = new UserRepository(LoginActivity.this);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                userRepository.validate(email, password, new CallBackRecipeApp<User>() {
                    @Override
                    public void correct(User respuest) {
                        String idUser = respuest.getId();
                        if(respuest!=null){
                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            i.putExtra("bandera", 1);
                            i.putExtra("idUser",idUser);
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this,"Incorrect Data",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void error(Exception e) {
                        Toast.makeText(LoginActivity.this,"Incorrect Data",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                i.putExtra("bandera", 2);
                startActivity(i);
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });


    }
}