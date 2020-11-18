package co.edu.unab.cuadros.juan.kitchenrecipeapp.activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import co.edu.unab.cuadros.juan.kitchenrecipeapp.R;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.adapter.RecipeAdapter;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.models.Recipe;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.repositorio.RecipeRepository;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.service.CallBackRecipeApp;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvRecipe;
    Button buttonAgregar;
    Button buttonSalir;
    List<Recipe> myRecipes;
    RecipeAdapter mAdapter;
    RecipeRepository recipeRepository;
    private static final int CODE_ADD = 100;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_ADD){
            actualizarListado();
            if(resultCode == RESULT_OK){
                Toast.makeText(MainActivity.this, "All Good", Toast.LENGTH_SHORT).show();
                actualizarListado();
            }else{
                Toast.makeText(MainActivity.this, "All Bad", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int entrada = getIntent().getIntExtra("bandera",0);
        final String users = getIntent().getStringExtra("user");
        
        recipeRepository = new RecipeRepository(MainActivity.this);
        
        rvRecipe = (RecyclerView)findViewById(R.id.rv_recipe);
        buttonAgregar = findViewById(R.id.button_agregar);
        buttonSalir = findViewById(R.id.button_salir);
        myRecipes = new ArrayList<>();

        configurarAdapter();
        

        RecyclerView.LayoutManager managerG = new GridLayoutManager(MainActivity.this,2);

        if( entrada == 1){
            buttonAgregar.setVisibility(View.VISIBLE);
            buttonAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this,AddRecipeActivity.class);
                    i.putExtra("users",users);
                    startActivityForResult(i,CODE_ADD);
                }
            });

            buttonSalir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.signOut();

                    Intent i = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }

        if(entrada == 2){
            buttonSalir.setVisibility(View.INVISIBLE);

        }




        rvRecipe.setLayoutManager(managerG);
        rvRecipe.setItemAnimator(new DefaultItemAnimator());
        rvRecipe.setHasFixedSize(true);

    }

    public void configurarAdapter(){
        mAdapter = new RecipeAdapter(myRecipes);
        mAdapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Recipe myRecipe, int position) {
                final int entrada = getIntent().getIntExtra("bandera",0);
                if(entrada == 1){
                    Intent i = new Intent(MainActivity.this, DetailActivity.class);
                    i.putExtra("idRecipe",myRecipe.getId());
                    startActivity(i);
                }else if(entrada == 2){
                    Intent i = new Intent(MainActivity.this, DetailNoLoginActivity.class);
                    i.putExtra("nombre", myRecipe.getName());
                    i.putExtra("descripcion", myRecipe.getDescription());
                    i.putExtra("url", myRecipe.getUrlImage());
                    i.putExtra("usar", myRecipe.getUser());
                    startActivity(i);
                }
            }
        });
        rvRecipe.setAdapter(mAdapter);
    }

    public void fakedata(){
        Recipe recipe1 = new Recipe();
        recipe1.setName("carne");
        recipe1.setUser("jcuadros398@unab.edu.co");
        recipe1.setUrlImage("https://www.archies.co/media/di-carne-min.jpg");
        recipe1.setDescription("no hay");

        recipeRepository.agregarFS(recipe1, new CallBackRecipeApp<Boolean>() {
            @Override
            public void correct(Boolean respuest) {

                Recipe recipe2 = new Recipe();
                recipe2.setName("pollo");
                recipe2.setDescription("no hay");
                recipe2.setUser("jcuadros398@unab.edu.co");
                recipe2.setUrlImage("https://www.archies.co/media/di-carne-min.jpg");

                recipeRepository.agregarFS(recipe2, new CallBackRecipeApp<Boolean>() {
                    @Override
                    public void correct(Boolean respuest) {
                        
                        actualizarListado();
                    }

                    @Override
                    public void error(Exception e) {

                    }
                });
            }

            @Override
            public void error(Exception e) {

            }
        });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        actualizarListado();
    }

    private void actualizarListado(){
        recipeRepository.obtenerTodosFS(new CallBackRecipeApp<List<Recipe>>() {
            @Override
            public void correct(List<Recipe> listado) {
                myRecipes = listado;
                if(myRecipes.isEmpty()){
                    fakedata();
                }
                mAdapter.setRecipes(myRecipes);
            }
            @Override
            public void error(Exception e) {
                Log.d("Este",e.getMessage());
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    

}
