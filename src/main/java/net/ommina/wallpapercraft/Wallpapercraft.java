package net.ommina.wallpapercraft;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.ommina.wallpapercraft.client.ClientProxy;
import net.ommina.wallpapercraft.network.Network;
import net.ommina.wallpapercraft.recipes.PressCraftingRecipe;
import net.ommina.wallpapercraft.server.ServerProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod( Wallpapercraft.MODID )
public class Wallpapercraft {

    public static final IProxy PROXY = DistExecutor.runForDist( () -> ClientProxy::new, () -> ServerProxy::new );
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "wallpapercraft";

    public static final ItemGroup TAB = new CreativeTab();

    public Wallpapercraft() {

        MinecraftForge.EVENT_BUS.register( this );

        FMLJavaModLoadingContext.get().getModEventBus().addListener( this::setup );

    }

    @Mod.EventBusSubscriber( bus = Mod.EventBusSubscriber.Bus.MOD )
    public static class RegistryEvents {

        @SubscribeEvent
        public static void registerSerials( RegistryEvent.Register<IRecipeSerializer<?>> event ) {
            IRecipeSerializer.register( PressCraftingRecipe.NAME.toString(), PressCraftingRecipe.SERIALIZER );
        }

    }

    public static ResourceLocation getId( String path ) {
        return new ResourceLocation( MODID, path );
    }

    private void setup( final FMLCommonSetupEvent event ) {
        Network.init();
    }

}