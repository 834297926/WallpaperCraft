package net.ommina.wallpapercraft.items;

public class PressColouredBrick extends PressPattern {

    public static final String VARIANT = "colouredbrick";
    public static final String NAME = "press" + VARIANT;

    public PressColouredBrick() {
        super( VARIANT );
        setRegistryName( NAME );

    }

}
