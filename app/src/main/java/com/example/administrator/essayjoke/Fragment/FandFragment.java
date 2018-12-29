package com.example.administrator.essayjoke.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.essayjoke.PasswoedEditText.CustomerKeyboard;
import com.example.administrator.essayjoke.PasswoedEditText.PasswordEditText;
import com.example.administrator.essayjoke.R;
import com.example.baselibrary.dialog.AlertDialog;

/**
 * Created by nana on 2018/9/12.
 */

public class FandFragment extends Fragment implements View.OnClickListener, CustomerKeyboard.CustomerKeyboardClickListener, PasswordEditText.PasswordFullListener {
    private CustomerKeyboard mCustomerKeyboard;
    private PasswordEditText mPasswordEt;
    private Button mPasswordBt;
    private ImageView mDeleteDialog;
    private AlertDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fand, container, false);
        mPasswordBt = view.findViewById(R.id.password_bt);
        mPasswordBt.setOnClickListener(this);

        return view;
    }

    @Override
    public void click(String number) {
        mPasswordEt.addPassword(number);
    }

    @Override
    public void delete() {
        mPasswordEt.deleteLastPassword();
    }

    @Override
    public void passwordFull(String password) {
        Toast.makeText(getActivity(), "密码输入完成--》" + password, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.password_bt:
                //弹出dialog，从底部弹出带动画
                dialog = new AlertDialog.Builder(getActivity())
                        .setContentView(R.layout.dialog_customer_keyboard)
                        .fromButton(true).fullWidth().show();
                mCustomerKeyboard = dialog.getView(R.id.customer_keyboard);
                mCustomerKeyboard.setOnCustomeKeyboardClickListener(this);
                mPasswordEt = dialog.getView(R.id.password_et);
                mPasswordEt.setEnabled(false);
                mPasswordEt.setOnPasswordFullListener(this);
                mDeleteDialog = dialog.getView(R.id.delete_dialog);
                mDeleteDialog.setOnClickListener(this);
                break;
            case R.id.delete_dialog:
                dialog.dismiss();
                break;

        }

    }
}
