package wclass.android.util;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import wclass.enums.LayoutGravity;

/**
 * @作者 做就行了！
 * @时间 2019/3/20 0020
 * @使用说明：
 */
public class LayoutParamsUT {

    public static void setGravity(LinearLayout root, LayoutGravity layoutGravity) {
        switch (layoutGravity) {
            case LEFT_TOP:
                root.setGravity(Gravity.LEFT | Gravity.TOP);
                break;
            case LEFT_BOTTOM:
                root.setGravity(Gravity.LEFT | Gravity.BOTTOM);
                break;
            case RIGHT_TOP:
                root.setGravity(Gravity.RIGHT | Gravity.TOP);
                break;
            case RIGHT_BOTTOM:
                root.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
                break;
        }
    }

    public static void setGravity(RelativeLayout root, LayoutGravity layoutGravity) {
        switch (layoutGravity) {
            case LEFT_TOP:
                root.setGravity(Gravity.LEFT | Gravity.TOP);
                break;
            case LEFT_BOTTOM:
                root.setGravity(Gravity.LEFT | Gravity.BOTTOM);
                break;
            case RIGHT_TOP:
                root.setGravity(Gravity.RIGHT | Gravity.TOP);
                break;
            case RIGHT_BOTTOM:
                root.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
                break;
        }
    }
    //////////////////////////////////////////////////////////////////////
    /*domain 已检查。*/

    /**
     * 获取 布局参数。
     *
     * @param width  宽
     * @param height 高
     * @return 布局参数
     */
    public static ViewGroup.LayoutParams makeLayoutParams(int width, int height) {
        return new ViewGroup.LayoutParams(width, height);
    }

    //----------------------------------------------------------------------

    /**
     * 生成LinearLayout参数。
     *
     * @param width  宽。
     * @param height 高。
     * @return LinearLayout参数。
     */
    public static LinearLayout.LayoutParams makeLinearLayoutParams(int width, int height) {
        return new LinearLayout.LayoutParams(width, height);
    }

    /**
     * 生成LinearLayout参数。
     *
     * @param width  宽。
     * @param height 高。
     * @param weight 权重。
     * @return LinearLayout参数。
     */
    public static LinearLayout.LayoutParams makeLinearLayoutParams(int width, int height, float weight) {
        LinearLayout.LayoutParams p = makeLinearLayoutParams(width, height);
        p.weight = weight;
        return p;
    }
    //////////////////////////////////////////////////

    /**
     * @param width
     * @param height
     * @param gravity {@link Gravity}
     * @return
     */
    public static FrameLayout.LayoutParams makeFrameLayoutParams(int width, int height, int gravity) {
        FrameLayout.LayoutParams p = makeFrameLayoutParams(width, height);
        p.gravity = gravity;
        return p;
    }

    public static FrameLayout.LayoutParams makeFrameLayoutParams(int width, int height) {
        return new FrameLayout.LayoutParams(width, height);
    }
    //--------------------------------------------------
    public static ViewGroup.LayoutParams makeWrapperContent() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static ViewGroup.LayoutParams makeMatchParent() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static LinearLayout.LayoutParams makeLinearWidthFill() {
        return new LinearLayout.LayoutParams(0,1,0.00001f);
    }

    public static LinearLayout.LayoutParams makeLinearHeightFill() {
        return new LinearLayout.LayoutParams(1,0,1);
    }
}
