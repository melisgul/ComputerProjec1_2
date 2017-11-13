package com.example.hp.socketjavaapp;

import java.io.Serializable;

/**
 * Created by HP on 10-Nov-17.
 */

public class HuffmanObject implements Serializable {
    private static final long serialVersionUID = 1L;
    String character;
    double frequency;
    HuffmanObject left, right;

    public HuffmanObject(HuffmanObject left, HuffmanObject right)  {
        character = right.character + left.character;
        frequency = right.frequency + left.frequency;
        if (right.frequency <= left.frequency) {
            this.right = left;
            this.left = right;
        }
        else {
            this.right = right;
            this.left = left;
        }
    }
    public HuffmanObject(double fr, String ch) {

        character = ch;
        frequency = fr;
        left = null;
        right = null;
    }
}