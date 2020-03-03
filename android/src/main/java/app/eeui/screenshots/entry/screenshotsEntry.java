package app.eeui.screenshots.entry;

import android.content.Context;

import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;

import app.eeui.framework.extend.annotation.ModuleEntry;
import app.eeui.screenshots.eeuiScreenshotsModule;

@ModuleEntry
public class screenshotsEntry {

    /**
     * APP启动会运行此函数方法
     * @param content Application
     */
    public void init(Context content) {

        try {
            WXSDKEngine.registerModule("eeuiScreenshots", eeuiScreenshotsModule.class);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }

}
