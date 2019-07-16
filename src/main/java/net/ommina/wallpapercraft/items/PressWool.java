package net.ommina.wallpapercraft.items;

public class PressWool extends PressPattern {

    public static final String VARIANT = "wool";
    public static final String NAME = "press" + VARIANT;

    public PressWool() {
        super( VARIANT );
        setRegistryName( NAME );
    }

}
