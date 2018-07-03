package com.fil.workerappz.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.R;
import com.fil.workerappz.pojo.JsonListPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.NotificationListJsonPojo;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HS on 17-Mar-18.
 * FIL AHM
 */


public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {

    private final Activity mContext;
    private final List<NotificationListJsonPojo> notificationListJsonPojos;
    private final int userId;
    private MessagelistData datumLable_languages_msg=new MessagelistData();
    private LabelListData datumLable_languages=new LabelListData();

    public NotificationListAdapter(Activity context, List<NotificationListJsonPojo> notificationListJsonPojos, int userId, MessagelistData datumLable_languages_msg, LabelListData datumLable_languages) {
        this.mContext = context;
        this.notificationListJsonPojos = notificationListJsonPojos;
        this.datumLable_languages_msg = datumLable_languages_msg;
        this.datumLable_languages = datumLable_languages;
        this.userId = userId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_adapter, parent, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int listPosition) {
        holder.notificationTextView.setText(notificationListJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getNotificationMessageText());

        holder.closeImageViewNotificationAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(mContext);
                }

                builder.setTitle(datumLable_languages.getDelete())
                        .setMessage(datumLable_languages.getDeleteThisNotification())
                        .setCancelable(false)
                        .setPositiveButton(datumLable_languages.getYes(), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleteJsonCall(notificationListJsonPojos.get(0).getData().get(holder.getAdapterPosition()).getNotificationID(), holder.getAdapterPosition(), holder.mainNotificationAdapterLinearLayout);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(datumLable_languages.getNo(), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(R.drawable.app_icon)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationListJsonPojos.get(0).getData().size();
    }

    private void deleteJsonCall(String notificationID, final int listPosition, final View view) {

        Constants.showProgress(mContext);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", userId);
            jsonObject.put("notificationID", notificationID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "delete notification" + json);
        Call<List<JsonListPojo>> call = RestClient.get().notificationDeleteJsonCall(json);
        call.enqueue(new Callback<List<JsonListPojo>>() {
            @Override
            public void onResponse(Call<List<JsonListPojo>> call, final Response<List<JsonListPojo>> response) {
                if (response.body() != null && response.body() instanceof ArrayList) {
                    try {
                        CustomLog.d("System out", "true");
                        Constants.closeProgress();
                        if (response.body().get(0).getStatus()) {
                            notificationListJsonPojos.get(0).getData().remove(listPosition);
                            notifyDataSetChanged();
                            Constants.showMessage(view, mContext, datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));
                        } else {
                            Constants.showMessage(view, mContext, datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onFailure(Call<List<JsonListPojo>> call, Throwable t) {
                Constants.closeProgress();
                t.printStackTrace();
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notificationTextView)
        TextView notificationTextView;
        @BindView(R.id.closeImageViewNotificationAdapter)
        ImageView closeImageViewNotificationAdapter;
        @BindView(R.id.mainNotificationAdapterLinearLayout)
        LinearLayout mainNotificationAdapterLinearLayout;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}