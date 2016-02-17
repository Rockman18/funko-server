package fr.rockman18.funko.server.api.domainmodel.product;

import java.util.HashSet;
import java.util.Set;

public enum FunkoVariant {
    BW		(0b1, "Black & White"),
    GITD	(0b10, "Glow in the Dark"),
    CLEAR	(0b100, "Clear/Transparent"),
    VARIANT	(0b1000, "Color/Paint Variant"),
    FLOCKED	(0b10000, "Flocked"),
    GLITTER	(0b100000, "Glitter"),
    METALLIC	(0b1000000, "Metallic"),
    PATINA	(0b10000000, "Patina"),
    CHASE	(0b100000000, "Chase"),
    VAULTED	(0b1000000000,"Vaulted");

    private int _value;
    private String _label;

    FunkoVariant(int value, String label) {
	_value = value;
	_label = label;
    }

    public int getValue() {
	return _value;
    }

    public String getLabel() {
	return _label;
    }

    public static Set<FunkoVariant> parseMask(int val) {
	Set<FunkoVariant> variantList = new HashSet<FunkoVariant>();
	for (FunkoVariant variant : values()) {
	    if ((val & variant.getValue()) != 0)
		variantList.add(variant);
	}
	return variantList;
    }

    public static int generateMask(Set<FunkoVariant> variantList) {
	int val = 0;
	for (FunkoVariant variant : variantList) {
	    val += variant.getValue();
	}
	return val;
    }
}