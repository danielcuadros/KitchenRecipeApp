package co.edu.unab.cuadros.juan.kitchenrecipeapp.activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import co.edu.unab.cuadros.juan.kitchenrecipeapp.R;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.adapter.CommentAdapter;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.models.Comentario;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.models.Recipe;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.repositorio.CommentRepository;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.repositorio.RecipeRepository;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.service.CallBackRecipeApp;

public class DetailActivity extends AppCompatActivity {

    RecyclerView  rvComentario;
    List<Comentario> misComentarios;
    TextView textViewName;
    TextView textViewDescripcion;
    TextView textViewUser;
    ImageView imageView;
    CommentAdapter mAdapter;
    Button btn_comentar;
    Button editar;
    Button eliminar;
    EditText edit_comentario;
    CommentRepository commentRepository;
    RecipeRepository recipeRepository;
    private static final int CODE_EDIT = 100;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_EDIT){
            finish();
            if(resultCode == RESULT_OK){
                Toast.makeText(DetailActivity.this, "All Good", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(DetailActivity.this, "All Bad", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textViewName = findViewById(R.id.textView_name);
        textViewUser = findViewById(R.id.textView_8787);

        textViewDescripcion = findViewById(R.id.textView_description);
        imageView = findViewById(R.id.imageView_urlimage);

        rvComentario = (RecyclerView)findViewById(R.id.rv_comentarios);
        btn_comentar = (Button)findViewById(R.id.button_comentar);
        editar = (Button)findViewById(R.id.button_eeeditar);
        eliminar = (Button)findViewById(R.id.button_eeeliminar);
        edit_comentario = (EditText)findViewById(R.id.edit_comentar);
        commentRepository = new CommentRepository(DetailActivity.this);
        recipeRepository = new RecipeRepository(DetailActivity.this);

        misComentarios=new ArrayList<>();

        configurarAdaptador();
        RecyclerView.LayoutManager managerL = new LinearLayoutManager(DetailActivity.this);
        rvComentario.setLayoutManager(managerL);
        rvComentario.setItemAnimator(new DefaultItemAnimator());
        rvComentario.setHasFixedSize(true);


        String idRecipe = getIntent().getStringExtra("idRecipe");



        recipeRepository.obtenerById(idRecipe, new CallBackRecipeApp<Recipe>() {
            @Override
            public void correct(Recipe respuest) {
                textViewName.setText(respuest.getName());
                textViewDescripcion.setText(respuest.getDescription());
                textViewUser.setText(respuest.getUser());


                Glide.with(DetailActivity.this).load(respuest.getUrlImage()).into(imageView);

                btn_comentar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String comment = edit_comentario.getText().toString();
                        Comentario miComentario = new Comentario(respuest.getId(),comment);
                        commentRepository.agregarFS(miComentario, new CallBackRecipeApp<Boolean>() {
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

                eliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recipeRepository.eliminarFS(respuest, new CallBackRecipeApp<Boolean>() {
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

                editar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(DetailActivity.this,AddRecipeActivity.class);
                        i.putExtra("idRecipe",respuest.getId());
                        startActivityForResult(i,CODE_EDIT);
                    }
                });
            }

            @Override
            public void error(Exception e) {
                finish();
            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarListado();
    }

    private void actualizarListado() {

        commentRepository.obtenerTodosFS(new CallBackRecipeApp<List<Comentario>>() {
           @Override
           public void correct(List<Comentario> respuest) {
               misComentarios=respuest;
               mAdapter.setComment(misComentarios);
               }

               @Override
                public void error(Exception e) {

                 Log.d("Este",e.getMessage());
           }
        });
    }

    //

    private void configurarAdaptador() {
        mAdapter=new CommentAdapter(misComentarios);
        rvComentario.setAdapter(mAdapter);
    }
}