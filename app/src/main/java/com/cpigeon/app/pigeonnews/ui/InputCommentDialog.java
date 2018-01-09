package com.cpigeon.app.pigeonnews.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.cpigeon.app.R;

/**
 * Created by Zhu TingYu on 2018/1/8.
 */

public class InputCommentDialog extends DialogFragment {

    TextView btn;
    EditText content;

    private OnPushClickListener listener;

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

        return dialog;
    }

    private void initView(Dialog dialog) {
        btn = dialog.findViewById(R.id.text_btn);
        content = dialog.findViewById(R.id.content);
        btn.setOnClickListener(v -> {
            listener.click(content.getText().toString());
        });
    }

    public interface OnPushClickListener{
        void click(String content);
    }

    public void setListener(OnPushClickListener listener) {
        this.listener = listener;
    }
}
