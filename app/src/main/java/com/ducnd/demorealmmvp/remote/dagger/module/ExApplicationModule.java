package com.ducnd.demorealmmvp.remote.dagger.module;

import android.content.Context;

import com.ducnd.demorealmmvp.remote.dagger.anomation.AccountContext;
import com.ducnd.demorealmmvp.remote.dagger.anomation.ApplicationContext;
import com.ducnd.demorealmmvp.remote.database.StoreManager;
import com.ducnd.demorealmmvp.remote.interact.interf.IAccountInteraction;
import com.ducnd.demorealmmvp.remote.interact.main.AccountInteraction;
import com.ducnd.demorealmmvp.remote.interact.main.ApiConnector;
import com.ducnd.demorealmmvp.remote.interact.source.ResApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ducnd on 8/10/17.
 */
@Module
public class ExApplicationModule {
    private final Context mContextApp;

    public ExApplicationModule(Context contextApp) {
        mContextApp = contextApp;
    }

    @Provides
    @ApplicationContext
    Context providesContext() {
        return mContextApp;
    }

    @Provides
    @Singleton
    @AccountContext
    IAccountInteraction providesAccountInteraction() {
        return new AccountInteraction(new ApiConnector("http://j.ginggong.com", ResApi.class), new StoreManager(mContextApp));
    }

}
