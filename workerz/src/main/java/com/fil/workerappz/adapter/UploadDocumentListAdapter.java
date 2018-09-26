package com.fil.workerappz.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.R;
import com.fil.workerappz.UploadYourDocumentActivity;
import com.fil.workerappz.fragments.MediaChooseFragmentForKYC;
import com.fil.workerappz.pojo.KYCUpdateDocumentListJsonPojo;
import com.fil.workerappz.pojo.KYCUploadedDocumentListJsonPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.retrofit.RestApi;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.ApiClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.squareup.picasso.Picasso;

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
 * Created by HS on 05-Mar-18.
 * FIL AHM
 */

public class UploadDocumentListAdapter extends RecyclerView.Adapter<UploadDocumentListAdapter.ViewHolder> {

    private final Activity mContext;

    private int userId = 0;
    private int counter = 0;

    private int uploadDocForPosition;
    private final List<KYCUploadedDocumentListJsonPojo.Info> kycUploadedDocumentListJsonPojos;
    TextView skipTextViewViewHeader2;
    LinearLayout mainUploadYourDocumentLinearLayout;
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private LabelListData datumLable_languages = new LabelListData();


    public UploadDocumentListAdapter(Activity mContext, List<KYCUploadedDocumentListJsonPojo.Info> kycUploadedDocumentListJsonPojos, int userId, TextView skipTextViewViewHeader2, LabelListData datumLable_languages, MessagelistData datumLable_languages_msg, LinearLayout mainUploadYourDocumentLinearLayout) {
        this.mContext = mContext;
        this.userId = userId;
        this.kycUploadedDocumentListJsonPojos = kycUploadedDocumentListJsonPojos;
        this.datumLable_languages_msg = datumLable_languages_msg;
        this.datumLable_languages = datumLable_languages;
        this.skipTextViewViewHeader2 = skipTextViewViewHeader2;
        this.mainUploadYourDocumentLinearLayout = mainUploadYourDocumentLinearLayout;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upload_document_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textviewupload.setText(datumLable_languages.getUpload());
        holder.textviewremove.setText(datumLable_languages.getRemove());
        holder.documentNameTextView.setText(new String(Base64.decode(kycUploadedDocumentListJsonPojos.get(position).getKycdoctypeName().trim().getBytes(), Base64.DEFAULT)) + " - " + new String(Base64.decode(kycUploadedDocumentListJsonPojos.get(position).getKycdocnameName().trim().getBytes(), Base64.DEFAULT)));
        if (kycUploadedDocumentListJsonPojos.get(position).getUserkycStatus().equalsIgnoreCase("Approved")) {
            holder.uploadSignImageView.setVisibility(View.INVISIBLE);
            Constants.Updateflag = false;
            holder.uploadStatusTextView.setText(kycUploadedDocumentListJsonPojos.get(position).getUserkycStatus());
        } else if (kycUploadedDocumentListJsonPojos.get(position).getUserkycStatus().equalsIgnoreCase("Pending")) {
            holder.uploadSignImageView.setVisibility(View.VISIBLE);
            holder.removeSignImageView.setVisibility(View.VISIBLE);
            holder.textviewupload.setText("Reupload");
            holder.uploadStatusTextView.setText(kycUploadedDocumentListJsonPojos.get(position).getUserkycStatus());
            Constants.Updateflag = true;
        } else if (kycUploadedDocumentListJsonPojos.get(position).getUserkycStatus().equalsIgnoreCase("Rejected")) {
            holder.uploadSignImageView.setVisibility(View.VISIBLE);
            holder.removeSignImageView.setVisibility(View.VISIBLE);
            holder.textviewupload.setText("Reupload");
            Constants.Updateflag = true;
            holder.uploadStatusTextView.setText(kycUploadedDocumentListJsonPojos.get(position).getUserkycStatus());

        } else if (kycUploadedDocumentListJsonPojos.get(position).getUserkycStatus().equalsIgnoreCase("Query")) {
            holder.uploadSignImageView.setVisibility(View.VISIBLE);
            holder.textviewupload.setText("Reupload");
            holder.removeSignImageView.setVisibility(View.VISIBLE);
            Constants.Updateflag = true;
            holder.uploadStatusTextView.setText(kycUploadedDocumentListJsonPojos.get(position).getUserkycStatus());

        } else {
            holder.removeSignImageView.setVisibility(View.INVISIBLE);
            holder.uploadStatusTextView.setText("");
            holder.uploadSignImageView.setVisibility(View.VISIBLE);
            holder.textviewupload.setText(datumLable_languages.getUpload());
            Constants.Updateflag = false;
        }

        holder.removeSignImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeUploadedKYCDocument(holder.getAdapterPosition());
            }
        });

        holder.uploadSignImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kycUploadedDocumentListJsonPojos.size()>0) {
                    counter=0;
                    for (int i = 0; i < kycUploadedDocumentListJsonPojos.size(); i++) {
                        if (kycUploadedDocumentListJsonPojos.get(i).getUserkycImage().equals("")) {

                        } else {
                            counter++;
                        }
                    }
                }
                Constants.uploaddocument=counter;
                if (counter >=3) {
                    Constants.showMessage(mainUploadYourDocumentLinearLayout, mContext, "You can Upload maximum Three documents");
                } else {
                    uploadDocForPosition = holder.getAdapterPosition();

                    if (kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).getUserkycStatus().equalsIgnoreCase("") || kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).getUserkycStatus().equalsIgnoreCase(datumLable_languages.getApproved())) {
                        Constants.Updateflag = false;
                    } else {
                        Constants.Updateflag = true;
                    }
                    MediaChooseFragmentForKYC mediaChooseFragmentForKYC = new MediaChooseFragmentForKYC();
                    mediaChooseFragmentForKYC.show(((UploadYourDocumentActivity) mContext).getSupportFragmentManager(), "BottomSheet Fragment");
                }
            }
        });

        Picasso.with(mContext)
                .load(Constants.IMAGE_URL_KYC + kycUploadedDocumentListJsonPojos.get(position).getUserkycImage())
                .placeholder(R.drawable.place_holder_image)
                .error(R.drawable.place_holder_image)
                .into(holder.documentImageView);

    }

    @Override
    public int getItemCount() {
        return kycUploadedDocumentListJsonPojos.size();
    }

    public void createKYCDoc(String imgName, final LinearLayout mainUploadYourDocumentLinearLayout) {
        RestApi apiService = ApiClient.getClient().create(RestApi.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", userId);
            jsonObject.put("kycdocnameID", kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).getKycdocnameID());
            jsonObject.put("userkycImage", imgName);
            jsonObject.put("kycdoctypeID", kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).getKycdoctypeID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.i("System out", "json " + json);

        Call<List<KYCUploadedDocumentListJsonPojo>> call = apiService.createKYCDocumentJsonCall(json);
        Constants.showProgress(mContext);

        call.enqueue(new Callback<List<KYCUploadedDocumentListJsonPojo>>() {
            @Override
            public void onResponse(Call<List<KYCUploadedDocumentListJsonPojo>> call, Response<List<KYCUploadedDocumentListJsonPojo>> response) {
                CustomLog.d("System out", "response code " + response.body().toString());

                if (response.body() != null) {
                    if (response.body().get(0).getStatus() == true) {

                        Constants.closeProgress();
                        kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).setUserID(String.valueOf(userId));
                        kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).setUserkycImage(response.body().get(0).getInfo().get(0).getUserkycImage());
                        Constants.showMessage(mainUploadYourDocumentLinearLayout, mContext, datumLable_languages_msg.getFileUploadedSuccessfully());
                        kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).setUserkycStatus(response.body().get(0).getInfo().get(0).getUserkycStatus());
                        kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).setUserkycStatusReason(response.body().get(0).getInfo().get(0).getUserkycStatusReason());
                        kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).setUserkycStatusDate(response.body().get(0).getInfo().get(0).getUserkycStatusDate());
                        kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).setUserkycCreatedDate(response.body().get(0).getInfo().get(0).getUserkycCreatedDate());
                        kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).setKycstatushistory(response.body().get(0).getInfo().get(0).getKycstatushistory());
                        kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).setUserkycID(response.body().get(0).getInfo().get(0).getUserkycID());

                        notifyDataSetChanged();
                        if (datumLable_languages.getSave() != null) {
                            skipTextViewViewHeader2.setText(datumLable_languages.getSave());
                        } else {
                            skipTextViewViewHeader2.setText("Save");
                        }
                    } else {
                        CustomLog.d("System out", "response false " + response.body().get(0).getInfo());
                    }
                } else {

                }
                Constants.closeProgress();
            }

            @Override
            public void onFailure(Call<List<KYCUploadedDocumentListJsonPojo>> call, Throwable t) {
                // Log error here since request failed
                CustomLog.e("System out", t.getMessage());
                Constants.closeProgress();
                t.printStackTrace();
            }
        });
    }

    private void removeUploadedKYCDocument(final int position) {
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(mContext);
        }

        builder.setTitle(datumLable_languages.getWorkerAppz())
                .setMessage(datumLable_languages.getRemoveThisDocument())
                .setCancelable(false)
                .setPositiveButton(datumLable_languages.getYes(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        removeDocumentJsonCall(position);
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

    private void removeDocumentJsonCall(final int position) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userkycID", kycUploadedDocumentListJsonPojos.get(position).getUserkycID());
            jsonObject.put("userID", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "signIn json " + json);
        Constants.showProgress(mContext);
        Call<List<KYCUploadedDocumentListJsonPojo>> call = RestClient.get().removeKYCDocumentJsonCall(json);

        call.enqueue(new Callback<List<KYCUploadedDocumentListJsonPojo>>() {
            @Override
            public void onResponse(Call<List<KYCUploadedDocumentListJsonPojo>> call, Response<List<KYCUploadedDocumentListJsonPojo>> response) {
                Constants.closeProgress();

                if (response.body() != null && response.body() instanceof ArrayList) {
                    if (response.body().get(0).getStatus() == true) {

                        kycUploadedDocumentListJsonPojos.get(position).setUserkycID("0");
//                        kycUploadedDocumentListJsonPojos.get(position).setKycdocnameID("0");
                        kycUploadedDocumentListJsonPojos.get(position).setUserID("0");
                        kycUploadedDocumentListJsonPojos.get(position).setUserkycImage("");
                        kycUploadedDocumentListJsonPojos.get(position).setUserkycStatus("");
                        kycUploadedDocumentListJsonPojos.get(position).setUserkycStatusReason("");
                        kycUploadedDocumentListJsonPojos.get(position).setUserkycStatusDate("");
                        kycUploadedDocumentListJsonPojos.get(position).setUserkycCreatedDate("");
                        kycUploadedDocumentListJsonPojos.get(position).setKycstatushistory("");

                        for (int i = 0; i < kycUploadedDocumentListJsonPojos.size(); i++) {
                            if (kycUploadedDocumentListJsonPojos.get(i).getUserkycImage().equalsIgnoreCase("")) {
                                skipTextViewViewHeader2.setText(datumLable_languages.getSkip());
                            } else {
                                skipTextViewViewHeader2.setText(datumLable_languages.getSave());
                            }
                        }
                        notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<KYCUploadedDocumentListJsonPojo>> call, Throwable t) {
                Constants.closeProgress();

            }
        });
    }

    public void updateKYCDOC(String imgName, final LinearLayout mainUploadYourDocumentLinearLayout) {
        RestApi apiService = ApiClient.getClient().create(RestApi.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", String.valueOf(userId));
            jsonObject.put("kycdocnameID", kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).getKycdocnameID());
            jsonObject.put("userkycImage", imgName);
            jsonObject.put("userkycID", kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).getUserkycID());
            jsonObject.put("kycdoctypeID", kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).getKycdoctypeID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.i("System out", "json " + json);

        Call<List<KYCUpdateDocumentListJsonPojo>> call = apiService.updateKYCDocumentJsonCall(json);
        Constants.showProgress(mContext);

        call.enqueue(new Callback<List<KYCUpdateDocumentListJsonPojo>>() {
            @Override
            public void onResponse(Call<List<KYCUpdateDocumentListJsonPojo>> call, Response<List<KYCUpdateDocumentListJsonPojo>> response) {
                CustomLog.d("System out", "response code " + response.body());
                if (response.body() != null) {
                    if (response.body().get(0).getStatus() == true) {
                        Constants.closeProgress();

//                        kycUploadedDocumentListJsonPojos.get(0).getData().get(uploadDocForPosition).setUserkycID(response.body().get(0).getInfo().get(0).getUserkycID());
//                        kycUploadedDocumentListJsonPojos.get(0).getData().get(uploadDocForPosition).setKycdocnameID(response.body().get(0).getInfo().get(0).getKycdocnameID());
//                        kycUploadedDocumentListJsonPojos.get(0).getData().get(uploadDocForPosition).setKycdocnameName(response.body().get(0).getInfo().get(0).getKycdocnameName());
                        kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).setUserID(String.valueOf(userId));
                        kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).setUserkycImage(response.body().get(0).getInfo().get(uploadDocForPosition).getUserkycImage());
                        Constants.showMessage(mainUploadYourDocumentLinearLayout, mContext, datumLable_languages_msg.getFileUploadedSuccessfully());
                        kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).setUserkycStatus(response.body().get(0).getInfo().get(uploadDocForPosition).getUserkycStatus());
                        kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).setUserkycStatusReason(response.body().get(0).getInfo().get(uploadDocForPosition).getUserkycStatusReason());
                        kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).setUserkycStatusDate(response.body().get(0).getInfo().get(uploadDocForPosition).getUserkycStatusDate());
                        kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).setUserkycCreatedDate(response.body().get(0).getInfo().get(uploadDocForPosition).getUserkycCreatedDate());
                        kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).setKycstatushistory(response.body().get(0).getInfo().get(uploadDocForPosition).getKycstatushistory());
                        kycUploadedDocumentListJsonPojos.get(uploadDocForPosition).setUserkycID(String.valueOf(response.body().get(0).getInfo().get(uploadDocForPosition).getUserkycID()));

                        notifyDataSetChanged();
                        skipTextViewViewHeader2.setText(datumLable_languages.getSave());
                    } else {
                        CustomLog.d("System out", "response false " + response.body().get(0).getInfo());
                    }
                } else {

                }
                Constants.closeProgress();
            }

            @Override
            public void onFailure(Call<List<KYCUpdateDocumentListJsonPojo>> call, Throwable t) {
                // Log error here since request failed
                CustomLog.e("System out", t.getMessage());
                Constants.closeProgress();
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.documentNameTextView)
        TextView documentNameTextView;
        @BindView(R.id.documentImageView)
        ImageView documentImageView;
        @BindView(R.id.textviewupload)
        TextView textviewupload;
        @BindView(R.id.uploadSignImageView)
        FrameLayout uploadSignImageView;
        @BindView(R.id.textviewremove)
        TextView textviewremove;
        @BindView(R.id.removeSignImageView)
        FrameLayout removeSignImageView;
        @BindView(R.id.uploadStatusTextView)
        TextView uploadStatusTextView;
        @BindView(R.id.mainlinearlayoutadapter)
        LinearLayout mainlinearlayoutadapter;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}