package co.edu.unab.cuadros.juan.kitchenrecipeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.R;
import co.edu.unab.cuadros.juan.kitchenrecipeapp.models.Comentario;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    List<Comentario> myComment;
    CommentAdapter.OnItemClickListener onItemClickListener;

    public CommentAdapter(List<Comentario> myComment) {
        this.myComment = myComment;
        this.onItemClickListener = null;
    }

    public void setOnItemClickListener(CommentAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setComment(List<Comentario> myComment) {
        this.myComment = myComment;
        notifyDataSetChanged();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{

        TextView textComment;


        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            textComment=itemView.findViewById(R.id.text_comentario);

        }

        public void onBind( final Comentario myComment){

            textComment.setText(myComment.getMensaje());
            if(onItemClickListener != null) {

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(myComment,getAdapterPosition());
                    }
                });
            }
        }
    }


    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_comentario,parent,false);
        return new CommentAdapter.CommentViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {

        Comentario comment = myComment.get(position);
        holder.onBind(comment);
    }

    @Override
    public int getItemCount() {
        return myComment.size();
    }

    public interface OnItemClickListener{

        void onItemClick(Comentario myComment, int position);

    }
}
