package com.github.telvarost.sameoldspells.block.spell;

import com.github.telvarost.sameoldspells.Config;
import com.github.telvarost.sameoldspells.mixin.WorldAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class WaterSpell extends TemplateBlock {

    public WaterSpell(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isSideVisible(BlockView blockView, int x, int y, int z, int side) {
        if (  !Config.config.RENDER_SPELLS_CONFIG.DISABLE_ALL_SPELL_RENDERING
            && Config.config.RENDER_SPELLS_CONFIG.WATER_SPELL
        ) {
            return true;
        } else {
            return false;
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Box getBoundingBox(World world, int x, int y, int z) {
        if (  !Config.config.RENDER_SPELLS_CONFIG.DISABLE_ALL_SPELL_RENDERING
            && Config.config.RENDER_SPELLS_CONFIG.WATER_SPELL
        ) {
            return Box.createCached((double)x + this.minX, (double)y + this.minY, (double)z + this.minZ, (double)x + this.maxX, (double)y + this.maxY, (double)z + this.maxZ);
        } else {
            return Box.createCached(0, 0, 0, 0, 0, 0);
        }
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public boolean isSolidBlock(BlockView blockView, int x, int y, int z, int i) {
        return false;
    }

    @Override
    public Box getCollisionShape(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public void onEntityCollision(World world, int x, int y, int z, Entity entity) {
        if (null != entity && !(entity instanceof Particle)) {
            if (null != ((WorldAccessor) world).getProperties()) {
                world.setBlock(x, y, z, 0);
                world.method_215(x, y, z, 0);
                ((WorldAccessor) world).getProperties().setThundering(false);
                ((WorldAccessor) world).getProperties().setRaining(true);
            }
        }
    }
}