package com.cpigeon.app.pigeonnews.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.utils.http.CommonUitls;

/**
 * Created by Zhu TingYu on 2018/1/8.
 */

public class InputCommentDialog extends DialogFragment {

    TextView btn;
    public EditText content;

    private OnPushClickListener listener;
    private OnDialogInitListener showedListener;

    public Dialog dialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.dialog_fragment_news_comment_layout);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        assert window != null;
        window.setWindowAnimations(R.style.AnimBottomDialog);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        initView(dialog);
        dialog.setOnShowListener(dialog1 -> {
            CommonUitls.showKeyBoard(getActivity(), content);
        });
        /*dialog.setOnDismissListener(dialog1 -> {
            CommonUitls.showKeyBoard(getActivity());
        });*/
        return dialog;
    }

    private void initView(Dialog dialog) {
        btn = dialog.findViewById(R.id.text_btn);
        content = dialog.findViewById(R.id.content);
        btn.setOnClickListener(v -> {
            listener.click(content);
        });
        if (showedListener != null) {
            showedListener.inited(content);
        }
    }

    public void closeDialog() {
        CommonUitls.hideSoftInput(getActivity(), content);
        dismiss();
    }

    public interface OnPushClickListener {
        void click(EditText editText);
    }

    public interface OnDialogInitListener {
        void inited(EditText editText);
    }

    public void setPushClickListener(OnPushClickListener listener) {
        this.listener = listener;
    }

    public void setInitedListener(OnDialogInitListener showedListener) {
        this.showedListener = showedListener;
    }
}
