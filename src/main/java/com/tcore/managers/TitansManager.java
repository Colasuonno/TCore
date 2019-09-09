package com.tcore.managers;

import com.tcore.api.TManager;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class TitansManager implements TManager {

    @Getter
    private static Set<TManager> managers = new HashSet<>();

    protected TitansManager() {
        managers.add(this);
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }
}
