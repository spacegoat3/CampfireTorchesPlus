package net.spacegoat.campfire_torches_plus.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.spacegoat.campfire_torches_plus.ModMain;

@Config(name = ModMain.MOD_ID)
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.Excluded
    private transient static boolean registered = false;
    public static synchronized ModConfig getConfig() {
        if (!registered) {
            AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
            registered = true;
        }
        return AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
    @ConfigEntry.Gui.TransitiveObject
    public CampfireTorch CampfireTorch = new CampfireTorch();
    @ConfigEntry.Gui.TransitiveObject
    @ConfigEntry.Category("soul_campfire_torch")
    public SoulCampfireTorch SoulCampfireTorch = new SoulCampfireTorch();
    public static class CampfireTorch{
        public boolean enableCampfireTorch = true;
        public float campfireTorchFireSoundEffectLevel = 1;
    }
    public static class SoulCampfireTorch{
        public boolean enableSoulCampfireTorch = true;
        public float soulCampfireTorchFireSoundEffectLevel = 1;
        public float soulCampfireTorchSoulSandSoundEffectLevel = 0.5f;
    }
}
