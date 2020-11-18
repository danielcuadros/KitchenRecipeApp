package co.edu.unab.cuadros.juan.kitchenrecipeapp.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import co.edu.unab.cuadros.juan.kitchenrecipeapp.R;


public class DetailNoLoginActivity extends AppCompatActivity {

    TextView editTextName;
    TextView editTextuser;
    ImageView imageViewNo;
    TextView  editTextDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_no_login);

        editTextName = findViewById(R.id.textView_name1);
        editTextuser = findViewById(R.id.textView_user2);
        editTextDescription = findViewById(R.id.textView_description1);
        imageViewNo = findViewById(R.id.imageView_urlimage11);

        String nombre = getIntent().getStringExtra("nombre");
        String descripcion = getIntent().getStringExtra("descripcion");
        String url = getIntent().getStringExtra("url");
        String user= getIntent().getStringExtra("usar");

        editTextName.setText(nombre);
        editTextuser.setText(user);
        editTextDescription.setText(descripcion);
        Glide.with(DetailNoLoginActivity.this).load(url).into(imageViewNo);
    }
}