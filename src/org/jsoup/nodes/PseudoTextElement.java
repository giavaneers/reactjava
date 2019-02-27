package org.jsoup.nodes;

import org.jsoup.parser.Tag;

import java.io.IOException;

/**
 * Represents a {@link org.jsoup.nodes.TextNode} as an {@link org.jsoup.nodes.Element}, to enable text nodes to be selected with
 * the {@link org.jsoup.select.Selector} {@code :matchText} syntax.
 */
public class PseudoTextElement extends Element {

    public PseudoTextElement(Tag tag, String baseUri, Attributes attributes) {
        super(tag, baseUri, attributes);
    }

    @Override
    void outerHtmlHead(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
    }

    @Override
    void outerHtmlTail(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
    }
}
