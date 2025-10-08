package net.leahperson.proficientmod.nbt;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.leahperson.proficientmod.ProficientMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public record Quality(ResourceLocation type, int level) {
    public static final Quality NONE = new Quality(ProficientMod.location("none"), 0);
    public static final Quality PLAYER_PLACED = new Quality(ProficientMod.location("none"), -1);


}
