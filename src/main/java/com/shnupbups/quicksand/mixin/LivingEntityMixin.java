package com.shnupbups.quicksand.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.shnupbups.quicksand.block.QuicksandBlock;
import com.shnupbups.quicksand.registry.ModBlocks;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	@Shadow
	protected boolean jumping;

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "applyMovementInput(Lnet/minecraft/util/math/Vec3d;F)Lnet/minecraft/util/math/Vec3d;", at = @At("RETURN"), cancellable = true)
	public void inject_applyMovementInput(Vec3d vec3d, float f, CallbackInfoReturnable<Vec3d> cir) {
		Vec3d vec3d2 = this.getVelocity();
		if ((this.horizontalCollision || this.jumping) && (this.getBlockStateAtPos().isOf(ModBlocks.QUICKSAND))) {
			cir.setReturnValue(new Vec3d(vec3d2.x, QuicksandBlock.canWalkOnQuicksand(this) ? 0.4D : 0.1D, vec3d2.z));
		}
	}
}
