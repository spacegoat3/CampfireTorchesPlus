package net.spacegoat.campfire_torches_plus;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.spacegoat.campfire_torches_plus.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin extends Block {
    public CampfireBlockMixin(Settings settings){
        super(settings);
    }
    @Inject(method = "onUse", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/player/PlayerEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onUseMixin(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> info, BlockEntity blockEntity){
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (!world.isClient && item == Items.STICK && state.isOf(Blocks.CAMPFIRE) && ModConfig.getConfig().CampfireTorch.enableCampfireTorch){
            player.setStackInHand(hand, new ItemStack(Items.TORCH));
            player.incrementStat(Stats.INTERACT_WITH_CAMPFIRE);
            player.playSound(SoundEvents.ITEM_FIRECHARGE_USE, ModConfig.getConfig().CampfireTorch.campfireTorchFireSoundEffectLevel, ModConfig.getConfig().CampfireTorch.campfireTorchFireSoundEffectLevel);
            info.setReturnValue(ActionResult.SUCCESS);
        }
        if (!world.isClient && item == Items.STICK && state.isOf(Blocks.SOUL_CAMPFIRE) && ModConfig.getConfig().SoulCampfireTorch.enableSoulCampfireTorch){
            player.setStackInHand(hand, new ItemStack(Items.TORCH));
            player.incrementStat(Stats.INTERACT_WITH_CAMPFIRE);
            player.playSound(SoundEvents.ITEM_FIRECHARGE_USE, ModConfig.getConfig().SoulCampfireTorch.soulCampfireTorchFireSoundEffectLevel, ModConfig.getConfig().SoulCampfireTorch.soulCampfireTorchFireSoundEffectLevel);
            player.playSound(SoundEvents.BLOCK_SOUL_SAND_PLACE, ModConfig.getConfig().SoulCampfireTorch.soulCampfireTorchSoulSandSoundEffectLevel,  ModConfig.getConfig().SoulCampfireTorch.soulCampfireTorchSoulSandSoundEffectLevel);
            info.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
