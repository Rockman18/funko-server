package fr.rockman18.funko.server.engine.service.synchronizer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.rockman18.funko.server.api.domainmodel.FunkoObject;
import fr.rockman18.funko.server.api.domainmodel.franchise.FunkoFranchise;
import fr.rockman18.funko.server.api.domainmodel.line.FunkoLine;
import fr.rockman18.funko.server.api.domainmodel.product.FunkoProduct;
import fr.rockman18.funko.server.engine.service.AbstractBaseInternalFunkoService;
import fr.rockman18.funko.server.engine.service.HtmlParser;

public class FunkoSynchronizerImpl extends AbstractBaseInternalFunkoService implements FunkoSynchronizer {

    protected HtmlParser htmlParser;
    protected FunkoObject parent;

    Pattern pattern = Pattern
	    .compile("^(\\#([0-9]+) *\n<br>)?" + "(Released ([a-zA-Z ]*[0-9]{4}) *\n<br>)?" + "(Excl. to (.*) *\n<br>)?"
		    + "(Edition Size: ([0-9,]+|[Ll]imited) *\n<br>)?" + "(Due ([a-zA-Z ]*[0-9]{4}) *\n<br>)?"
		    + "(Rarity: (1[/:][0-9]+) *\n<br>)?" + "(([vV]aulted) *\n<br>)?" + "(([rR]etired) *\n<br>)?");

    public void setHtmlParser(HtmlParser htmlParser) {
	this.htmlParser = htmlParser;
    }

    public void setParent(FunkoObject parent) {
	this.parent = parent;
    }

    @Override
    public Set<FunkoObject> call() throws Exception {
	Set<FunkoObject> sfo = new HashSet<>();
	long startTimeMillis, endTimeMillis;
	startTimeMillis = System.currentTimeMillis();
	logger.info("Starting Thread.");
	if (parent == null) {
	    logger.info("No parent => New discovering");
	    sfo.addAll(discoverFunkoLines());
	    sfo.addAll(discoverFunkoFranchises());
	} else if (parent instanceof FunkoLine) {
	    logger.info("Discovering products from line [{}]", ((FunkoLine) parent).getUrl());
	    sfo.addAll(discoverProducts((FunkoLine) parent, 1));
	} else if (parent instanceof FunkoFranchise) {
	    logger.warn("Discovering Product from Franchise is not yet implemented.");
	} else {
	    logger.error("What kind of discovering is it ???");
	}
	endTimeMillis = System.currentTimeMillis();
	logger.info("Ending Thread. Processing time : {} ms", endTimeMillis - startTimeMillis);
	return sfo;
    }

    protected void populateProduct(FunkoProduct funkoProduct, Element divItemRow) {
	if (!divItemRow.tagName().equals("div") || !divItemRow.hasClass("itemrow")) {
	    logger.error("Element sent is not a <div class=\"itemrow\">");
	} else {
	    logger.debug("<----- New Product ----->");

	    Element aName = divItemRow.select("a").first();
	    Element img = divItemRow.select("img.itemlistimg-ext").first();
	    Element quotation = divItemRow.getElementsByAttributeValue("style", "position: relative; bottom: 19px; z-index: 1; margin-bottom: -25px; padding: 5px; background-color: #d6fed8; color: green; font-weight: bold; font-size: 20px; opacity: 0.8; text-align: center; border: 1px solid green; border-radius: 8px;").first();
	    Element textContainer = divItemRow.select("div.textcontainer").first();
	    logger.debug(textContainer.html());
	    logger.debug(quotation.text());

	    funkoProduct.setId(Integer.parseInt(divItemRow.id().substring(1)));
	    logger.debug("Id : {}", funkoProduct.getId());
	    funkoProduct.setLabel(aName.text());
	    logger.debug("Label : {}", funkoProduct.getLabel());
	    funkoProduct.setUrl(aName.absUrl("href"));
	    logger.debug("Url : {}", funkoProduct.getUrl());
	    funkoProduct.setImg(img.absUrl("src"));
	    logger.debug("Img : {}", funkoProduct.getImg());

	    Matcher matcher = pattern.matcher(textContainer.html());
	    if (matcher.find()) {
		if (matcher.group(2) != null) {
		    funkoProduct.setNumber(Integer.parseInt(matcher.group(2)));
		    logger.debug("# : {}", funkoProduct.getNumber());
		}
		if (matcher.group(4) != null) {
		    funkoProduct.setReleaseDate(matcher.group(4));
		    funkoProduct.setReleased(true);
		    logger.debug("Released : {}", funkoProduct.getReleaseDate());
		}
		if (matcher.group(6) != null) {
		    funkoProduct.setExclusiveRetailer(matcher.group(6));
		    logger.debug("Excl. to : {}", funkoProduct.getExclusiveRetailer());
		}
		if (matcher.group(8) != null) {
		    try {
			funkoProduct.setEditionSize(Integer.parseInt(matcher.group(8)));
		    } catch (NumberFormatException e) {
			funkoProduct.setEditionSize(-1);
		    }
		    logger.debug("Edition size : {}", funkoProduct.getEditionSize());
		}
		if (matcher.group(10) != null) {
		    funkoProduct.setReleaseDate(matcher.group(10));
		    funkoProduct.setReleased(false);
		    logger.debug("Due : {}", funkoProduct.getReleaseDate());
		}
		if (matcher.group(12) != null) {
		    logger.debug("Rarity : {}", matcher.group(12));
		}
		if (matcher.group(14) != null) {
		    logger.debug("Vaulted : {}", matcher.group(14));
		}
		if (matcher.group(16) != null) {
		    logger.debug("Retired : {}", matcher.group(16));
		}
	    } else {
		logger.warn("Can't extract data for this product.");
		logger.debug("******************************");
		logger.debug("******************************");
		logger.debug(textContainer.html());
		logger.debug("******************************");
		logger.debug("******************************");
	    }
	    logger.info(funkoProduct.toString());
	}
    }

