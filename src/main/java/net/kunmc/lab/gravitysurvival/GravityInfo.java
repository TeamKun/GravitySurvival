package net.kunmc.lab.gravitysurvival;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;

import java.util.UUID;

public class GravityInfo extends BossInfo {
    public GravityInfo(UUID uniqueIdIn, ITextComponent nameIn, Color colorIn, Overlay overlayIn, float percent) {
        super(uniqueIdIn, nameIn, colorIn, overlayIn);
        this.percent = percent;
    }

}
