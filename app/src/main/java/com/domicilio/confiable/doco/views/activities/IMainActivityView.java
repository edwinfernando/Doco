package com.domicilio.confiable.doco.views.activities;

import android.content.Context;

/**
 * Created by edwinmunoz on 11/28/16.
 */

public interface IMainActivityView {
    void expandFAB();
    void hideFAB();
    void deployDialogPromotions(Context context);
    void deployDialogIsDriverDoco(Context context);
}
