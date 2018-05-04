package team11.mobileiot.myooz.views;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

import java.util.Collection;
import java.util.List;

import team11.mobileiot.myooz.R;
import team11.mobileiot.myooz.models.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private List<Note> list = null;

    public NoteAdapter(List<Note> list) {
        this.list = list;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View iv = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.bindItem(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void addAll(Collection<?extends Note> collection){
        int size=list.size();
        list.addAll(collection);
        notifyItemRangeChanged(size,collection.size());
    }
}

class NoteViewHolder extends RecyclerView.ViewHolder {
    private TextView commentText,numkudos,time;
    private Button reportButton;
    private SimpleDraweeView simpleDraweeView;

    public NoteViewHolder(View itemView) {
        super(itemView);
        commentText = itemView.findViewById(R.id.comment_content);
        simpleDraweeView = itemView.findViewById(R.id.note_image);
        numkudos=itemView.findViewById(R.id.kudosnumber);
        time=itemView.findViewById(R.id.time);
        reportButton = itemView.findViewById(R.id.comment_report);
    }

    public void bindItem(final Note src) {
        final String note_id = src.id;
        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>(){
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                float ratio = imageInfo.getWidth() / (float)imageInfo.getHeight();
                simpleDraweeView.setAspectRatio(ratio);
            }
        };
        simpleDraweeView.setController(Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener)
                .setUri(Uri.parse(src.avatar)).build());
        simpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(simpleDraweeView.getContext(), NoteActivity.class);
                intent.putExtra("note", src);
                simpleDraweeView.getContext().startActivity(intent);
            }
        });
        numkudos.setText("0");
        time.setText("2017-11-17");
        commentText.setText(src.content);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Improve this one
                View popView = LayoutInflater.from(view.getContext()).inflate(R.layout.report_window, null);
                final PopupWindow popupWindow = new PopupWindow(popView, 1000, 1000);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupWindow.setFocusable(true);
                popupWindow.update();
                final Button cancelButton = popView.findViewById(R.id.close_button);
                final Button reportButton = popView.findViewById(R.id.report_button);
                final EditText reportText = popView.findViewById(R.id.report_text);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                reportButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reportText.setText("We will handle the report within 24 hours. Meanwhile, if you have any concern, please call 230-123-2345");
                        reportText.setKeyListener(null);
                        cancelButton.setVisibility(View.INVISIBLE);
                        reportButton.setText("OK");
                        reportButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                            }
                        });
                    }
                });
            }
        });
    }
}