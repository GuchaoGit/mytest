package com.guc.ui;

import android.content.ClipboardManager;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.inputmethodservice.Keyboard.KEYCODE_DONE;
import static android.inputmethodservice.Keyboard.KEYCODE_MODE_CHANGE;
import static android.inputmethodservice.Keyboard.KEYCODE_SHIFT;

/**
 * Created by guc on 2018/7/11.
 * 描述：车牌输入
 */
public class KeyBoardCarNumberView extends LinearLayout {
    private EditText mEditText;
    private KeyboardView mKeyboardView;
    private boolean isShift;
    /**
     * 输入模式 0：省份 1：字母 2：数字
     */
    private int mInputType = 0;
    private boolean mShowPreview = true;
    private Context mCxt;
    private OnInputFinishListener mOnInputFinishListener;
    private String[] mProvinces = new String[]{"京", "津", "沪", "渝", "黑"
            , "吉", "辽", "苏", "浙", "闽"
            , "皖", "赣", "湘", "晋", "冀"
            , "鲁", "豫", "鄂", "陕", "甘"
            , "宁", "蒙", "新", "青", "藏"
            , "川", "贵", "云", "桂", "粤"
            , "琼", "港", "澳", "台"
    };
    private KeyboardView.OnKeyboardActionListener mListener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {
            // 手指离开该键（抬起手指或手指移动到其它键）时回调
        }

        @Override
        public void onPress(int primaryCode) {
            // 当某个键被按下时回调，只有press_down时会触发，长按不会多次触发
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            // 键被按下时回调，在onPress后面。如果isRepeat属性设置为true，长按时会连续回调

            Editable editable = mEditText.getText();
            int selectionPosition = mEditText.getSelectionStart();
            switch (primaryCode) {
                case Keyboard.KEYCODE_DELETE:
                    // 如果按下的是delete键，就删除EditText中的str
                    if (editable != null && editable.length() > 0) {
                        if (selectionPosition > 0) {
                            editable.delete(selectionPosition - 1, selectionPosition);
                        }
                    }
                    break;
                case KEYCODE_SHIFT://大小写切换
                    isShift = !isShift;
                    mKeyboardView.setShifted(isShift);
                    break;
                case KEYCODE_DONE://完成
                    mKeyboardView.closing();
                    if (mOnInputFinishListener != null) {
                        mOnInputFinishListener.onFinish();
                    }
                    break;
                case KEYCODE_MODE_CHANGE://输入法模式改变
                    mInputType++;
                    changeMode(mInputType);
                    break;
                case 100://复制全部
                    ClipboardManager cm = (ClipboardManager) mCxt.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(mEditText.getText().toString());
                    Toast.makeText(mCxt, "已复制到剪切板", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    switch (mInputType) {
                        case 0:
                            editable.insert(selectionPosition, getProvice(primaryCode));
                            break;
                        case 1:
                            editable.insert(selectionPosition, Character.toString((char) primaryCode));
                            break;
                        case 2:
                            editable.insert(selectionPosition, Character.toString((char) primaryCode));
                            break;
                    }
                    break;
                // 其实还有很多code的判断，比如“完成”键、“Shift”键等等，这里就不一一列出了
                // 对于Shift键被按下，需要做两件事，一件是把键盘显示字符全部切换为大写，调用setShifted()方法就可以了；另一件是把Shift状态下接收到的正常字符（Shift、完成、Delete等除外）的code值-32再转换成相应str，插入到EidtText中
            }
        }
    };

    public KeyBoardCarNumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCxt = context;
        init(context);
    }

    private String getProvice(int primaryCode) {
        return mProvinces[primaryCode - 65];
    }

    public void setOnInputFinishListener(OnInputFinishListener mOnInputFinishListener) {
        this.mOnInputFinishListener = mOnInputFinishListener;
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.lyt_keyboard, this, true);

        mKeyboardView = findViewById(R.id.kv_lyt_keyboard);
        // 加载上面的qwer.xml键盘布局，new出KeyBoard对象
        Keyboard kb;
        switch (mInputType) {
            case 0:
                kb = new Keyboard(context, R.xml.my_keyboard_provice);
                break;
            case 1:
                kb = new Keyboard(context, R.xml.my_keyboard_letter);
                break;
            case 2:
                kb = new Keyboard(context, R.xml.my_keyboard_number);
                break;
            default:
                kb = new Keyboard(context, R.xml.my_keyboard_provice);
                break;
        }
        mKeyboardView.setKeyboard(kb);
        mKeyboardView.setEnabled(true);
        // 设置是否显示预览，就是某个键时，上面弹出小框显示你按下的键，称为预览框
        mKeyboardView.setPreviewEnabled(mShowPreview);
        // 设置监听器
        mKeyboardView.setOnKeyboardActionListener(mListener);
    }

    // 设置接受字符的EditText
    public void setStrReceiver(EditText et) {
        mEditText = et;
    }

    /**
     * 设置是否显示预览
     *
     * @param showPreview
     */
    public void setShowPreview(boolean showPreview) {
        this.mShowPreview = showPreview;
        mKeyboardView.setPreviewEnabled(mShowPreview);
    }

    /**
     * 改变输入模式
     */
    public void changeMode(int type) {
        this.mInputType = type;
        Keyboard kb;
        switch (type) {
            case 0:
                kb = new Keyboard(mCxt, R.xml.my_keyboard_provice);
                break;
            case 1:
                kb = new Keyboard(mCxt, R.xml.my_keyboard_letter);
                break;
            case 2:
                kb = new Keyboard(mCxt, R.xml.my_keyboard_number);
                break;
            default:
                kb = new Keyboard(mCxt, R.xml.my_keyboard_provice);
                break;
        }
        mKeyboardView.setKeyboard(kb);
    }

    public interface OnInputFinishListener {
        void onFinish();
    }
}
