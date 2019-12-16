package com.shanchuang.ssf.net.progress;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.vondear.rxui.view.dialog.RxDialogLoading;
import com.vondear.rxui.view.progressing.SpriteFactory;
import com.vondear.rxui.view.progressing.Style;
import com.vondear.rxui.view.progressing.sprite.Sprite;


/**
 * 一个判断dialog是否显示的类
 */
public class ProgressDialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    // private ProgressDialog pd;
    private RxDialogLoading rxDialogLoading;
    private Context context;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener,
                                 boolean cancelable) {
        super();
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    private void initProgressDialog() {
        if (rxDialogLoading == null) {
            rxDialogLoading = new RxDialogLoading(context);
            Sprite sprite = SpriteFactory.create(Style.THREE_BOUNCE);//设置style
            rxDialogLoading.getLoadingView().setIndeterminateDrawable(sprite);
            rxDialogLoading.setCancelable(false);//设置是否可取消
            //当网络请求完成后取消dialog
            if (cancelable) {
                rxDialogLoading.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }

            if (!rxDialogLoading.isShowing()) {
                rxDialogLoading.show();
            }
        }
    }

    private void dismissProgressDialog() {
        if (rxDialogLoading != null) {
            rxDialogLoading.dismiss();
            rxDialogLoading = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }

}
