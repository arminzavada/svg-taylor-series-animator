package com.triad.fourier;

import com.triad.math.Complex;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGOMPathElement;
import org.apache.batik.anim.dom.SVGPathSupport;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGPoint;

import java.io.IOException;
import java.net.URI;

/**
 * Represenets a {@link ComplexFunction}, which gets it's values from an SVG file. It's values are computed from the first Path in the SVG file. If there is no Path tag present, then it throws an {@link IOException}. It uses Apache Batik library.
 * @see SVGPathSupport
 * @see SAXSVGDocumentFactory
 */
public class SVGComplexFunction implements ComplexFunction {
    /**
     * The static {@link SAXSVGDocumentFactory} used to build the {@link SVGDocument}s.
     */
    private static SAXSVGDocumentFactory SVGDocumentFactory = new SAXSVGDocumentFactory(XMLResourceDescriptor.getXMLParserClassName());
    /**
     * How many samples have been calculated.
     */
    private int numberOfSamples;
    /**
     * The calculated {@link Complex} values.
     */
    private Complex[] complexes;

    /**
     * Reads in the given SVG file, and calculates the given amount of values.
     * @param svgUri the SVG file uri.
     * @param numberOfSamples the number of samples to calculate.
     * @throws IOException when the given SVG file does not exist, or it does not contain a well formed SVG file with at least one Path tag.
     */
    public SVGComplexFunction(URI svgUri, int numberOfSamples) throws IOException {
        this.numberOfSamples = numberOfSamples;
        var svgDocument = openSVGDocument(svgUri);

        var path = (SVGOMPathElement)svgDocument.getElementsByTagName("path").item(0);
        if (path == null) {
            throw new IOException("The svg file specified does not contain any Path tags");
        }

        generateSamples(path, numberOfSamples);
        normaliseSamples();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfSamples() {
        return numberOfSamples;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Complex getValueAt(int t) {
        return complexes[t];
    }

    /**
     * Initialises the SVGDocumentv
     * @param svgUri the SVG file uri.
     * @throws IOException when the given SVG file does not exist, or it does not contain a well formed SVG file with at least one Path tag.
     * @return the parsed SVGDocument
     */
    private SVGDocument openSVGDocument(URI svgUri) throws IOException {
        var userAgent = new UserAgentAdapter();
        var loader = new DocumentLoader(userAgent);
        var bridgeContext = new BridgeContext(userAgent, loader);
        bridgeContext.setDynamicState(BridgeContext.DYNAMIC);
        var builder = new GVTBuilder();

        var svgDocument = SVGDocumentFactory.createSVGDocument(svgUri.getRawPath());

        builder.build(bridgeContext, svgDocument);

        return svgDocument;
    }

    /**
     * Genrates the samples from the given {@link SVGOMPathElement} path.
     * @param path the specified {@link SVGOMPathElement}.
     * @param numberOfSamples the number of samples to calculate.
     */
    private void generateSamples(SVGOMPathElement path, int numberOfSamples) {
        complexes = new Complex[numberOfSamples];

        float unit_length = path.getTotalLength()/numberOfSamples;

        for(int i = 0; i < numberOfSamples; i++) {
            SVGPoint tmp_point = SVGPathSupport.getPointAtLength(path, unit_length * i);
            complexes[i] = new Complex(tmp_point.getX(), tmp_point.getY());
        }
    }

    /**
     * Normalises the samples, so their average be zero.
     */
    private void normaliseSamples() {
        var c = new Complex(0, 0);
        for (Complex complex : complexes) {
            c = Complex.add(c, complex);
        }
        c = Complex.multiply(c, -1f / numberOfSamples);

        for (int i = 0; i < numberOfSamples; i++) {
            complexes[i] = Complex.add(complexes[i], c);
        }
    }

}
