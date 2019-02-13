package com.ycbjie.api;


import android.app.Activity;
import android.view.View;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <pre>
 *     @author 杨充
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/06/21
 *     desc  : 初始化类
 *     revise:
 * </pre>
 */
public class OnceInit {

    private static Map<Class<?>, AbstractInjector<Object>> INJECTORS = new LinkedHashMap<>();

    private static final long INTERVAL_TIME = 2000;
    private static final String PROXY = "PROXY";

    public static void once(Activity activity, long intervalTime) {
        AbstractInjector<Object> injector = findInjector(activity);
        injector.inject(Finder.ACTIVITY, activity, activity);
        injector.setIntervalTime(intervalTime);
    }

    public static void once(View view, long intervalTime) {
        AbstractInjector<Object> injector = findInjector(view);
        injector.inject(Finder.VIEW, view, view);
        injector.setIntervalTime(intervalTime);
    }

    public static void once(Activity activity) {
        once(activity, INTERVAL_TIME);
    }

    public static void once(View view) {
        once(view, INTERVAL_TIME);
    }

    private static AbstractInjector<Object> findInjector(Object activity) {
        Class<?> clazz = activity.getClass();
        AbstractInjector<Object> injector = INJECTORS.get(clazz);
        if (injector == null) {
            try {
                Class injectorClazz = Class.forName(clazz.getName() + "$$" + PROXY);
                injector = (AbstractInjector<Object>) injectorClazz.newInstance();
                INJECTORS.put(clazz, injector);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return injector;
    }

}
