#import "WXScreenshotsModule.h"
#import "UIView+Screenshots.h"
#import "UIImage+Screenshots.h"
#import <WeexPluginLoader/WeexPluginLoader.h>

@implementation WXScreenshotsModule
@synthesize weexInstance;

WX_PlUGIN_EXPORT_MODULE(screenshots, WXScreenshotsModule)
WX_EXPORT_METHOD(@selector(shots:callback:))

-(void)shots:(NSString*)ref  callback:(WXModuleCallback)callback{
    [self findComponent:ref instance:weexInstance block:^(WXComponent *comp) {
        UIImage *img = [comp.view toImage];
        NSString *path = [img saveToDisk:@"shots.png"];
        if (!path) {
            callback(@{@"status":@"error", @"msg":@"截图失败", @"path":@""});
        }else{
            callback(@{@"status":@"success", @"msg":@"", @"path":path});
        }
    }];
}

- (UIImage *)snapsHotView:(UIView *)view
{
    UIGraphicsBeginImageContextWithOptions(view.bounds.size,YES,[UIScreen mainScreen].scale);
    [view drawViewHierarchyInRect:view.bounds afterScreenUpdates:NO];
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return image;
}

-(void)findComponent:(NSString *)elemRef instance:(WXSDKInstance*)instance block:(void (^)(WXComponent *))block {
    if (!elemRef) {
        return;
    }
    WXPerformBlockOnComponentThread(^{
        WXComponent *component = (WXComponent *)[instance componentForRef:elemRef];
        if (!component) {
            return;
        }
        dispatch_async(dispatch_get_main_queue(), ^{
            block(component);
        });
    });
}

@end
