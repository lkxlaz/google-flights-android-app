package mad.com.myflight.utils;

import android.os.Binder;

/**
 * wraps object wrapper for binders
 */

public class ObjectWrapperForBinder extends Binder {

    private final Object mData;

    /**
     * constructs a ObjectWrapperForBinder
     *
     * @param data object that is to be wrapped.
     */
    public ObjectWrapperForBinder(Object data) {
        mData = data;
    }

    /**
     * returns object wrapped by ObjectWrapperForBinder
     *
     */
    public Object getData() {
        return mData;
    }
}
