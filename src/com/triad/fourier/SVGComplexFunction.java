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

public class SVGComplexFunction implements ComplexFunction {
    private static SAXSVGDocumentFactory SVGDocumentFactory = new SAXSVGDocumentFactory(XMLResourceDescriptor.getXMLParserClassName());
    private int numberOfSamples;
    private float svgLength;
    private Complex[] complexes;

    public SVGComplexFunction(URI svgUri, int numberOfSamples) throws IOException {
        this.numberOfSamples = numberOfSamples;
        var svgDocument = openSVGDocument(svgUri);

        var path = (SVGOMPathElement)svgDocument.getElementsByTagName("path").item(0);
        if (path == null) {
            throw new IOException("The svg file specified does not contain any Path tags");
        }

        svgLength = path.getTotalLength();

        complexes = getSamples(path, numberOfSamples);
    }

    @Override
    public float getFunctionRange() {
        return svgLength;
    }

    @Override
    public int getNumberOfSamples() {
        return numberOfSamples;
    }

    @Override
    public Complex getValueAt(int t) {
        return complexes[t];
    }

    private static SVGDocument openSVGDocument(URI svgUri) throws IOException {
        var userAgent = new UserAgentAdapter();
        var loader = new DocumentLoader(userAgent);
        var bridgeContext = new BridgeContext(userAgent, loader);
        bridgeContext.setDynamicState(BridgeContext.DYNAMIC);
        var builder = new GVTBuilder();

        var svgDocument = SVGDocumentFactory.createSVGDocument(svgUri.getRawPath());

        builder.build(bridgeContext, svgDocument);

        return svgDocument;
    }

    private static Complex[] getSamples(SVGOMPathElement path, int numberOfSamples) {
        float unit_length = path.getTotalLength()/numberOfSamples;
        Complex[] complexes = new Complex[numberOfSamples];

        for(int i = 0; i < numberOfSamples; i++) {
            SVGPoint tmp_point = SVGPathSupport.getPointAtLength(path, unit_length * i);
            complexes[i] = new Complex(tmp_point.getX(), tmp_point.getY());
        }

        return complexes;
    }
}
