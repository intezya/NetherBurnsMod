package com.intezya.harderthanhard.mixin.food;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.FoodComponents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Mixin(FoodComponents.class)
public class FoodComponentsMixin {
    @Unique
    private static final Logger logger = LoggerFactory.getLogger(FoodComponentsMixin.class);

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void nerfFood(CallbackInfo ci) {
        Field[] fields = FoodComponents.class.getFields();

        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers()) || field.getType() != FoodComponent.class) {
                continue;
            }

            try {
                field.setAccessible(true);

                FoodComponent original = (FoodComponent) field.get(null);
                if (original == null) {
                    continue;
                }

                if (original == FoodComponents.GOLDEN_APPLE || original == FoodComponents.ENCHANTED_GOLDEN_APPLE) {
                    continue;
                }

                int newNutrition = Math.max(1, (int) Math.floor(original.nutrition() / 2f));
                float oldSaturationModifier = originalModifierFromFood(original);
                float newSaturationModifier = Math.max(0.4f, oldSaturationModifier) / 2;

                FoodComponent.Builder nerfedFoodBuilder = (new FoodComponent.Builder())
                    .nutrition(newNutrition)
                    .saturationModifier(newSaturationModifier);

                if (original.canAlwaysEat()) {
                    nerfedFoodBuilder = nerfedFoodBuilder.alwaysEdible();
                }

                FoodComponent nerfed = nerfedFoodBuilder.build();

                forceSetStaticFinal(field, nerfed);
            } catch (Throwable e) {
                logger.warn("failed to nerf food: ", e);
            }
        }

    }

    @Unique
    @SuppressWarnings("deprecation")
    private static void forceSetStaticFinal(Field field, Object value) throws Exception {
        field.setAccessible(true);

        Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);

        Object staticFieldBase = unsafe.staticFieldBase(field);
        long staticFieldOffset = unsafe.staticFieldOffset(field);

        unsafe.putObject(staticFieldBase, staticFieldOffset, value);
    }

    @Unique
    private static float originalModifierFromFood(FoodComponent foodComponent) {
        return foodComponent.saturation() / foodComponent.nutrition() / 2;
    }
}
