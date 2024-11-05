package net.jsona.ytwpn.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireBlock.class)
public class MixinFireBlock {

    @Inject(method = "onBlockAdded", at = @At("HEAD"), cancellable = true)
    private void onFireBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        // Replace fire with air
        if (state.getBlock() == Blocks.FIRE) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            ci.cancel(); // Prevents further processing of fire block placement
        }
    }

}