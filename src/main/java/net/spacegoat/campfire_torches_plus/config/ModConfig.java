package net.spacegoat.campfire_torches_plus.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
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
        @Comment("You can right-click a Campfire with a stick to make a Torch.")
        public boolean enableCampfireTorch = true;
        @Comment("The amount of sticks you will need to make a Torch.")
        public int stickCost = 1;
        public boolean spawnParticles = true;
        @Comment("Plays a fire sound effect whenever you lit a Torch.")
        public boolean playCustomSoundEffect = true;
    }
    public static class SoulCampfireTorch{
        @Comment("You can right-click a Soul Campfire with a stick to make a Soul Torch.")
        public boolean enableSoulCampfireTorch = true;
        @Comment("The amount of sticks you will need to make a Soul Torch.")
        public int stickCost = 1;
        public boolean spawnParticles = true;
        @Comment("Plays a fire sound effect whenever you lit a Soul Torch.")
        public boolean playCustomSoundEffect = true;
        @Comment("Plays Soul Sand Block's sound effect whenever you lit a Soul Torch.")
        public boolean playSoulSandSoundEffect = true;
    }
}
