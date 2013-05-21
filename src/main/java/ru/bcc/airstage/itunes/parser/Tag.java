/*
 * Source code from iPlaylist Copier is (C) Jason Baker 2006
 * 
 * Please make an effort to document your additions to this source code file,
 * so future developers can give you credit where due.
 * 
 * Please include this copyright information in these source files when
 * redistributing source code. 
 *
 * Please make note of this copyright information in documentation for
 * binary redistributions that contain any or all of the source code. 
 *
 * If you are having any trouble understanding the meaning of this code
 * email jason directly at jason@onejasonforsale.com.
 *
 * Thanks, and happy coding!
 */

/*
 * Tag.java
 *
 * Created on September 11, 2006, 6:22 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ru.bcc.airstage.itunes.parser;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import ru.bcc.airstage.itunes.handler.constants.TagType;

import java.nio.CharBuffer;

/**
 * Represents XML tag
 */
public class Tag {
    private static final Logger log = Logger.getLogger(Tag.class);

    @NotNull
    private TagType name;

    @NotNull
    private String innerText;
    private CharBuffer byteBuffer;

    public Tag(@NotNull TagType name) {
        this.name = name;
    }

    @NotNull
    public TagType getName() {
        return name;
    }

    public void setName(@NotNull TagType name) {
        this.name = name;
    }

    @NotNull
    public String getInnerText() {
        return innerText;
    }

    public void addInnerText(char[] buffer, int start, int length) {
        innerText = decode(buffer, start, length);
    }

    public String toString() {
        return "Tag {name: " + name + ", innerText: " + innerText + "}";
    }

    @NotNull
    private String decode(char[] buffer, int start, int length) {
        String utf8 = new String(buffer, start, length);
        for (char b : utf8.toCharArray()) {
            if (b > 0xC0 && b < 0x17C) {
                log.warn("Can't parse " + utf8);
                return "Undefined";
            }
        }
        return utf8;
    }
}
