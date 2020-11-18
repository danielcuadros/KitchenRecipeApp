package co.edu.unab.cuadros.juan.kitchenrecipeapp.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.edu.unab.cuadros.juan.kitchenrecipeapp.R;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.adapter.CommentAdapter;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.models.Comentario;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.repositorio.CommentRepository;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.service.CallBackRecipeApp;

public class DetailActivity extends AppCompatActivity {

    RecyclerView  rvComentario;
    List<Comentario> misComentarios;
    CommentAdapter mAdapter;
    Button btn_comentar;
    EditText edit_comentario;
    CommentRepository commentRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        rvComentario=(RecyclerView)findViewById(R.id.rv_comentarios);
        btn_comentar=(Button)findViewById(R.id.button_comentar);
        edit_comentario=(EditText)findViewById(R.id.edit_comentar);
        commentRepository=new CommentRepository(DetailActivity.this);

        misComentarios=new ArrayList<>();

        configurarAdaptador();
        RecyclerView.LayoutManager managerL = new LinearLayoutManager(DetailActivity.this);
        rvComentario.setLayoutManager(managerL);
        rvComentario.setItemAnimator(new DefaultItemAnimator());
        rvComentario.setHasFixedSize(true);

        final String entrada = getIntent().getStringExtra("idUser");

        btn_comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = edit_comentario.getText().toString();
                Comentario miComentario = new Comentario(entrada,comment);
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

    private void configurarAdaptador() {
        mAdapter=new CommentAdapter(misComentarios);
        rvComentario.setAdapter(mAdapter);
    }
}