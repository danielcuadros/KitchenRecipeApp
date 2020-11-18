package co.edu.unab.cuadros.juan.kitchenrecipeapp.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import co.edu.unab.cuadros.juan.kitchenrecipeapp.R;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.models.Recipe;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.repositorio.RecipeRepository;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.service.CallBackRecipeApp;

public class AddRecipeActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextDescripcion;
    EditText editTextImage;
    Button buttonGuardar;
    Button buttonCancel;
    RecipeRepository recipeRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        recipeRepository = new RecipeRepository(AddRecipeActivity.this);

        editTextName = findViewById(R.id.et_nombre);
        editTextDescripcion = findViewById(R.id.et_descripcion);
        editTextImage= findViewById(R.id.et_url);

        buttonGuardar = findViewById(R.id.button_save);
        buttonCancel = findViewById(R.id.button_cancela);

        final String idRecipe = getIntent().getStringExtra("idRecipe");
        final String user = getIntent().getStringExtra("users");


        if(idRecipe != null){

            recipeRepository.obtenerById(idRecipe, new CallBackRecipeApp<Recipe>() {
                @Override
                public void correct(Recipe respuest) {

                    editTextName.setText(respuest.getName());
                    editTextDescripcion.setText(respuest.getDescription());
                    editTextImage.setText(respuest.getUrlImage());
                }

                @Override
                public void error(Exception e) {

                }
            });

            buttonGuardar.setText("EDITAR");



            buttonGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setResult(RESULT_OK);
                    Recipe myRecipe = new Recipe(editTextName.getText().toString(),user, editTextImage.getText().toString(), editTextDescripcion.getText().toString());
                    myRecipe.setId(idRecipe);
                    recipeRepository.actualizarFS(myRecipe, new CallBackRecipeApp<Boolean>() {
                        @Override
                        public void correct(Boolean respuest) {
                            finish();
                        }

                        @Override
                        public void error(Exception e) {

                        }
                    });

                }
            });
        }else{
            buttonGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setResult(RESULT_OK);
                    Recipe myRecipe = new Recipe(editTextName.getText().toString(),user, editTextImage.getText().toString(), editTextDescripcion.getText().toString());
                    recipeRepository.agregarFS(myRecipe, new CallBackRecipeApp<Boolean>() {
                        @Override
                        public void correct(Boolean respuest) {
                            finish();
                        }

                        @Override
                        public void error(Exception e) {

                        }
                    });

                }
            });
        }

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });


    }
}