package wvv.particleshield.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.EntityFX;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import wvv.particleshield.config.ConfigHandler;

import java.lang.reflect.Field;
import java.util.List;

public class ParticleRenderEventHandler {

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }

        if (!ConfigHandler.isShieldActive) {
            return;
        }

        final Minecraft mc = Minecraft.getMinecraft();
        if (mc == null || mc.theWorld == null) {
            return;
        }

        final EntityPlayerSP player = mc.thePlayer;
        if (player == null) {
            return;
        }

        final float partialTicks = event.renderTickTime;

        // Get player's eye position with interpolation
        final double px = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
        final double py = player.prevPosY + (player.posY - player.prevPosY) * partialTicks + player.getEyeHeight();
        final double pz = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;

        final double minDist = Math.max(0, ConfigHandler.shieldSize);
        final double minDistSq = minDist * minDist;

        // Iterate EffectRenderer internal particle lists via reflection to cover all layers/types
        Object effectRenderer = mc.effectRenderer;
        if (effectRenderer == null) {
            return;
        }

        Field fxLayersField = null;
        try {
            fxLayersField = ReflectionHelper.findField(effectRenderer.getClass(), "fxLayers", "field_78876_b");
        } catch (ReflectionHelper.UnableToFindFieldException ignored) {
        }

        if (fxLayersField != null) {
            try {
                fxLayersField.setAccessible(true);
                Object layersObj = fxLayersField.get(effectRenderer);

                if (layersObj instanceof Object[]) {
                    Object[] outer = (Object[]) layersObj;
                    for (Object outerElem : outer) {
                        if (outerElem == null) continue;
                        if (outerElem instanceof List) {
                            // Single list case
                            @SuppressWarnings("unchecked")
                            List<Object> list = (List<Object>) outerElem;
                            cullParticlesFromList(list, px, py, pz, partialTicks, minDistSq);
                        } else if (outerElem instanceof Object[]) {
                            // Likely List[] inner array
                            Object[] inner = (Object[]) outerElem;
                            for (Object innerElem : inner) {
                                if (innerElem instanceof List) {
                                    @SuppressWarnings("unchecked")
                                    List<Object> list = (List<Object>) innerElem;
                                    cullParticlesFromList(list, px, py, pz, partialTicks, minDistSq);
                                }
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                // Swallow to avoid chat spam/crashes in case of mapping differences
            }
        }
    }

    private static void cullParticlesFromList(List<Object> list, double px, double py, double pz, float partialTicks, double minDistSq) {
        if (list == null || list.isEmpty()) return;
        for (int i = list.size() - 1; i >= 0; i--) {
            Object obj = list.get(i);
            if (!(obj instanceof EntityFX)) continue;
            EntityFX fx = (EntityFX) obj;

            // Interpolated particle position
            double x = fx.prevPosX + (fx.posX - fx.prevPosX) * partialTicks;
            double y = fx.prevPosY + (fx.posY - fx.prevPosY) * partialTicks;
            double z = fx.prevPosZ + (fx.posZ - fx.prevPosZ) * partialTicks;

            double dx = x - px;
            double dy = y - py;
            double dz = z - pz;
            double distSq = dx * dx + dy * dy + dz * dz;

            if (distSq <= minDistSq) {
                // Mark dead and remove immediately to ensure it doesn't render this frame
                fx.setDead();
                list.remove(i);
            }
        }
    }
}
