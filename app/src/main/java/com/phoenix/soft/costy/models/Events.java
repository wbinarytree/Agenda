package com.phoenix.soft.costy.models;

/**
 * Created by yaoda on 03/03/17.
 * Events for RxBus
 *
 */

public class Events {

    //change full toolbar layout
    //ex: AppbarLayout/ CollapsingToolbarLayout /ImageView
    public static class ToolbarChangeEvent{
        public boolean isVisible() {
            return visible;
        }

        private boolean visible;

        public ToolbarChangeEvent(boolean visible) {
            this.visible = visible;
        }

    }
}
