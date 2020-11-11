package co.edu.unab.cuadros.juan.kitchenrecipeapp.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.edu.unab.cuadros.juan.kitchenrecipeapp.R;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.adapter.RecipeAdapter;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.models.Recipe;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvRecipe;
    List<Recipe>myRecipes;
    RecipeAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //m
        rvRecipe = (RecyclerView)findViewById(R.id.rv_recipe);
        myRecipes = new ArrayList<>();

        fakedata();

        RecyclerView.LayoutManager managerG = new GridLayoutManager(MainActivity.this,2);
        rvRecipe.setLayoutManager(managerG);
        rvRecipe.setItemAnimator(new DefaultItemAnimator());
        rvRecipe.setHasFixedSize(true);

        configurarAdapter();


    }

    public void configurarAdapter(){
        mAdapter = new RecipeAdapter(myRecipes);
        mAdapter.setOnClickListener(view -> Toast.makeText(MainActivity.this, "Seleccion: "+myRecipes.get(rvRecipe.getChildAdapterPosition(view)).getName(), Toast.LENGTH_SHORT).show());
        rvRecipe.setAdapter(mAdapter);
    }

    public void fakedata(){
        Recipe recipe1 = new Recipe();
        recipe1.setName("carne");
        recipe1.setUser("jcuadros398@unab.edu.co");
        recipe1.setUrlImage("https://www.archies.co/media/di-carne-min.jpg");

        myRecipes.add(recipe1);

        Recipe recipe2 = new Recipe();
        recipe2.setName("pollo");
        recipe2.setUser("jcuadros398@unab.edu.co");
        recipe2.setUrlImage("https://www.archies.co/media/di-carne-min.jpg");

        myRecipes.add(recipe2);

        Recipe recipe3 = new Recipe();
        recipe3.setName("pollo");
        recipe3.setUser("jcuadros398@unab.edu.co");
        recipe3.setUrlImage("https://www.archies.co/media/di-carne-min.jpg");

        myRecipes.add(recipe3);

        Recipe recipe4 = new Recipe();
        recipe4.setName("pollo");
        recipe4.setUser("jcuadros398@unab.edu.co");
        recipe4.setUrlImage("https://www.archies.co/media/di-carne-min.jpg");

        myRecipes.add(recipe4);
    }
}