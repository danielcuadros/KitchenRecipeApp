package co.edu.unab.cuadros.juan.kitchenrecipeapp;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> implements View.OnClickListener {
    public List<Recipe>myRecipes;
    private View.OnClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public ImageView img;

        public MyViewHolder(View view){
            super(view);
            name = (TextView)view.findViewById(R.id.text_recipe_name);
            img = (ImageView)view.findViewById(R.id.img_recipe);
        }
    }
    public RecipeAdapter(List<Recipe> myRecipes){this.myRecipes = myRecipes;}
    public void setRecipes(List<Recipe> myRecipes){
        this.myRecipes=myRecipes;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecipeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipes_list,parent,false);
        itemView.setOnClickListener(this);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.MyViewHolder holder, int position) {

        Recipe recipe = myRecipes.get(position);
        holder.name.setText(recipe.getName());
        if(!recipe.getUrlImage().isEmpty()){
            Glide.with(holder.itemView.getContext()).load(recipe.getUrlImage()).into(holder.img);
        }else{
            Glide.with(holder.itemView.getContext()).load(R.drawable.ic_launcher_background).into(holder.img);

        }
    }

    @Override
    public int getItemCount() {
        return myRecipes.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {

        if (listener!=null){
            listener.onClick(view);
        }
    }
}
