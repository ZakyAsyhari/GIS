package com.mapping.ahassmap;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mapbox.mapboxsdk.annotations.Marker;

public class TabMapBottomSheet extends BottomSheetDialogFragment {
    private static final String DESCRIBABLE_KEY = "keyId";
    private static Marker currentMarker;

    public static TabMapBottomSheet newInstance(Marker marker){
        Log.i("title", marker.getTitle());
        currentMarker = marker;
        TabMapBottomSheet tabMapBottomSheet = new TabMapBottomSheet();
//        Bundle bundle = new Bundle();
//        bundle.puts(DESCRIBABLE_KEY, null);
//        mainActivityBottomSheet.setArguments(bundle);

        return tabMapBottomSheet ;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View contentView = View.inflate(getContext(), R.layout.tab_map_bottom_sheet, null);
        TextView tvTitlePlace = (TextView) contentView.findViewById(R.id.tvTitlePlace);
        TextView tvStreetPlace = (TextView) contentView.findViewById(R.id.tvStreetPlace);
        TextView tvLatLng = (TextView) contentView.findViewById(R.id.tvLatLng);
        RelativeLayout relativeLayoutDirection = (RelativeLayout) contentView.findViewById(R.id.relativeDirection);
        tvTitlePlace.setText(currentMarker.getTitle());
        tvStreetPlace.setText(currentMarker.getSnippet());
        tvLatLng.setText(Double.toString( currentMarker.getPosition().getLatitude()) +", "+ Double.toString( currentMarker.getPosition().getLongitude()));

        relativeLayoutDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NavigationAcitvity.class);
                intent.putExtra("Latitude", currentMarker.getPosition().getLatitude());
                intent.putExtra("Longitude", currentMarker.getPosition().getLongitude());
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.setContentView(contentView);
    }
}
