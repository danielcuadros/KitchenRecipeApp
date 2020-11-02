package co.edu.unab.cuadros.juan.kitchenrecipeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvRecipe;
    List<Recipe>myRecipes;
    RecipeAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //m
        rvRecipe=(RecyclerView)findViewById(R.id.rv_recipe);
        myRecipes=new ArrayList<>();

        RecyclerView.LayoutManager managerG = new GridLayoutManager(MainActivity.this,2);
        rvRecipe.setLayoutManager(managerG);
        rvRecipe.setItemAnimator(new DefaultItemAnimator());
        rvRecipe.setHasFixedSize(true);


    }

    public void configurarAdapter(){
        mAdapter = new RecipeAdapter(myRecipes);

        mAdapter.setOnClickListener(view -> Toast.makeText(MainActivity.this, "Seleccion: "+myRecipes.get(rvRecipe.getChildAdapterPosition(view)).getName(), Toast.LENGTH_SHORT).show());
        rvRecipe.setAdapter(mAdapter);
    }
}