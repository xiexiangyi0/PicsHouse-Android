package com.xiangyixie.picshouse.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by xiangyixie on 3/10/15.
 */
public class UserWarning {
    public static void warn(Context ctxt, String txt) {
        Toast.makeText(ctxt, txt, Toast.LENGTH_LONG).show();
    }

    public static void warn(Context ctxt, int id)  {
        Toast.makeText(ctxt, id, Toast.LENGTH_LONG).show();
    }
}
