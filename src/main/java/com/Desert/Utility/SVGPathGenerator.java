package com.Desert.Utility;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.bridge.*;
import org.apache.batik.dom.svg.SVGDocumentFactory;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGPath;
import org.apache.batik.util.XMLResourceDescriptor;
import org.springframework.core.io.Resource;
import org.w3c.dom.svg.SVGDocument;

import java.io.IOException;

public class SVGPathGenerator {

    public static String generate(Resource svgResource) throws IOException {
        /*
        Create Document from SVG Resource
         */
        String s = XMLResourceDescriptor.getXMLParserClassName();
        SVGDocumentFactory factory = new SAXSVGDocumentFactory(s);
        SVGDocument document = factory.createSVGDocument(svgResource.getURL().toString());

        /*
        Create Graphics Node from Document
         */

        UserAgent agent = new UserAgentAdapter();
        DocumentLoader loader = new DocumentLoader(agent);
        BridgeContext context = new BridgeContext(agent, loader);
        context.setDynamicState(BridgeContext.DYNAMIC);

        GVTBuilder builder = new GVTBuilder();
        GraphicsNode node = builder.build(context, document);

        /*
        Get SVG Path
         */
        SVGGeneratorContext genContext = SVGGeneratorContext.createDefault(document);
        return SVGPath.toSVGPathData(node.getOutline(), genContext);
    }
}
