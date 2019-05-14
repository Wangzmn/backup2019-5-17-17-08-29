package neww;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import ex.Length;
import wclass.android.ui.view.base_view.UsefulFrameLayout;
import wclass.android.util.DurationUT;
import wclass.android.util.debug.EventUT;
import wclass.android.util.debug.StringUT;
import wclass.enums.Orien5;
import wclass.ui.event_parser.MultiSingleParser;
import wclass.util.MathUT;

/**
 * @作者 做就行了！
 * @时间 2019-05-13下午 11:53
 * @该类描述： -
 * @名词解释： -
 * @该类用途： -
 * @注意事项： -
 * @使用说明： -
 * @思维逻辑： -
 * @优化记录： -
 * @待解决： -
 */

/**
 * todo
 * 1、怎么确定进度的百分比？
 * 想完睡觉。
 */
public abstract class NewProgressBar<B extends View, P extends View, T extends View> extends UsefulFrameLayout {
    private static final boolean DEBUG = true;
    protected B bgView;
    protected P progressView;
    protected T thumbView;
    private int progressValueInLength;
    private float progress;
    boolean init;

    public NewProgressBar(Context context) {
        super(context);
        parser = new MultiSingleParser(ViewConfiguration.get(context).
                getScaledTouchSlop());
        this.context = context;
    }

    @Override
    protected void onSizeChangedSafely(int w, int h) {
        super.onSizeChangedSafely(w, h);
        if (!init) {
            init();
        }

        onAdjustBgView(bgView, w, h);
        onAdjustProgressView(progressView, w, h);
        onAdjustThumbView(thumbView, w, h);
        length.setLength(onGetProgressLengthInParent(w, h));
        limitLength();
    }

    private void init() {
        bgView = onCreateBgView(context);
        progressView = onCreateProgressView(context);
        thumbView = onCreateThumbView(context);
        addView(bgView);
        addView(progressView);
        addView(thumbView);
        onCreateViewsFinish();
    }

    protected abstract B onCreateBgView(Context context);

    protected abstract P onCreateProgressView(Context context);

    protected abstract T onCreateThumbView(Context context);

    protected abstract void onCreateViewsFinish();

    //--------------------------------------------------
    protected abstract void onAdjustBgView(B bgView, int w, int h);

    protected abstract void onAdjustProgressView(P progressView, int w, int h);

    protected abstract void onAdjustThumbView(T thumbView, int w, int h);
    //--------------------------------------------------
    //超出则限制。

    /**
     * @param w
     * @param h
     * @return Length.start和Length.end相对于容器的left。
     */
    protected abstract Length onGetProgressLengthInParent(int w, int h);
    //////////////////////////////////////////////////

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (DEBUG) {
            Log.e("TAG", getClass() + "#onTouchEvent:" +
                    " event = " + EventUT.actionToStr(event));
        }

        //step 如果设置了点击事件，那就由他去吧。
        super.onTouchEvent(event);
        handleEvent(event);
        return true;
    }

    MultiSingleParser parser;
    private Context context;
    Length length = new Length();

    private void limitLength() {
        if (length.start < 0) {
            length.start = 0;
//            throw new IllegalStateException("start不能小于0。");
        }
        int width = getWidth();
        if (length.end > width) {
            length.end = width;
//            throw new IllegalStateException("end不能大于宽。");
        }
        if (length.end < length.start) {
            length.end = length.start;
//            throw new IllegalStateException("尾不能大于头。");
        }
    }

    protected abstract void onProgressChanged(int progressValueInRoot, float progress);


    /**
     * step 确定：
     * 1、300毫秒内才能触发点击。
     * 2、
     */
    private void handleEvent(MotionEvent ev) {
        parser.parse(ev);
        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (DEBUG) {
                    Log.e("TAG", getClass() + "#handleEvent.ACTION_DOWN:" +
                            "  ");
                }
                getParent().requestDisallowInterceptTouchEvent(true);
                onDown();
                break;
            case MotionEvent.ACTION_MOVE:
                //触发移动了。
                boolean moved = parser.getFirstTouchOrien() != Orien5.SITU;
                if (DEBUG) {
                    Log.e("TAG", getClass() + "#handleEvent.ACTION_MOVE:" +
                            " moved = " + moved + " 。");
                }
                if (moved) {
                    //todo 大幅度移动了。用变量标记，只进入一次该方法。
                    //todo thumb应该移动到此位置。
                    int value = (int) (parser.xMove + 0.5f);
                    limitPV(value);
                    limitProgress();

                    onProgressChanged(progressValueInLength, progress);
                    if (DEBUG) {
                        Log.e("TAG", getClass() + "#handleEvent.ACTION_MOVE:" +
                                " progressValueInLength = " + progressValueInLength +
                                ", progress = " + progress + " 。");
                    }
                }
                onMove();

                break;
            case MotionEvent.ACTION_UP:
                boolean doClick = parser.getFirstTouchOrien() == Orien5.SITU
                        && parser.getTimeDelta_cutDown() < DurationUT.CLICK_LIMIT_TIME;
                if (DEBUG) {
                    Log.e("TAG", getClass() + "#handleEvent.ACTION_UP:" +
                            " doClick = " + doClick + " 。" +"\n"+
                            "bgView = " + StringUT.toStr(bgView)+"\n"+
                            "progressView = " + StringUT.toStr(progressView)+
                            "thumbView = "+ StringUT.toStr(thumbView));

                    StringUT.toStr(bgView);
                }
                if (doClick) {
                    onClickProtected();
                }
                onUp();
                break;
            case MotionEvent.ACTION_CANCEL:
                if (DEBUG) {
                    Log.e("TAG", getClass() + "#handleEvent.ACTION_CANCEL:  ");
                }
                onCancel();
                break;
        }

        if (isInThumb(ev)) {
            //todo 设置触摸状态。
        }
        //todo 移动worm后，触发progress的滑动。
    }

    private void onClickProtected() {
    }

    protected void onMove() {
    }


    protected void onCancel() {
        thumbView.setPressed(false);
    }

    protected void onUp() {
        thumbView.setPressed(false);
    }

    private void onDown() {
        thumbView.setPressed(true);
    }

    Rect reuse = new Rect();

    private boolean isInThumb(MotionEvent ev) {
        thumbView.getHitRect(reuse);
        return reuse.contains(((int) ev.getX()), ((int) ev.getY()));
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (DEBUG) {
            Log.e("TAG", getClass() + "#onInterceptTouchEvent:" +
                    " event = " + EventUT.actionToStr(ev));
        }
        return true;
    }
    //--------------------------------------------------

    private void limitPV(int value) {
        progressValueInLength = MathUT.limit(value,
                length.start, length.end);
    }

    private void limitProgress() {
        progress = MathUT.limit(
                (float) (progressValueInLength - length.start)
                        / length.getValue()
                , 0, 1);
    }
    //////////////////////////////////////////////////
    /*子父类分类*/

    /**
     * 1、点击事件发出回调。
     */
    //////////////////////////////////////////////////

    /**/
    public void animToProgress(float progress) {

    }
}
