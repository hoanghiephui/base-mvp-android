package com.ducnd.demorealmmvp.remote.interact.main;

import com.ducnd.demorealmmvp.remote.interact.interf.IAccountInteraction;
import com.ducnd.realmmvp.remote.interact.main.BasePresenter;
import com.ducnd.realmmvp.ui.base.BaseViewUI;

import org.jetbrains.annotations.NotNull;

/**
 * Created by ducnd on 8/11/17.
 */

public abstract class BasePresenterM<V extends BaseViewUI> extends BasePresenter<V> {
    protected IAccountInteraction mAccountInteraction;

    public BasePresenterM(@NotNull V view, @NotNull IAccountInteraction accountInteraction) {
        super(view);
        mAccountInteraction = accountInteraction;
    }
}
