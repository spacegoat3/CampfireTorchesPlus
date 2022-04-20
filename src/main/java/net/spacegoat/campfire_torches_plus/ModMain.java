package net.spacegoat.campfire_torches_plus;

import net.fabricmc.api.ModInitializer;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModMain implements ModInitializer {
	public static final String MOD_ID = "campfire_torches_plus";

	public static final Identifier TORCH_SOUND = new Identifier(MOD_ID, "lit_torch");
	public static SoundEvent PLAYER_LIT_TORCH = new SoundEvent(TORCH_SOUND);

	@Override
	public void onInitialize() {
		Registry.register(Registry.SOUND_EVENT, TORCH_SOUND, PLAYER_LIT_TORCH);
	}
}
