package net.ommina.wallpapercraft.items;

public class PressFancyTiles extends Press {

    public static final String VARIANT = "fancytiles";
    public static final String NAME = "press" + VARIANT;

    public PressFancyTiles() {
        super( VARIANT );
        setRegistryName( NAME );

    }

}