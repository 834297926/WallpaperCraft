package net.ommina.wallpapercraft.recipes;

import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.Tag;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.ommina.wallpapercraft.Wallpapercraft;
import net.ommina.wallpapercraft.blocks.DecorativeBlock;
import net.ommina.wallpapercraft.blocks.ModBlocks;
import net.ommina.wallpapercraft.items.ModItems;
import net.ommina.wallpapercraft.items.Press;
import net.ommina.wallpapercraft.items.PressBlank;
import net.ommina.wallpapercraft.tags.Tags;

import javax.annotation.Nonnull;

public class PressCraftingRecipe implements ICraftingRecipe {

    public static final ResourceLocation NAME = Wallpapercraft.getId( "presscrafting" );
    public static final Serializer SERIALIZER = new Serializer();

    private ResourceLocation id;
    private Press press;
    private Tag<Item> tags;
    private boolean valid = true;

    public PressCraftingRecipe( ResourceLocation id ) {
        this.id = id;
    }

    @Override
    public boolean matches( @Nonnull final CraftingInventory inv, @Nonnull final World world ) {

        int i = 0;
        int j = 0;

        for( int k = 0; k < inv.getSizeInventory(); k++ ) {

            final ItemStack stack = inv.getStackInSlot( k );

            if( !stack.isEmpty() ) {

                if( stack.getItem() == this.press ) {
                    i++;
                } else {

                    if( !this.tags.contains( stack.getItem() ) ) {
                        return false;
                    }

                    j++;

                }

                if( j > 1 || i > 1 ) {
                    return false;
                }

            }
        }

        return i == 1 && j == 1;
    }

    @Override
    @Nonnull
    public ItemStack getCraftingResult( @Nonnull final CraftingInventory inv ) {

        for( int i = 0; i < inv.getSizeInventory(); i++ ) {

            final ItemStack stack = inv.getStackInSlot( i );

            if( !stack.isEmpty() && this.tags.contains( stack.getItem() ) ) {

                final DecorativeBlock block = ModBlocks.BLOCKS.get( stack.getItem().getRegistryName().getPath() );

                if( block == null )
                    return ItemStack.EMPTY;

                final Item item = ModItems.ITEMS.get( this.press.getVariant() + block.getColour() + block.getSuffix() );

                if( item == null )
                    return ItemStack.EMPTY;

                return new ItemStack( item );

            }

        }

        return ItemStack.EMPTY;

    }

    @Nonnull
    @Override
    public NonNullList<ItemStack> getRemainingItems( final CraftingInventory inv ) {

        final NonNullList<ItemStack> list = NonNullList.withSize( inv.getSizeInventory(), ItemStack.EMPTY );

        for( int i = 0; i < inv.getSizeInventory(); i++ ) {

            final ItemStack stack = inv.getStackInSlot( i );

            if( stack.getItem() instanceof Press ) {

                final ItemStack stack1 = stack.copy();
                stack1.setCount( 1 );
                list.set( i, stack1 );
                break;

            }

        }

        return list;
    }


    @Override
    public boolean canFit( final int width, final int height ) {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isDynamic() {
        return true; // Don't show in recipe book
    }

    @Nonnull
    @Override
    public String getGroup() {
        return Wallpapercraft.MODID;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static final class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<PressCraftingRecipe> {

        private Serializer() {

        }

        @Nonnull
        @Override
        public PressCraftingRecipe read( @Nonnull final ResourceLocation recipeId, @Nonnull final JsonObject json ) {

            final PressCraftingRecipe recipe = new PressCraftingRecipe( recipeId );


            final Item press = ForgeRegistries.ITEMS.getValue( Wallpapercraft.getId( JSONUtils.getString( json, "press", PressBlank.NAME ) ) );

            if( press instanceof Press )
                recipe.press = (Press) press;
            else
                recipe.valid = false;


            final Tag<Item> tags = Tags.TAGS.get( JSONUtils.getString( json, "tag", "solid" ) );

            if( tags != null )
                recipe.tags = tags;
            else
                recipe.valid = false;

            return recipe;

        }

        @Override
        public PressCraftingRecipe read( final ResourceLocation recipeId, final PacketBuffer buffer ) {
            return new PressCraftingRecipe( recipeId );
        }

        @Override
        public void write( PacketBuffer buffer, PressCraftingRecipe recipe ) {
        }

    }

}