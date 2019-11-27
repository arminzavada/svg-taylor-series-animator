package com.triad.fourier;

import com.triad.math.Complex;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGOMPathElement;
import org.apache.batik.anim.dom.SVGPathSupport;
import org.apache.batik.bridge.*;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGPoint;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class SVGProcessor {
    private static SAXSVGDocumentFactory SVGDocumentFactory = new SAXSVGDocumentFactory(XMLResourceDescriptor.getXMLParserClassName());
    private SVGOMPathElement path;

    public SVGProcessor(URI svgUri) throws IOException {
        UserAgent userAgent = new UserAgentAdapter();
        DocumentLoader loader = new DocumentLoader( userAgent );
        BridgeContext bridgeContext = new BridgeContext( userAgent, loader );
        bridgeContext.setDynamicState( BridgeContext.DYNAMIC );

        SVGDocument svgDoc = SVGDocumentFactory.createSVGDocument(svgUri.getRawPath());
        (new GVTBuilder()).build( bridgeContext, svgDoc );
        path = (SVGOMPathElement)svgDoc.getElementsByTagName("path").item(0);
    }

    public Complex[] getProcessed(int count) {
        float unit_length = path.getTotalLength()/count;
        Complex[] complexes = new Complex[count];

        for(int i = 0; i < count; i++){
            SVGPoint tmp_point = SVGPathSupport.getPointAtLength(path, unit_length * i);
            complexes[i] = new Complex(tmp_point.getX(), tmp_point.getY());
        }

        return complexes;
    }
}
