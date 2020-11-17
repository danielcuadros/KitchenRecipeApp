package co.edu.unab.cuadros.juan.kitchenrecipeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import co.edu.unab.cuadros.juan.kitchenrecipeapp.R;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.models.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    List<Recipe> myRecipes;
    OnItemClickListener onItemClickListener;

    public RecipeAdapter(List<Recipe> myRecipes) {
        this.myRecipes = myRecipes;
        this.onItemClickListener = null;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setRecipes(List<Recipe> myRecipes) {
        this.myRecipes = myRecipes;
        notifyDataSetChanged();
    }


    public class RecipeViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName;
        ImageView imageViewPhoto;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textView_namerecipe);
            imageViewPhoto = itemView.findViewById(R.id.imageView_recipe);

        }

        public void onBind(final Recipe myRecipe){

            textViewName.setText(myRecipe.getName());

            if(!myRecipe.getUrlImage().isEmpty()){
                Glide.with(itemView.getContext()).load(myRecipe.getUrlImage()).into(imageViewPhoto);
            }else{
                Glide.with(itemView.getContext()).load(R.drawable.ic_launcher_background).into(imageViewPhoto);
            }

            if(onItemClickListener != null) {

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(myRecipe,getAdapterPosition());
                    }
                });
            }
        }
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipes_list,parent,false);
        return new RecipeViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = myRecipes.get(position);
        holder.onBind(recipe);
    }

    @Override
    public int getItemCount() {
        return myRecipes.size();
    }

    public interface OnItemClickListener{

        void onItemClick(Recipe myRecipe, int position);

    }
}

