package com.ducnd.demorealmmvp.remote.database;

import android.content.Context;

import com.ducnd.realmmvp.remote.database.BaseStoreManager;

import org.jetbrains.annotations.NotNull;

/**
 * Created by ducnd on 8/11/17.
 */

public class StoreManager extends BaseStoreManager {
    private static final String NAME_SCHEMA = "music.realm";
    private static final int VERSION_SCHEMA = 1;

    public StoreManager(@NotNull Context context) {
        super(context, NAME_SCHEMA, VERSION_SCHEMA);
    }
}
