package com.example.mipro.netschool.Settings;

public class SettingsElement {
    private String title;
    private String description;
    private int icon;

    SettingsElement(String title, String description, int icon) {
        this.title = title;
        this.description = description;
        this.icon = icon;
    }

    String getTitle() {
        return this.title;
    }

    String getDescription() {
        return this.description;
    }

    int getIcon() {
        return this.icon;
    }
}
