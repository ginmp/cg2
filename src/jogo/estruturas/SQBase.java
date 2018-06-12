/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.estruturas;

import java.util.LinkedList;

/**
 *
 * @author inmp
 */
public abstract class SQBase<T> {

    protected LinkedList<T> linkedList;

    public SQBase() {
        linkedList = new LinkedList<T>();
    }

    public abstract void add(T e);

    public abstract T get();

    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    public void clear() {
        linkedList.clear();
    }

    public int size() {
        return linkedList.size();
    }

}
