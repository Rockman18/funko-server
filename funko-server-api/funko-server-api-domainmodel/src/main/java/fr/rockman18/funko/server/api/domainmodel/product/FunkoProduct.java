package fr.rockman18.funko.server.api.domainmodel.product;

// Generated 21 oct. 2015 16:04:04 by Hibernate Tools 4.3.1

import javax.xml.bind.annotation.XmlRootElement;

import fr.rockman18.funko.server.api.domainmodel.FunkoObject;
import fr.rockman18.funko.server.api.domainmodel.franchise.FunkoFranchise;
import fr.rockman18.funko.server.api.domainmodel.line.FunkoLine;

/**
 * Patch generated by hbm2java
 */
@XmlRootElement
public class FunkoProduct extends FunkoObject {

    private static final long serialVersionUID = 4795387256238654686L;
    private int number;
    private int variantMask;
    private String releaseDate;
    private Boolean released;
    private String exclusiveRetailer;
    private int editionSize;
    private float estimatedQuotation;
    private FunkoLine line;
    private FunkoFranchise franchise;

    public FunkoProduct() {
	super();
    }

    public FunkoProduct(int id) {
	super(id);
    }

    public FunkoProduct(int id, String label, String url, String img,
	    int number, int variantMask, String releaseDate, Boolean released,
	    String exclusiveRetailer, int editionSize,
	    float estimatedQuotation, FunkoLine line, FunkoFranchise franchise) {
	super(id, label, url, img);
	this.number = number;
	this.variantMask = variantMask;
	this.setReleaseDate(releaseDate);
	this.setReleased(released);
	this.exclusiveRetailer = exclusiveRetailer;
	this.editionSize = editionSize;
	this.estimatedQuotation = estimatedQuotation;
	this.line = line;
	this.franchise = franchise;
    }

    public int getNumber() {
	return number;
    }

    public void setNumber(int number) {
	this.number = number;
    }

    public int getVariantMask() {
	return variantMask;
    }

    public void setVariantMask(int variantMask) {
	this.variantMask = variantMask;
    }

    public String getReleaseDate() {
	return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
	this.releaseDate = releaseDate;
    }

    public Boolean getReleased() {
	return released;
    }

    public void setReleased(Boolean released) {
	this.released = released;
    }

    public String getExclusiveRetailer() {
	return exclusiveRetailer;
    }

    public void setExclusiveRetailer(String exclusiveRetailer) {
	this.exclusiveRetailer = exclusiveRetailer;
    }

    public int getEditionSize() {
	return editionSize;
    }

    public void setEditionSize(int editionSize) {
	this.editionSize = editionSize;
    }

    public float getEstimatedQuotation() {
	return estimatedQuotation;
    }

    public void setEstimatedQuotation(float estimatedQuotation) {
	this.estimatedQuotation = estimatedQuotation;
    }

    public FunkoLine getLine() {
	return this.line;
    }

    public void setLine(FunkoLine line) {
	this.line = line;
    }

    public FunkoFranchise getFranchise() {
	return this.franchise;
    }

    public void setFranchise(FunkoFranchise franchise) {
	this.franchise = franchise;
    }

    @Override
    public String toString() {
	return String.format("[%s][#%d] %s", line, number, getLabel());
    }
}