    protected void populateCategory(FunkoLine funkoLine, Element divCatRow) {
	if (!divCatRow.tagName().equals("div") || !divCatRow.className().equals("catrow")) {
	    logger.error("Element sent is not a <div class=\"catrow\">");
	} else {
	    Element aName = divCatRow.select("a").first();
	    funkoLine.setLabel(aName.text());
	    funkoLine.setUrl(aName.attr("abs:href").replace("/1/", "/%d/"));
	    logger.debug(funkoLine.toString());
	}
    }

    private String[] populateProductName(String productName) {
	// Toujours entre parenthèses

	// Black & White
	// B&W
	// Monochrome
	// Color Reject

	// Frozen mais pas Frozen fever
	// Clear
	// Faded
	// Transformation ? Elsa Frozen
	// Crystal
	// Transporting ? Star Trek / Big Bang Theory

	// Metallic
	// (Gold) ? Spongebob
	// Chrome mais pas monochrome

	// Flocked

	// Glitter
	// Beyond the wall ? John snow

	// Glow
	// Holographic ? Star Wars
	// Spirit ? Yoda

	// Patina
	return null;
    }

    private Set<FunkoLine> discoverFunkoLines() {
	Set<FunkoLine> lines = new HashSet<>();
	logger.info("Discovering Lines");
	Document doc = null;
	try {
	    doc = htmlParser.parseUrl("http://www.poppriceguide.com/guide");
	    for (Element div : doc.select("div.col-40")) {
		if ("Browse by Line".equals(div.text())) {
		    for (Element link : div.select("a")) {
			FunkoLine line = new FunkoLine();
			line.setImg(link.select("img").attr("src"));
			line.setUrl(link.attr("abs:href").replace("/1/", "/%d/"));
			logger.info("[New Line] {}", line.getUrl());
			lines.add(line);
		    }
		}
	    }
	} catch (IOException e) {
	    logger.error("Stacktrace : ", e);
	}
	logger.info("{} lines discovered", lines.size());
	return lines;
    }

    private Set<FunkoFranchise> discoverFunkoFranchises() {
	Set<FunkoFranchise> franchises = new HashSet<>();
	logger.info("Discovering Franchises");
	Document doc = null;
	try {
	    doc = htmlParser.parseUrl("http://www.poppriceguide.com/guide/franchise.php");
	    for (Element element : doc.getElementsByClass("col-33")) {
		FunkoFranchise franchise = new FunkoFranchise();
		franchise.setLabel(element.text());
		franchise.setUrl(element.select("a").first().attr("abs:href"));
		logger.info("[New Franchise] {}", franchise.getLabel());
		franchises.add(franchise);
	    }
	} catch (IOException e) {
	    logger.error("Stacktrace : ", e);
	}
	logger.info("{} franchises discovered", franchises.size());
	return franchises;
    }

    private Set<FunkoProduct> discoverProducts(FunkoLine line, int page) {
	Set<FunkoProduct> products = new HashSet<>();
	Document doc = null;
	try {
	    doc = htmlParser.parseUrl(String.format(line.getUrl(), page));
	    if (page == 1) {
		Element divLabel = doc.select("h1").first();
		if (divLabel != null) {
		    line.setLabel(divLabel.text());
		}
		Element divPage = doc.select("div.pagenav").last();
		if (divPage != null) {
		    line.setPage(Integer.parseInt(divPage.text()));
		} else {
		    line.setPage(1);
		}
	    }
	    logger.info("Discovering products for line [{}] page [{}]", line.toString(), page);

	    Elements subLines = doc.select("div.catrow");
	    if (subLines.size() > 0) {
		logger.info("{} subLines detected for line [{}]", subLines.size(), line.toString());
		for (Element divCatRow : subLines) {
		    FunkoLine subLine = new FunkoLine();
		    subLine.setParentLine(line);
		    populateCategory(subLine, divCatRow);
		    products.addAll(discoverProducts(subLine, 1));
		}
	    } else {
		for (Element divItemRow : doc.select("div.itemrow")) {
		    FunkoProduct product = new FunkoProduct();
		    product.setLine(line);
		    populateProduct(product, divItemRow);
		    products.add(product);
		}
		if (page < line.getPage()) {
		    products.addAll(discoverProducts(line, page + 1));
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	    logger.error(e.getMessage());
	}
	if (page == 1) {
	    logger.info("{} products discovered for line [{}]", products.size(), line.toString());
	}
	return products;
    }

}
