package com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.holders.AssetHolder;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.interfaces.PopupMenu;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by francisco on 01/10/15.
 */
public class AssetFactoryAdapter extends FermatAdapter<AssetFactory, AssetHolder> {

    private PopupMenu menuItemClick;

    public AssetFactoryAdapter(Context context) {
        super(context);
    }

    public AssetFactoryAdapter(Context context, List<AssetFactory> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected AssetHolder createHolder(View itemView, int type) {
        return new AssetHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_row_asset;
    }

    @Override
    protected void bindHolder(final AssetHolder holder, final AssetFactory data, final int position) {
        holder.itemView.setVisibility(View.VISIBLE);
        switch (data.getState()) {
            case DRAFT:
                renderDraf(holder, data, position);
                holder.itemView.setLongClickable(true);
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (menuItemClick != null) {
                            menuItemClick.onMenuItemClickListener(view, data, position);
                        }
                        return true;
                    }
                });
                break;
            case FINAL:
                renderFinal(holder, data, position);
                holder.itemView.setLongClickable(false);
                break;
            case PENDING_FINAL:
                renderPendingFinal(holder, data, position);
                holder.itemView.setLongClickable(false);
                break;
            default:
                holder.itemView.setVisibility(View.INVISIBLE);
                holder.itemView.setLongClickable(false);
                break;
        }
        List<Resource> resources = data.getResources();
        if (resources != null && resources.size() > 0) {
            holder.thumbnail.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(resources.get(0).getResourceBinayData())));
        } else {
            holder.thumbnail.setImageResource(R.drawable.img_asset_image);
        }
        // loading image resource in background
        /*new FermatWorker((android.app.Activity) context, new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                if (result != null && result.length > 0) {
                    try {
                        BitmapDrawable drawable = (BitmapDrawable) result[0];
                        holder.thumbnail.setImageDrawable(drawable);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {

            }
        }) {
            @Override
            protected BitmapDrawable doInBackground() throws Exception {
                BitmapDrawable drawable = null;
                if (data.getResources() != null && context != null) {
                    for (Resource resource : data.getResources()) {
                        byte[] imageBytes = resource.getResourceBinayData();
                        if (imageBytes != null) {
                            drawable = new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
                            break;
                        }
                    }
                }
                return drawable;
            }
        }.execute();*/
    }

    private void renderPendingFinal(AssetHolder holder, AssetFactory data, int position) {
        holder.rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        holder.bottomLine.setVisibility(View.GONE);
        holder.state.setTextColor(ContextCompat.getColor(context, R.color.state_color_publishing));
        holder.state.setBackgroundResource(R.drawable.white);
        holder.state.setVisibility(View.VISIBLE);
        holder.name.setTextColor(ContextCompat.getColor(context, R.color.white_asset_name));
        holder.amount.setTextColor(ContextCompat.getColor(context, R.color.white_amount));
        holder.bitcoins.setTextColor(ContextCompat.getColor(context, R.color.white_bitcoins));
        holder.name.setText(data.getName() != null ? data.getName() : context.getString(R.string.app_unnamed));
        holder.state.setText(R.string.home_asset_state_publishing);
        holder.amount.setText(String.format(context.getString(R.string.home_row_asset_amount), data.getQuantity()));
        holder.bitcoins.setText(String.format(context.getString(R.string.home_row_asset_bitcoins), data.getAmount()));
    }

    private void renderFinal(AssetHolder holder, AssetFactory data, int position) {
        holder.rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_gray));
        holder.bottomLine.setVisibility(View.VISIBLE);
        holder.state.setTextColor(ContextCompat.getColor(context, R.color.state_color_publishing));
        holder.state.setVisibility(View.GONE);
        holder.state.setBackgroundResource(R.drawable.white);
        holder.name.setTextColor(ContextCompat.getColor(context, R.color.white));
        holder.amount.setTextColor(ContextCompat.getColor(context, R.color.black));
        holder.bitcoins.setTextColor(ContextCompat.getColor(context, R.color.black));
        holder.name.setText(data.getName() != null ? data.getName() : context.getString(R.string.app_unnamed));
        holder.state.setText(R.string.home_asset_state_published);
        holder.amount.setText(String.format(context.getString(R.string.home_row_asset_amount), data.getQuantity()));
        holder.bitcoins.setText(String.format(context.getString(R.string.home_row_asset_bitcoins), data.getAmount()));
    }

    private void renderDraf(AssetHolder holder, AssetFactory data, int position) {
        holder.rowView.setBackgroundColor(ContextCompat.getColor(context, R.color.blue));
        holder.bottomLine.setVisibility(View.GONE);
        holder.state.setTextColor(ContextCompat.getColor(context, R.color.state_color_editable));
        holder.state.setBackgroundResource(R.drawable.blue);
        holder.state.setVisibility(View.VISIBLE);
        holder.name.setTextColor(ContextCompat.getColor(context, R.color.blue_asset_name));
        holder.amount.setTextColor(ContextCompat.getColor(context, R.color.blue_amount));
        holder.bitcoins.setTextColor(ContextCompat.getColor(context, R.color.blue_bitcoins));
        holder.name.setText(data.getName() != null ? data.getName() : context.getString(R.string.app_unnamed));
        holder.state.setText(R.string.home_asset_state_editable);
        holder.amount.setText(String.format(context.getString(R.string.home_row_asset_amount), data.getQuantity()));
        holder.bitcoins.setText(String.format(context.getString(R.string.home_row_asset_bitcoins), data.getAmount()));
    }

    public void setMenuItemClick(PopupMenu menuItemClick) {
        this.menuItemClick = menuItemClick;
    }
}
