/*
 * Copyright (C) 2014 Andrew Comminos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.morlunk.mumbleclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Singleton settings class for universal access to the app's preferences.
 * @author morlunk
 */
public class Settings {
    public static final String PREF_INPUT_METHOD = "audioInputMethod";
    /** Voice activity transmits depending on the amplitude of user input. */
    public static final String ARRAY_INPUT_METHOD_VOICE = "voiceActivity";
    /** Push to talk transmits on command. */
    public static final String ARRAY_INPUT_METHOD_PTT = "ptt";
    /** Continuous transmits always. */
    public static final String ARRAY_INPUT_METHOD_CONTINUOUS = "continuous";
    /** Handset mode transmits always, using the proximity sensor and phone speaker. */
    public static final String ARRAY_INPUT_METHOD_HANDSET = "handset";

    public static final String PREF_THRESHOLD = "vadThreshold";
    public static final int DEFAULT_THRESHOLD = 50;

    public static final String PREF_PUSH_KEY = "talkKey";
    public static final Integer DEFAULT_PUSH_KEY = -1;

    public static final String PREF_HOT_CORNER_KEY = "hotCorner";
    public static final String ARRAY_HOT_CORNER_NONE = "none";
    public static final String ARRAY_HOT_CORNER_TOP_LEFT = "topLeft";
    public static final String ARRAY_HOT_CORNER_BOTTOM_LEFT = "bottomLeft";
    public static final String ARRAY_HOT_CORNER_TOP_RIGHT = "topRight";
    public static final String ARRAY_HOT_CORNER_BOTTOM_RIGHT = "bottomRight";
    public static final String DEFAULT_HOT_CORNER = ARRAY_HOT_CORNER_NONE;

    public static final String PREF_PUSH_BUTTON_HIDE_KEY = "hidePtt";
    public static final Boolean DEFAULT_PUSH_BUTTON_HIDE = false;

    public static final String PREF_PTT_TOGGLE = "togglePtt";
    public static final Boolean DEFAULT_PTT_TOGGLE = false;

    public static final String PREF_INPUT_RATE = "input_quality";
    public static final String DEFAULT_RATE = "48000";

    public static final String PREF_INPUT_QUALITY = "input_bitrate";
    public static final int DEFAULT_INPUT_QUALITY = 40000;

    public static final String PREF_AMPLITUDE_BOOST = "inputVolume";
    public static final Integer DEFAULT_AMPLITUDE_BOOST = 100;
    
    public static final String PREF_ATTENUATE_OTHERS = "attenuateOthers";
    public static final Integer DEFAULT_ATTENUATE_OTHERS = 0;

    public static final String PREF_CHAT_NOTIFY = "chatNotify";
    public static final Boolean DEFAULT_CHAT_NOTIFY = true;

    public static final String PREF_USE_TTS = "useTts";
    public static final Boolean DEFAULT_USE_TTS = true;

    public static final String PREF_AUTO_RECONNECT = "autoReconnect";
    public static final Boolean DEFAULT_AUTO_RECONNECT = true;

    public static final String PREF_THEME = "theme";
    public static final String ARRAY_THEME_LIGHT = "lightDark";
    public static final String ARRAY_THEME_DARK = "dark";
    public static final String ARRAY_THEME_SOLARIZED_LIGHT = "solarizedLight";
    public static final String ARRAY_THEME_SOLARIZED_DARK = "solarizedDark";

    public static final String PREF_CERT = "certificatePath";
    public static final String PREF_CERT_PASSWORD = "certificatePassword";

    public static final String PREF_DEFAULT_USERNAME = "defaultUsername";
    public static final String DEFAULT_DEFAULT_USERNAME = "Plumble_User"; // funny var name

    public static final String PREF_FORCE_TCP = "forceTcp";
    public static final Boolean DEFAULT_FORCE_TCP = false;

    public static final String PREF_USE_TOR = "useTor";
    public static final Boolean DEFAULT_USE_TOR = false;

    public static final String PREF_DISABLE_OPUS = "disableOpus";
    public static final Boolean DEFAULT_DISABLE_OPUS = false;

    public static final String PREF_MUTED = "muted";
    public static final Boolean DEFAULT_MUTED = false;

    public static final String PREF_DEAFENED = "deafened";
    public static final Boolean DEFAULT_DEAFENED = false;

    public static final String PREF_FIRST_RUN = "firstRun";
    public static final Boolean DEFAULT_FIRST_RUN = true;

    public static final String PREF_LOAD_IMAGES = "load_images";
    public static final boolean DEFAULT_LOAD_IMAGES = true;

    public static final String PREF_FRAMES_PER_PACKET = "frames_per_packet";
    public static final int DEFAULT_FRAMES_PER_PACKET = 6;

    public static final String PREF_HALF_DUPLEX = "half_duplex";
    public static final boolean DEFAULT_HALF_DUPLEX = false;

    private final SharedPreferences preferences;

    public static Settings getInstance(Context context) {
        return new Settings(context);
    }

