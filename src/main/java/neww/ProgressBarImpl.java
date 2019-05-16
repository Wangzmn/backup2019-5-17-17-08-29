package neww;

import android.content.Context;
import android.widget.ImageView;

import ex.Length;
import wclass.android.ui.drawable.ColorWithStrokesDrawable;
import wclass.android.ui.drawable.ProgressDrawable;
import wclass.android.ui.drawable.simple_shape_drawable.CircleCornerDrawable;
import wclass.android.ui.params.RectBoolean;
import wclass.android.util.LayoutParamsUT;
import wclass.android.util.SizeUT;
import wclass.android.util.ViewUT;
import wclass.util.ColorUT;

/**
 * @作者 做就行了！
 * @时间 2019-05-15下午 5:07
 * @该类描述： -
 * @名词解释： -
 * @该类用途： -
 * @注意事项： -
 * @使用说明： -
 * @思维逻辑： -
 * @优化记录： -
 * @待解决： -
 */
public class ProgressBarImpl extends SimpleProgressBar {
    ColorWithStrokesDrawable bgPic = new ColorWithStrokesDrawable(ColorUT.WHITE,
            0.5f,
            0xff000000, 1, 0xffaaaaaa, 1);
    ProgressDrawable progressPic;
    private int margin;
    private CircleCornerDrawable thumbPic;

    boolean on;

    public ProgressBarImpl(Context context) {
        super(context);
        int pad = SizeUT.getMMpixel(context) / 2;
        if (pad < 4) {
            pad = 4;
        }
        this.margin = pad / 2;
        int quarter = pad / 4;
        int innerPart = pad - quarter * 3;
        int outColor = 0xff000000;
        int innerColor = 0xffdddddd;
        int outColorAfter = ColorUT.deSaturation(outColor, 0.2f, innerColor);
        int innerColorBefore = ColorUT.deSaturation(outColor, 0.6f, innerColor);
        bgPic = new ColorWithStrokesDrawable(ColorUT.WHITE,
                0.5f,
                outColor, quarter,
                outColorAfter, quarter,
                innerColorBefore, quarter,
                innerColor, innerPart);
        progressPic = new ProgressDrawable(ColorUT.GREEN);
        thumbPic = new CircleCornerDrawable(ColorUT.RED, RectBoolean.TRUE);
    }

    //////////////////////////////////////////////////

    @Override
    protected void onDown() {
        super.onDown();
    }

    @Override
    protected void onUp(boolean click) {
        super.onUp(click);
        if(!click) {
            float p = getProgress();
            if (p < 0.5f){
                animToProgress(0);
            }else{
                animToProgress(1);
            }
        }
    }

    //--------------------------------------------------
    @Override
    protected void onClickProtected() {
        super.onClickProtected();
        if (on) {
            on = false;
            animToProgress(0);
        } else {
            on = true;
            animToProgress(1);
        }
    }

    Length length = new Length();
    @Override
    protected Length onGetProgressLengthInParent(int w, int h) {
        int start = h / 2;
        int end = w - h / 2;
        length.setLength(start,end);
        return length;
    }

    @Override
    protected void onProgressChanged(float progress, int progressValueInRoot) {
        float progressInSelf = getProgressInSelf();
        progressPic.setProgress(progressInSelf);
        thumbView.setX(progressValueInRoot - getHotSpotInSelf(thumbView));
    }

    @Override
    protected void onSetBgViewPic(ImageView bgView) {
        bgView.setBackground(bgPic);
    }

    @Override
    protected void onSetProgressViewPic(ImageView progressView) {
//        pd = new ProgressDrawable(0, ColorUT.GREEN);
        progressView.setBackground(progressPic);
    }

    @Override
    protected void onSetThumbViewPic(ImageView thumbView) {
        thumbView.setBackground(thumbPic);
    }

    @Override
    protected void onAdjustBgView(ImageView bgView, int w, int h) {
        ViewUT.toMatchParent(bgView);
    }

    @Override
    protected void onAdjustProgressView(ImageView progressView, int w, int h) {
        progressView.setLayoutParams(LayoutParamsUT.frameParamsMatchParent(
                margin, margin, margin, margin
        ));
    }

    @Override
    protected void onAdjustThumbView(ImageView thumbView, int w, int h) {
        ViewUT.adjustSize(thumbView, h, h);
    }
}
