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

public final class SVGProcessor {
    private static SAXSVGDocumentFactory SVGDocumentFactory = new SAXSVGDocumentFactory(XMLResourceDescriptor.getXMLParserClassName());
    private Complex[] fourierSeries;
    private int numberOfSamples;
    private int seriesLength;

    public SVGProcessor(URI svgUri, int numberOfSamples, int seriesLength) throws IOException {
        this.numberOfSamples = numberOfSamples;
        this.seriesLength = seriesLength;

        var svgDocument = openSVGDocument(svgUri);

        var path = (SVGOMPathElement)svgDocument.getElementsByTagName("path").item(0);
        if (path == null) {
            throw new IOException("The svg file specified does not contain any Path tags");
        }

        Complex[] complexes = getSamples(path, numberOfSamples);
        fourierSeries = generateSeries(complexes, seriesLength);
    }

    public Complex getValueAtIndex(int k) { return fourierSeries[k + seriesLength]; }
    public int getNumberOfSamples() { return numberOfSamples; }
    public int getSeriesLength() { return seriesLength; }

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

    private static Complex[] generateSeries(Complex[] samples, int n) {
        Complex[] output = new Complex[n * 2 + 1];

        for (int k = -n; k <= n; k++) {
            output[k + n] = integrateOver(samples, k);
        }

        return output;
    }

    private static Complex integrateOver(Complex[] samples, int k) {
        Complex result = new Complex(0, 0);

        for (int i = 0; i < samples.length; i++) {
            float t = (float)i / samples.length;
            result = Complex.add(
                    result,
                    Complex.multiply(
                            samples[i],
                            Complex.exp(
                                    Complex.multiply(
                                            Complex.I,
                                            k * 2 * (float)Math.PI * t
                                    )
                            )
                    )
            );
        }

        return result;
    }
}
