package org.lineageos.settings.device.dac.utils;

import android.media.AudioSystem;
import android.os.SystemProperties;
import android.util.Log;

import org.lineageos.hardware.util.FileUtils;

public class QuadDAC {

    private static final String TAG = "QuadDAC";

    private QuadDAC() {}

    public static void enable()
    {
        int digital_filter = getDigitalFilter();
        int sound_preset = getSoundPreset();
        int left_balance = getLeftBalance();
        int right_balance = getRightBalance();
        int mode = getDACMode();
        int avc_vol = getAVCVolume();
        SystemProperties.set(Constants.PROPERTY_HIFI_DAC_NORMAL, "true");
        setDACMode(mode);
        setLeftBalance(left_balance);
        setRightBalance(right_balance);
        setDigitalFilter(digital_filter);
        setSoundPreset(sound_preset);
        setAVCVolume(avc_vol);
    }

    public static void disable()
    {
        SystemProperties.set(Constants.PROPERTY_HIFI_DAC_NORMAL, "false");
    }

    public static void setHifiDACdop(int dop)
    {
        AudioSystem.setParameters(Constants.SET_HIFI_DAC_DOP_COMMAND + dop);
        SystemProperties.set(Constants.PROPERTY_HIFI_DAC_DOP, Integer.toString(dop));
    }

    public static int getHifiDACdop()
    {
        return SystemProperties.getInt(Constants.PROPERTY_HIFI_DAC_DOP, 0);
    }

    public static void setDACMode(int mode)
    {
        switch(mode)
        {
        case 0:
            SystemProperties.set(Constants.PROPERTY_HIFI_DAC_NORMAL, "true");
            SystemProperties.set(Constants.PROPERTY_HIFI_DAC_ADVANCED, "false"); //Not the cleanest I know, I'll work on a cleaner solution later.
            SystemProperties.set(Constants.PROPERTY_HIFI_DAC_AUX, "false");
            break;
        case 1:
            SystemProperties.set(Constants.PROPERTY_HIFI_DAC_NORMAL, "false");
            SystemProperties.set(Constants.PROPERTY_HIFI_DAC_ADVANCED, "true");
            SystemProperties.set(Constants.PROPERTY_HIFI_DAC_AUX, "false");
            break;
        case 2:
            SystemProperties.set(Constants.PROPERTY_HIFI_DAC_NORMAL, "false");
            SystemProperties.set(Constants.PROPERTY_HIFI_DAC_ADVANCED, "false");
            SystemProperties.set(Constants.PROPERTY_HIFI_DAC_AUX, "true");
            break;
        default: 
            return;
        }
        SystemProperties.set(Constants.PROPERTY_HIFI_DAC_MODE, Integer.toString(mode));
    }

    public static int getDACMode()
    {
        return SystemProperties.getInt(Constants.PROPERTY_HIFI_DAC_MODE, 0);
    }

    public static void setAVCVolume(int avc_volume)
    {
        FileUtils.writeLine(Constants.AVC_VOLUME_SYSFS, (avc_volume * -1) + "");
        SystemProperties.set(Constants.PROPERTY_HIFI_DAC_AVC_VOLUME, Integer.toString(avc_volume));
    }

    public static int getAVCVolume()
    {
        return SystemProperties.getInt(Constants.PROPERTY_HIFI_DAC_AVC_VOLUME, 0);
    }

    public static void setMasterVolume(int master_volume)
    {
        FileUtils.writeLine(Constants.MASTER_VOLUME_SYSFS, (master_volume * -1) + "");
        SystemProperties.set(Constants.PROPERTY_HIFI_DAC_MASTER_VOLUME, Integer.toString(master_volume));
    }

    public static int getMasterVolume()
    {
        return SystemProperties.getInt(Constants.PROPERTY_HIFI_DAC_MASTER_VOLUME, 0);
    }

    public static void setDigitalFilter(int filter)
    {
        AudioSystem.setParameters(Constants.SET_DIGITAL_FILTER_COMMAND + filter);
        //FileUtils.writeLine(Constants.ESS_FILTER_SYSFS, filter + "");
        SystemProperties.set(Constants.PROPERTY_DIGITAL_FILTER, Integer.toString(filter));
    }

    public static int getDigitalFilter()
    {
        return SystemProperties.getInt(Constants.PROPERTY_DIGITAL_FILTER, 0);
    }

    public static void setSoundPreset(int preset)
    {
        AudioSystem.setParameters(Constants.SET_SOUND_PRESET_COMMAND + preset);
        SystemProperties.set(Constants.PROPERTY_SOUND_PRESET, Integer.toString(preset));
    }

    public static int getSoundPreset()
    {
        return SystemProperties.getInt(Constants.PROPERTY_SOUND_PRESET, 0);
    }

    public static void setLeftBalance(int balance)
    {
        AudioSystem.setParameters(Constants.SET_LEFT_BALANCE_COMMAND + balance);
        SystemProperties.set(Constants.PROPERTY_LEFT_BALANCE, Integer.toString(balance));
    }

    public static int getLeftBalance()
    {
        return SystemProperties.getInt(Constants.PROPERTY_LEFT_BALANCE, 0);
    }

    public static void setRightBalance(int balance)
    {
        AudioSystem.setParameters(Constants.SET_RIGHT_BALANCE_COMMAND + balance);
        SystemProperties.set(Constants.PROPERTY_RIGHT_BALANCE, Integer.toString(balance));
    }

    public static int getRightBalance()
    {
        return SystemProperties.getInt(Constants.PROPERTY_RIGHT_BALANCE, 0);
    }

    public static boolean isEnabled()
    {
        String hifi_dac = SystemProperties.get(Constants.PROPERTY_HIFI_DAC_NORMAL);
        return hifi_dac.equals("ON");
    }

}
