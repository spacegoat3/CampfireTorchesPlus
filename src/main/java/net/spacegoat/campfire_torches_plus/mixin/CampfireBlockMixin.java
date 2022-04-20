package net.spacegoat.campfire_torches_plus.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.spacegoat.campfire_torches_plus.CTPMain;
import net.spacegoat.campfire_torches_plus.config.CTPConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin extends Block {
    @Shadow @Final public static BooleanProperty LIT;

    public CampfireBlockMixin(Settings settings){
        super(settings);
    }
    @Inject(method = "onUse", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/player/PlayerEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onUseMixin(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> info, BlockEntity blockEntity){
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (item == Items.STICK && state.isOf(Blocks.CAMPFIRE) && state.get(LIT) && CTPConfig.getConfig().CampfireTorch.enableCampfireTorch){
            if (CTPConfig.getConfig().CampfireTorch.playCustomSoundEffect) {
                world.playSound(player, pos, CTPMain.PLAYER_LIT_TORCH, SoundCategory.PLAYERS, 1, 1);
            }
            if (!player.isCreative()){
                itemStack.decrement(CTPConfig.getConfig().CampfireTorch.stickCost);
            }
            player.getInventory().insertStack(new ItemStack(Items.TORCH));
            player.incrementStat(Stats.INTERACT_WITH_CAMPFIRE);
            info.setReturnValue(ActionResult.SUCCESS);
        }
        if (item == Items.STICK && state.isOf(Blocks.SOUL_CAMPFIRE) && state.get(LIT) && CTPConfig.getConfig().SoulCampfireTorch.enableSoulCampfireTorch){
            if (!player.isCreative()){
                itemStack.decrement(CTPConfig.getConfig().SoulCampfireTorch.stickCost);
            }
            player.getInventory().insertStack(new ItemStack(Items.SOUL_TORCH));
            player.incrementStat(Stats.INTERACT_WITH_CAMPFIRE);
            if (CTPConfig.getConfig().SoulCampfireTorch.playCustomSoundEffect) {
                world.playSound(player, pos, CTPMain.PLAYER_LIT_TORCH, SoundCategory.PLAYERS, 1, 1);
            }
            if (CTPConfig.getConfig().SoulCampfireTorch.playSoulSandSoundEffect) {
                world.playSound(player, pos, SoundEvents.BLOCK_SOUL_SAND_PLACE, SoundCategory.PLAYERS, 0.5f, 0.5f);
            }
            info.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
