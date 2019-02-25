package com.fhss.shop.event;

public class RedPointsEvent {
    private boolean isShowRedPoints;

    public RedPointsEvent(boolean isShowRedPoints) {
        this.isShowRedPoints = isShowRedPoints;
    }

    public boolean isShowRedPoints() {
        return isShowRedPoints;
    }

    public void setShowRedPoints(boolean showRedPoints) {
        isShowRedPoints = showRedPoints;
    }
}
