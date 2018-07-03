package com.fil.workerappz.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fil.workerappz.ProfileActivity;
import com.fil.workerappz.R;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.SessionManager;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by farhad on 1/13/17.
 */

public class MediaChooseFragmentForProfile extends BottomSheetDialogFragment {

    private static final int REQUEST_CODE_CAMERA_PERMISSIONS = 001;
    private static final int REQUEST_CODE_READ_PERMISSIONS = 002;
    private static final int REQUEST_CODE_WRITE_PERMISSIONS = 003;
    @BindView(R.id.takePhotoTextView)
    TextView takePhotoTextView;
    @BindView(R.id.chooseGalleryTextView)
    TextView chooseGalleryTextView;
    @BindView(R.id.cancelTextView)
    TextView cancelTextView;
    private boolean cameraPermission = false;
    private boolean readPermission = false;
    private boolean writePermission = false;

    private ProfileActivity profileActivity;
    private LabelListData datumLable_languages=new LabelListData();

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.media_choose, null);
        dialog.setContentView(contentView);
        ButterKnife.bind(this, contentView);
        try {
            SessionManager sessionManager = new SessionManager(profileActivity);
            datumLable_languages = sessionManager.getAppLanguageLabel();


            if (datumLable_languages != null) {
                takePhotoTextView.setText(datumLable_languages.getTakePhoto());
                chooseGalleryTextView.setText(datumLable_languages.getChooseFromGallery());
                cancelTextView.setText(datumLable_languages.getCancel());
            }
            else
            {
                takePhotoTextView.setText(getResources().getString(R.string.take_photo));
                chooseGalleryTextView.setText(getResources().getString(R.string.choose_from_gallery));
                cancelTextView.setText(getResources().getString(R.string.cancel));
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (cameraPermission && writePermission) {
            if (getDialog() != null) {
                dismiss();
            }
            profileActivity.startCameraActivity();
            cameraPermission = false;
        }

        if (readPermission) {
            if (getDialog() != null) {
                dismiss();
            }
            profileActivity.openGallery();
            readPermission = false;
        }

    }

    private void checkCameraPermission() {
        if (!addPermission(Manifest.permission.CAMERA)) {
            String message = "You need to grant access to camera permission for take picture.";
            Constants.showMessageOkCancel(getActivity(), message, new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (getDialog() != null) {
                        dialog.dismiss();

                    }
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            REQUEST_CODE_CAMERA_PERMISSIONS);
                }
            });

        } else {
            if (getDialog() != null) {
                dismiss();
            }
            if (writePermission) {
                profileActivity.startCameraActivity();
            } else {
                checkWritePermission();
            }
        }
    }

    private void checkWritePermission() {
        if (!addPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            String message = "You need to grant access to write permission for take picture.";
            Constants.showMessageOkCancel(getActivity(), message, new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (getDialog() != null) {
                        dialog.dismiss();
                    }

                    ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_CODE_WRITE_PERMISSIONS);

                }
            });
        } else {
            if (getDialog() != null) {
                dismiss();
            }
            profileActivity.startCameraActivity();
        }
    }

    private void checkReadPermission() {
        if (!addPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            String message = "You need to grant access to read permission for choose picture.";
            Constants.showMessageOkCancel(getActivity(), message, new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (getDialog() != null) {
                        dialog.dismiss();
                    }
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_READ_PERMISSIONS);
                }
            });
        } else {
            if (getDialog() != null) {
                dismiss();
            }
            profileActivity.openGallery();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        profileActivity = (ProfileActivity) context;
    }

    private void requestCameraPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA_PERMISSIONS);
        }
    }

    private boolean addPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (getDialog() != null) {
            dismiss();
        }

        switch (requestCode) {
            case REQUEST_CODE_CAMERA_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    profileActivity.startCameraActivity();
                    if (writePermission) {
                        profileActivity.startCameraActivity();
                    } else {
                        checkWritePermission();
                    }

                }
                break;
            case REQUEST_CODE_READ_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    profileActivity.openGallery();
                }
                break;
            case REQUEST_CODE_WRITE_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    profileActivity.startCameraActivity();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @OnClick({R.id.takePhotoTextView, R.id.chooseGalleryTextView, R.id.cancelTextView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.takePhotoTextView:
//                if (Build.VERSION.SDK_INT >= 23) {
//                    checkCameraPermission();
//
//                } else {
//                    if (getDialog() != null) {
//                        dismiss();
//                    }
//                    profileActivity.startCameraActivity();
//                }

                if (Build.VERSION.SDK_INT >= 23) {
                    dismiss();
                    TedPermission tedPermission = new TedPermission(getActivity());
                    tedPermission.setPermissionListener(permissionlistener)
                            .setDeniedMessage("If you reject permission, you can not use this service\nPlease turn on permissions at [Setting] > [Permission]")
                            .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                            .check();

                } else {
                    if (getDialog() != null) {
                        dismiss();
                    }
                    profileActivity.startCameraActivity();
                }
                break;
            case R.id.chooseGalleryTextView:
                if (Build.VERSION.SDK_INT >= 23) {
                    dismiss();
                    TedPermission tedPermission = new TedPermission(getActivity());
                    tedPermission.setPermissionListener(permissionlistener1)
                            .setDeniedMessage("If you reject permission, you can not use this service\nPlease turn on permissions at [Setting] > [Permission]")
                            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                            .check();
                } else {
                    if (getDialog() != null) {
                        dismiss();
                    }
                    profileActivity.openGallery();
                }
                break;
            case R.id.cancelTextView:
                if (getDialog() != null) {
                    dismiss();
                }
                break;
        }
    }


    public interface OnItemSelectedListener {
        public void onItemSelected(String view);
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {

            profileActivity.startCameraActivity();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {

        }
    };
    PermissionListener permissionlistener1 = new PermissionListener() {
        @Override
        public void onPermissionGranted() {

            profileActivity.openGallery();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {

        }
    };
}