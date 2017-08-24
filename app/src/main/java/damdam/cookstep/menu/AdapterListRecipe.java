package damdam.cookstep.menu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import damdam.cookstep.R;

/**
 * Created by Poste on 21/07/2016.
 */
public class AdapterListRecipe extends RecyclerView.Adapter<AdapterListRecipe.ViewHolder> implements View.OnClickListener {

    private ArrayList<RecipeData> listDataRecipe;

    public AdapterListRecipe(){
        listDataRecipe = new ArrayList<>();
    }

    @Override
    public void onClick(View view) {
        //int itemPosition = mRecyclerView.getChildLayoutPosition(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageRecipe;
        public TextView textRecipe;
        public ViewHolder(View itemView) {
            super(itemView);
            imageRecipe  = (ImageView) itemView.findViewById(R.id.imageViewRecipeListImage);
            textRecipe = (TextView) itemView.findViewById(R.id.textViewRecipeListText);
        }
    }

    @Override
    public AdapterListRecipe.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recipe_list, parent, false);
        v.setOnClickListener(this);
        ViewHolder vH = new ViewHolder(v);
        return vH;
    }

    @Override
    public void onBindViewHolder(AdapterListRecipe.ViewHolder holder, int position) {
        holder.textRecipe.setText(listDataRecipe.get(position).getRecipeTitle());
    }

    @Override
    public int getItemCount() {
        return listDataRecipe.size();
    }
}
