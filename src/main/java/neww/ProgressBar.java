package neww;

import android.content.Context;
import android.widget.ImageView;

import ex.Length;
import wclass.android.ui.drawable.ProgressDrawable;
import wclass.android.ui.drawable.simple_shape_drawable.CircleCornerDrawable;
import wclass.android.ui.params.RectBoolean;
import wclass.android.util.ViewUT;
import wclass.util.ColorUT;

/**
 * @作者 做就行了！
 * @时间 2019-05-14下午 11:51
 * @该类描述： -
 * @名词解释： -
 * @该类用途： -
 * @注意事项： -
 * @使用说明： -
 * @思维逻辑： -
 * @优化记录： -
 * @待解决： -
 */
public class ProgressBar extends NewProgressBar<ImageView, ImageView, ImageView> {

    private ProgressDrawable pd;

    public ProgressBar(Context context) {
        super(context);
    }

    @Override
    protected ImageView onCreateBgView(Context context) {
        return new ImageView(context);
    }

    @Override
    protected ImageView onCreateProgressView(Context context) {
        return new ImageView(context);
    }

    @Override
    protected ImageView onCreateThumbView(Context context) {
        return new ImageView(context);
    }

    @Override
    protected void onCreateViewsFinish() {
        bgView.setBackground(new CircleCornerDrawable(ColorUT.BLACK, RectBoolean.TRUE));
        pd = new ProgressDrawable(0, ColorUT.GREEN);
        progressView.setBackground(pd);
        thumbView.setBackground(new CircleCornerDrawable(ColorUT.RED, RectBoolean.TRUE));
    }

    @Override
    protected void onAdjustBgView(ImageView bgView, int w, int h) {
        ViewUT.toMatchParent(bgView);
    }

    @Override
    protected void onAdjustThumbView(ImageView thumbView, int w, int h) {

        ViewUT.adjustSize(thumbView, h, h);
    }

    @Override
    protected void onAdjustProgressView(ImageView progressView, int w, int h) {

        ViewUT.toMatchParent(progressView);
    }

    @Override
    protected Length onGetProgressLengthInParent(int w, int h) {
        return new Length(h / 2, w - h / 2);
    }

    @Override
    protected void onProgressChanged(int progressValueInRoot, float progress) {
        pd.setProgress(progress);
        thumbView.setX(progressValueInRoot-getHeight()/2);
    }
}
