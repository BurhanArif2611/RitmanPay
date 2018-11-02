package com.fil.workerappz.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.fil.workerappz.R;
import com.fil.workerappz.SignUpSubmitActivity;
import com.fil.workerappz.pojo.GetSecurityListPojo;
import com.fil.workerappz.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecurityQuestionListAdapter extends RecyclerView.Adapter<SecurityQuestionListAdapter.ViewHolder> {
    private Intent mIntent;
    private final Activity mContext;
    private int selectedradiobutton = -1;
    private List<GetSecurityListPojo.DataSecurityList> getSecurityListPojo;

    public SecurityQuestionListAdapter(Activity SignUpSubmitActivity, ArrayList<GetSecurityListPojo.DataSecurityList> sequrityQuestionListPojos) {
        this.getSecurityListPojo = sequrityQuestionListPojos;
        this.mContext = SignUpSubmitActivity;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_question_list, parent, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        holder.RadioButtonSignUpSubmit.setText(getSecurityListPojo.get(listPosition).getSecQuestion().trim());
        holder.RadioButtonSignUpSubmit.setTag(listPosition + 1);

        holder.RadioButtonSignUpSubmit.setChecked(selectedradiobutton == listPosition);

        if (listPosition==selectedradiobutton)
        {
            holder.answerEdittext.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.answerEdittext.setVisibility(View.GONE);
        }
        holder.RadioButtonSignUpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.answerId = "";
                Constants.answer ="";
                holder.answerEdittext.setText("");
                selectedradiobutton = holder.getAdapterPosition();
                holder.answerEdittext.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
            }
        });

        holder.answerEdittext.addTextChangedListener(new TextWatcher() {

            // the user's changes are saved here
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                Constants.answerId = getSecurityListPojo.get(listPosition).getSecID();
                Constants.answer = String.valueOf(c);
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });
    }

    @Override
    public int getItemCount() {
        return getSecurityListPojo.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.RadioButtonSignUpSubmit)
        RadioButton RadioButtonSignUpSubmit;
        @BindView(R.id.answerEdittext)
        AppCompatEditText answerEdittext;
        @BindView(R.id.mainQuestionsAdapterLinearLayout)
        LinearLayout mainQuestionsAdapterLinearLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
