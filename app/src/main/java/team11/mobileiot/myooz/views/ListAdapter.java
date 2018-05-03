package team11.mobileiot.myooz.views;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;

import team11.mobileiot.myooz.R;
import team11.mobileiot.myooz.models.Artiest;

/**
 * Created by flora on 5/2/18.
 */

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder>{
    private List<Artiest> list = null;

    public ListAdapter(List<Artiest> list) {
        this.list = list;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View iv = LayoutInflater.from(parent.getContext()).inflate(R.layout.artiest_card, parent, false);
        return new ListViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.bindImage(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void addAll(Collection<? extends Artiest> collection) {
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeChanged(size, collection.size());
    }
}


class ListViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;
    private TextView author;

    public ListViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.artiest_image);
        author=itemView.findViewById(R.id.artiest_name);
        //category= itemView.findViewById(R.id.artwork_card_category);

    }

    public void bindImage(final Artiest artist) {
        imageView.setImageBitmap(artist.bitmap);
        author.setText(artist.name);
        //category.setText(artwork.category);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(imageView.getContext(), ActivityArtistNote.class);
                intent.putExtra("artiest", artist);
                MainActivity.needBeaconUpdate = false;
                imageView.getContext().startActivity(intent);
            }
        });

    }
}
