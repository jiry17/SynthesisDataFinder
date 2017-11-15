// Copyright 2017 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
package com.yahoo.vespa.model.container.xml;

import com.yahoo.config.model.builder.xml.XmlHelper;
import com.yahoo.container.bundle.BundleInstantiationSpecification;
import com.yahoo.component.ComponentSpecification;
import com.yahoo.processing.handler.ProcessingHandler;
import com.yahoo.search.handler.SearchHandler;
import org.w3c.dom.Element;

import java.util.*;

/**
 * This object builds a bundle instantiation spec from an XML element.
 *
 * @author gjoranv
 */
public class BundleInstantiationSpecificationBuilder {

    public static BundleInstantiationSpecification build(Element spec, boolean legacyMode) {
        ComponentSpecification id = XmlHelper.getIdRef(spec);
        ComponentSpecification classId = getComponentSpecification(spec, "class");
        ComponentSpecification bundle = getComponentSpecification(spec, "bundle");

        BundleInstantiationSpecification instSpec = new BundleInstantiationSpecification(id, classId, bundle);
        if ( ! legacyMode) // TODO: Remove?
            validate(instSpec);

        return bundle == null ? setBundleForKnownClass(instSpec) : instSpec;
    }

    private static BundleInstantiationSpecification setBundleForKnownClass(BundleInstantiationSpecification spec) {
        return BundleMapper.getBundle(spec.getClassName()).
                map(spec::inBundle).
                orElse(spec);
    }


    private static void validate(BundleInstantiationSpecification instSpec) {
        List<String> forbiddenClasses = Arrays.asList(
                "com.yahoo.search.handler.SearchHandler",
                "com.yahoo.processing.handler.ProcessingHandler");

        for (String forbiddenClass: forbiddenClasses) {
            if (forbiddenClass.equals(instSpec.getClassName())) {
                throw new RuntimeException("Setting up " + forbiddenClass + " manually is not supported.");
            }
        }
    }

    //null if missing
    private static ComponentSpecification getComponentSpecification(Element spec, String attributeName) {
        return (spec.hasAttribute(attributeName)) ?
                new ComponentSpecification(spec.getAttribute(attributeName)) :
                null;
    }

}
