package jp.yitt.top_navigation_panel;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

class PanelItemAdapter extends RecyclerView.Adapter<PanelItemAdapter.ViewHolder> {

    private Menu menu;
    private OnItemSelectedListener listener;

    public PanelItemAdapter(Menu menu) {
        this.menu = menu;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_panel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("Adapter", "onBind");
        holder.bind(menu.getItem(position));
    }

    @Override
    public int getItemCount() {
        if (menu != null) {
            return menu.size();
        } else {
            return 0;
        }
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout itemLayout;
        private ImageView iconImage;
        private TextView titleText;

        ViewHolder(View itemView) {
            super(itemView);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.layout_item);
            iconImage = (ImageView) itemView.findViewById(R.id.image_icon);
            titleText = (TextView) itemView.findViewById(R.id.text_title);
        }

        void bind(final MenuItem item) {
            itemLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (listener == null) {
                        return;
                    }
                    listener.onItemSelected(item.getItemId());
                }
            });

            iconImage.setImageDrawable(item.getIcon());
            titleText.setText(item.getTitle());
        }
    }
}
