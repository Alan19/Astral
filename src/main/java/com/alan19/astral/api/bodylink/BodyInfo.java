package com.alan19.astral.api.bodylink;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.UUID;

public class BodyInfo implements INBTSerializable<CompoundNBT> {
    private float health;
    private BlockPos pos;
    private boolean alive;
    private RegistryKey<World> dimensionType;
    private UUID bodyId;

    public BodyInfo(CompoundNBT nbt) {
        deserializeNBT(nbt);
    }

    public BodyInfo(float health, BlockPos pos, boolean alive, RegistryKey<World> dimensionType, UUID bodyId) {
        this.health = health;
        this.pos = pos;
        this.alive = alive;
        this.dimensionType = dimensionType;
        this.bodyId = bodyId;
    }

    public UUID getBodyId() {
        return bodyId;
    }

    public void setBodyId(UUID bodyId) {
        this.bodyId = bodyId;
    }

    public RegistryKey<World> getDimensionType() {
        return dimensionType;
    }

    public void setDimensionType(RegistryKey<World> dimensionKey) {
        this.dimensionType = dimensionKey;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putFloat("health", health);
        nbt.putBoolean("alive", alive);
        nbt.put("pos", NBTUtil.writeBlockPos(pos));
        nbt.putString("dimension", dimensionType.getRegistryName().toString());
        nbt.put("bodyID", NBTUtil.func_240626_a_(bodyId));
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        health = nbt.getFloat("health");
        alive = nbt.getBoolean("alive");
        pos = NBTUtil.readBlockPos(nbt.getCompound("pos"));
        dimensionType = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(nbt.getString("dimension")));
        bodyId = nbt.getUniqueId("bodyID");
    }
}
