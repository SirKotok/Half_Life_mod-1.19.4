package net.sirkotok.half_life_mod.entity.mob_normal.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.level.Level;

public class Barnacle extends Shulker {

    public Barnacle(EntityType<? extends Barnacle> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10D)
                .add(Attributes.ATTACK_DAMAGE, 3f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 0f)
                .add(Attributes.MOVEMENT_SPEED, 0f).build();
    }






}
