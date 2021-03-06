package com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.navigation_drawer;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;

/**
 * Created by frank on 12/9/15.
 */
public class RedeemPointWalletNavigationViewAdapter extends FermatAdapter<MenuItem, RedeemPointWalletNavigationItemMenuViewHolder> {
    protected RedeemPointWalletNavigationViewAdapter(Context context) {
        super(context);
    }

    @Override
    protected RedeemPointWalletNavigationItemMenuViewHolder createHolder(View itemView, int type) {
        return new RedeemPointWalletNavigationItemMenuViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_wallet_asset_redeem_point_navigation_row;
    }

    @Override
    protected void bindHolder(RedeemPointWalletNavigationItemMenuViewHolder holder, MenuItem data, int position) {
        try {

            holder.getLabel().setText(data.getLabel());

            switch (position) {
                case 0:
                    holder.getIcon().setImageResource(R.drawable.sad_face);
                    break;
                case 1:
                    holder.getIcon().setImageResource(R.drawable.sad_face);
                    break;
                case 2:
                    holder.getIcon().setImageResource(R.drawable.sad_face);
                    break;
                case 3:
                    holder.getIcon().setImageResource(R.drawable.sad_face);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
