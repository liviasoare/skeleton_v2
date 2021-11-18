package com.servustech.skeleton.features.account;

public enum AccountStatus {
    ACTIVE, BANNED, LOCKED, INACTIVE;

    public boolean isActive() {
        return this == ACTIVE;
    }

    public boolean isBanned() {
        return this == BANNED;
    }

    public boolean isLocked() {
        return this == LOCKED;
    }

    public boolean isInactive(){return this == INACTIVE;}
}
