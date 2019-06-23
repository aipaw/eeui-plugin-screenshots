package app.eeui.screenshots;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.ui.component.WXComponent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.weex.plugin.annotation.WeexModule;

import app.eeui.framework.extend.base.WXModuleBase;
import app.eeui.screenshots.util.Picture;
import app.eeui.screenshots.util.SDCard;

@WeexModule(name = "screenshots")
public class eeuiScreenshotsModule extends WXModuleBase {

    @JSMethod
    public void shots(String id, JSCallback callback) {
        Map<String, Object> data = new HashMap<>();
        WXComponent com = findComponent(id);
        if (com == null) {
            data.put("status", "error");
            data.put("msg", "截图失败");
            data.put("path", "");
            callback.invoke(data);
            return;
        }
        Bitmap bitmap = viewConversionBitmap(com.getHostView());
        if (bitmap == null) {
            data.put("status", "error");
            data.put("msg", "截图失败");
            data.put("path", "");
            callback.invoke(data);
            return;
        }
        String dir = SDCard.getBasePath(getContext()) + "/shots/";
        File f = new File(dir);
        if (f.exists()) {
            f.delete();
        }
        f.mkdirs();
        Picture.saveImageToSDCard(dir + "shots.png", bitmap);
        data.put("status", "success");
        data.put("msg", "");
        data.put("path", dir + "shots.png");
        callback.invoke(data);
    }

    private Bitmap viewConversionBitmap(View view) {
        if (view == null) {
            return null;
        }
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }
}
