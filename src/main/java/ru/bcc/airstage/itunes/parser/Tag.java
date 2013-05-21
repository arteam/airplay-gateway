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

import org.jetbrains.annotations.Nullable;
import org.mozilla.universalchardet.UniversalDetector;
import ru.bcc.airstage.itunes.handler.constants.TagType;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Represents XML tag
 */
public class Tag {

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
            if ((int) b > 0xC0) {
                try {
                    byte[] buf = utf8.getBytes("windows-1252");
                    return new String(buf, "windows-1251").replace("?", "");
                } catch (UnsupportedEncodingException e) {
                    throw new IllegalStateException(e);
                }
            }
        }

        return utf8;
    }
}