    private Settings(Context ctx) {
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public String getInputMethod() {
        return preferences.getString(PREF_INPUT_METHOD, ARRAY_INPUT_METHOD_VOICE);
    }

    public int getInputSampleRate() {
        return Integer.parseInt(preferences.getString(Settings.PREF_INPUT_RATE, DEFAULT_RATE));
    }

    public int getInputQuality() {
        return preferences.getInt(Settings.PREF_INPUT_QUALITY, DEFAULT_INPUT_QUALITY);
    }

    public float getAmplitudeBoostMultiplier() {
        return (float)preferences.getInt(Settings.PREF_AMPLITUDE_BOOST, DEFAULT_AMPLITUDE_BOOST)/100;
    }
    
    public float getAttenuateOthers() {
        return (float)preferences.getInt(Settings.PREF_ATTENUATE_OTHERS, DEFAULT_ATTENUATE_OTHERS)/100;
    }

    public float getDetectionThreshold() {
        return (float)preferences.getInt(PREF_THRESHOLD, DEFAULT_THRESHOLD)/100;
    }

    public int getPushToTalkKey() {
        return preferences.getInt(PREF_PUSH_KEY, DEFAULT_PUSH_KEY);
    }

    public String getHotCorner() {
        return preferences.getString(PREF_HOT_CORNER_KEY, DEFAULT_HOT_CORNER);
    }

    /**
     * @return the resource ID of the user-defined theme.
     */
    public int getTheme() {
        String theme = preferences.getString(PREF_THEME, ARRAY_THEME_LIGHT);
        if(ARRAY_THEME_LIGHT.equals(theme))
            return R.style.Theme_Plumble;
        else if(ARRAY_THEME_DARK.equals(theme))
            return R.style.Theme_Plumble_Dark;
        else if(ARRAY_THEME_SOLARIZED_LIGHT.equals(theme))
            return R.style.Theme_Plumble_Solarized_Light;
        else if(ARRAY_THEME_SOLARIZED_DARK.equals(theme))
            return R.style.Theme_Plumble_Solarized_Dark;
        return -1;
    }

    /**
     * Attempts to read the certificate from the path specified in settings.
     * @return The parsed bytes of the certificate, or null otherwise.
     */
    public byte[] getCertificate() {
        try {
            FileInputStream inputStream = new FileInputStream(preferences.getString(PREF_CERT, ""));
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            return buffer;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isUsingCertificate() {
        return preferences.contains(PREF_CERT);
    }

    public String getCertificatePassword() {
        return preferences.getString(PREF_CERT_PASSWORD, "");
    }

    public String getDefaultUsername() {
        return preferences.getString(PREF_DEFAULT_USERNAME, DEFAULT_DEFAULT_USERNAME);
    }

    public boolean isPushToTalkToggle() {
        return preferences.getBoolean(PREF_PTT_TOGGLE, DEFAULT_PTT_TOGGLE);
    }

    public boolean isPushToTalkButtonShown() {
        return !preferences.getBoolean(PREF_PUSH_BUTTON_HIDE_KEY, DEFAULT_PUSH_BUTTON_HIDE);
    }

    public boolean isChatNotifyEnabled() {
        return preferences.getBoolean(PREF_CHAT_NOTIFY, DEFAULT_CHAT_NOTIFY);
    }

    public boolean isTextToSpeechEnabled() {
        return preferences.getBoolean(PREF_USE_TTS, DEFAULT_USE_TTS);
    }

    public boolean isAutoReconnectEnabled() {
        return preferences.getBoolean(PREF_AUTO_RECONNECT, DEFAULT_AUTO_RECONNECT);
    }

    public boolean isTcpForced() {
        return preferences.getBoolean(PREF_FORCE_TCP, DEFAULT_FORCE_TCP);
    }

    public boolean isOpusDisabled() {
        return preferences.getBoolean(PREF_DISABLE_OPUS, DEFAULT_DISABLE_OPUS);
    }

    public boolean isTorEnabled() {
        return preferences.getBoolean(PREF_USE_TOR, DEFAULT_USE_TOR);
    }

    public boolean isMuted() {
        return preferences.getBoolean(PREF_MUTED, DEFAULT_MUTED);
    }

    public boolean isDeafened() {
        return preferences.getBoolean(PREF_DEAFENED, DEFAULT_DEAFENED);
    }

    public boolean isFirstRun() {
        return preferences.getBoolean(PREF_FIRST_RUN, DEFAULT_FIRST_RUN);
    }

    public boolean shouldLoadExternalImages() {
        return preferences.getBoolean(PREF_LOAD_IMAGES, DEFAULT_LOAD_IMAGES);
    }

    public void setMutedAndDeafened(boolean muted, boolean deafened) {
        Editor editor = preferences.edit();
        editor.putBoolean(PREF_MUTED, muted || deafened);
        editor.putBoolean(PREF_DEAFENED, deafened);
        editor.commit();
    }

    public void setCertificatePath(String path) {
        Editor editor = preferences.edit();
        editor.putString(PREF_CERT, path);
        editor.commit();
    }

    public void setFirstRun(boolean run) {
        preferences.edit().putBoolean(PREF_FIRST_RUN, run).commit();
    }

    public int getFramesPerPacket() {
        return preferences.getInt(PREF_FRAMES_PER_PACKET, DEFAULT_FRAMES_PER_PACKET);
    }

    public boolean isHalfDuplex() {
        return preferences.getBoolean(PREF_HALF_DUPLEX, DEFAULT_HALF_DUPLEX);
    }
}
