package org.ikeda.security.macaroons.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class MacaroonNamespaceHandler implements NamespaceHandler {

    private final Log logger = LogFactory.getLog(getClass());

    public MacaroonNamespaceHandler() {
        String coreVersion = SpringSecurityCoreVersion.getVersion();


    }
    @Override
    public void init() {

    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        return null;
    }

    @Override
    public BeanDefinitionHolder decorate(Node source, BeanDefinitionHolder definition, ParserContext parserContext) {
        return null;
    }
}
