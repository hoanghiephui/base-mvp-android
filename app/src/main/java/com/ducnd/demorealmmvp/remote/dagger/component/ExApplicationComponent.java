package com.ducnd.demorealmmvp.remote.dagger.component;

import android.content.Context;

import com.ducnd.demorealmmvp.common.ExApplication;
import com.ducnd.demorealmmvp.remote.dagger.anomation.AccountContext;
import com.ducnd.demorealmmvp.remote.dagger.anomation.ApplicationContext;
import com.ducnd.demorealmmvp.remote.dagger.module.ExApplicationModule;
import com.ducnd.demorealmmvp.remote.interact.main.AccountInteraction;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ducnd on 8/10/17.
 */
@Singleton
@Component(modules = ExApplicationModule.class)
public interface ExApplicationComponent {
    void inject(ExApplication application);

    @ApplicationContext
    Context context();

    @AccountContext
    AccountInteraction getAccountCount();
}
